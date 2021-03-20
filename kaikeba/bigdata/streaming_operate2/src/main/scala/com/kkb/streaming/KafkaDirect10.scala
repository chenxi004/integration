package com.kkb.streaming

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.{Seconds, StreamingContext}

object KafkaDirect10 {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    //1������StreamingContext����
    val sparkConf= new SparkConf()
      .setAppName("KafkaDirect10")
      .setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf,Seconds(2))


    //2��ʹ��direct����kafka����
    //׼������
    val topic =Set("bigdata")
    val kafkaParams=Map(
      "bootstrap.servers" ->"node01:9092,node02:9092,node03:9092",
      "group.id" -> "KafkaDirect10",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "enable.auto.commit" -> "false"
    )

    val kafkaDStream: InputDStream[ConsumerRecord[String, String]] =
      KafkaUtils.createDirectStream[String, String](
        ssc,
        //���ݱ����Բ���
        LocationStrategies.PreferConsistent,
        //ָ��Ҫ���ĵ�topic
        ConsumerStrategies.Subscribe[String, String](topic, kafkaParams)
      )

    //3�������ݽ��д���
    //��������ȡ����Ϣ���ѵ�ƫ�ƣ�������Ҫ�õ��ʼ�����Dstream���в���
    //�����Ը�DStream������������ת��֮���������µ�DStream���µ�DStream���ڱ����Ӧ����Ϣ��ƫ����
    kafkaDStream.foreachRDD(rdd =>{
      rdd.foreach(record=>{
        println("topic:"+record.topic()+"; partition:"+record.partition()+"; data:"+record.value())
      })

      //4���ύƫ������Ϣ����ƫ������Ϣ��ӵ�kafka��
      val offsetRanges: Array[OffsetRange] =
        rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      kafkaDStream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
    })


    //5��������ʽ����
    ssc.start()
    ssc.awaitTermination()

  }

}
