import scala.collection.mutable.ListBuffer
val list = List("hello flink", "hello spark", "hello spark")
val listbuffer = ListBuffer[String]()
for( x <- list) {
  x.split(" ").foreach(x => listbuffer += x)
}
var map = listbuffer.map((_, 1)).groupBy(_._1).mapValues(_.map(_._2)).mapValues(_.reduce(_ + _))
map.foreach(x => println(x))