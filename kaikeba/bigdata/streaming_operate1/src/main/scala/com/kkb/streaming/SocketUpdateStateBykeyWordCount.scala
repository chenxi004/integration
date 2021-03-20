package com.kkb.streaming

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SocketUpdateStateBykeyWordCount {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    // todo: 1������SparkConf����
    val sparkConf: SparkConf = new SparkConf().setAppName("SocketUpdateStateBykeyWordCount").setMaster("local[2]")

    // todo: 2������StreamingContext����
    val ssc = new StreamingContext(sparkConf,Seconds(2))
    //��Ҫ����checkpointĿ¼�����ڱ���֮ǰ���εĽ������,��Ŀ¼һ��ָ��hdfs·��
    ssc.checkpoint("hdfs://node01:8020/ck")
    //todo: 3������socket����
    val socketTextStream: ReceiverInputDStream[String] = ssc.socketTextStream("node01",9999)

    //todo: 4�������ݽ��д���
    val wordAndOneDstream: DStream[(String, Int)] = socketTextStream.flatMap(_.split(" ")).map((_,1))

    val result: DStream[(String, Int)] = wordAndOneDstream.updateStateByKey(updateFunc)

    //todo: 5����ӡ���
    result.print()

    //todo: 6��������ʽ����
    ssc.start()
    ssc.awaitTermination()

  }
  //currentValue:��ǰ������ÿһ�����ʳ��ֵ����е�1
  //historyValues:֮ǰ������ÿ�����ʳ��ֵ��ܴ���,Option���ͱ�ʾ���ڻ��߲����ڡ� Some��ʾ������ֵ��None��ʾû��
  def updateFunc(currentValue:Seq[Int], historyValues:Option[Int]):Option[Int] = {

    val newValue: Int = currentValue.sum + historyValues.getOrElse(0)
    Some(newValue)
  }
}
