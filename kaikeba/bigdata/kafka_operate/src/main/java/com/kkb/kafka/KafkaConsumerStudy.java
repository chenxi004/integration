package com.kkb.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
public class KafkaConsumerStudy {
    public static void main(String[] args) {
        //准备配置属性
        Properties props = new Properties();
        //kafka集群地址
        props.put("bootstrap.servers", "node01:9092,node02:9092,node03:9092");
        //消费者组id
        props.put("group.id", "consumer-test");
        //自动提交偏移量
        props.put("enable.auto.commit", "true");
        //自动提交偏移量的时间间隔
        props.put("auto.commit.interval.ms", "1000");
        //默认是latest
        //earliest: 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
        //latest: 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
        //none : topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
        props.put("auto.offset.reset","earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        //指定消费哪些topic
        consumer.subscribe(Arrays.asList("test"));
        while (true) {
            //不断的拉取数据
            ConsumerRecords<String, String> records =  consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
                //该消息所在的分区号
                int partition = record.partition();
                //该消息对应的key
                String key = record.key();
                //该消息对应的偏移量
                long offset = record.offset();
                //该消息内容本身
                String value = record.value();
                System.out.println("partition:"+partition+"\t key:"+key+"\toffset:"+offset+"\tvalue:"+value);
            }
        }
    }
}

