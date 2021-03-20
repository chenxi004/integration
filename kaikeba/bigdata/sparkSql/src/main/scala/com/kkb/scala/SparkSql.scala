package com.kkb.scala
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

//通过动态指定dataFrame对应的schema信息将rdd转换成dataFrame
object SparkSql {

  def main(args: Array[String]): Unit = {
    //1、构建SparkSession对象
    val spark: SparkSession = SparkSession.builder().appName("SparkSql").master("local").getOrCreate()

    //2、获取sparkContext对象
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("warn")

    //3、读取文件数据
    val data: RDD[Array[String]] = sc.textFile(this.getClass.getClassLoader.getResource("person.txt").getPath).map(x=>x.split(" "))

    //4、将rdd与Row对象进行关联
    val rowRDD: RDD[Row] = data.map(x=>Row(x(0),x(1),x(2).toInt))

    //5、指定dataFrame的schema信息
    //这里指定的字段个数和类型必须要跟Row对象保持一致
    val schema=StructType(
      StructField("id",StringType)::
        StructField("name",StringType)::
        StructField("age",IntegerType)::Nil
    )

    val dataFrame: DataFrame = spark.createDataFrame(rowRDD,schema)
    dataFrame.printSchema()
    dataFrame.show(3)
    dataFrame.count()
    //dataFrame.createTempView("person")
    //spark.sql("select count(*) from person").show()

    spark.stop()
  }
}
