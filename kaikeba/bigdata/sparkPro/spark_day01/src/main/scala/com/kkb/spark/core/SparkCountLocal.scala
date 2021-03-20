package com.kkb.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SparkCountLocal {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("WorkCount").setMaster("local[*]")
    val sc = new SparkContext(conf)
    //本地资源文件
    //val tuples: Array[(String, Int)] = sc.textFile(ClassLoader.getSystemResource("word.csv").getPath)
    //  .flatMap(_.split(","))
    //  .map((_, 1))
    //  .reduceByKey(_ + _)
    //  .collect()

    //hdfs 文件
    val tuples: Array[(String, Int)] = sc.textFile("hdfs://node01:8020/word.txt")
      .flatMap(_.split(" "))
      .map((_, 1))
      .reduceByKey(_ + _)
      .collect()
    tuples.foreach(println)
    sc.stop()
  }
}