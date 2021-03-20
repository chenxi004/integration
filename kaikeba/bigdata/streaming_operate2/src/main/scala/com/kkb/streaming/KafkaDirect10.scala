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

    //1、创建StreamingContext对象
    val sparkConf= new SparkConf()
      .setAppName("KafkaDirect10")
      .setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf,Seconds(2))


    //2、使用direct接受kafka数据
    //准备配置
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
        //数据本地性策略
        LocationStrategies.PreferConsistent,
        //指定要订阅的topic
        ConsumerStrategies.Subscribe[String, String](topic, kafkaParams)
      )

    //3、对数据进行处理
    //如果你想获取到消息消费的偏移，这里需要拿到最开始的这个Dstream进行操作
    //如果你对该DStream进行了其他的转换之后，生成了新的DStream，新的DStream不在保存对应的消息的偏移量
    kafkaDStream.foreachRDD(rdd =>{
      rdd.foreach(record=>{
        println("topic:"+record.topic()+"; partition:"+record.partition()+"; data:"+record.value())
      })

      //4、提交偏移量信息，把偏移量信息添加到kafka中
      val offsetRanges: Array[OffsetRange] =
        rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      kafkaDStream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
    })


    //5、开启流式计算
    ssc.start()
    ssc.awaitTermination()

  }

}
