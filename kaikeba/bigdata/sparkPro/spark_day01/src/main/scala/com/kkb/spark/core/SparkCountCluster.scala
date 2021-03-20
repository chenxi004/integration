package com.kkb.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SparkCountCluster{
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("WorkCount")//.setMaster("local[*]")
    val sc = new SparkContext(conf)
    val resultRDD: RDD[(String, Int)] = sc.textFile(args(0))
      .flatMap(_.split(" "))
      .map((_, 1))
      .reduceByKey(_ + _)
    resultRDD.saveAsTextFile(args(1))
    sc.stop()
  }
}