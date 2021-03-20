package com.kkb.scala

import java.util.Properties
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object SparkCsvMysql {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("SparkCsvMysql")

    val session: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()
    session.sparkContext.setLogLevel("WARN")
    val frame: DataFrame = session
      .read
      .format("csv")
      .option("timestampFormat", "yyyy/MM/dd HH:mm:ss ZZ")//时间转换
      .option("header", "true")//表示第一行数据都是head(字段属性的意思)
      .option("multiLine", true)//表示数据可能换行
      .load("D:\\WorkSpace\\JAVA\\kaikeba\\bigdata\\sparkSql\\src\\main\\resources\\data")

    frame.createOrReplaceTempView("job_detail")
    session.sql("select job_name,job_url,job_location,job_salary,job_company,job_experience,job_class,job_given,job_detail,company_type,company_person,search_key,city from job_detail where job_company = '北京无极慧通科技有限公司'  ").show(80)
    val prop = new Properties()
    prop.put("user", "root")
    prop.put("password", "root")

    frame.write.mode(SaveMode.Append).jdbc("jdbc:mysql://localhost:3306/ssm?useSSL=false&useUnicode=true&serverTimezone=UTC&characterEncoding=UTF-8", "job_detail", prop)

  }
}
