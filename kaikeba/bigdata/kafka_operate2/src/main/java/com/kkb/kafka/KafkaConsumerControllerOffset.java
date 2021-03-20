package com.kkb.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
public class KafkaConsumerControllerOffset {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "node01:9092,node02:9092,node03:9092");
        props.put("group.id", "controllerOffset");
        //�ر��Զ��ύ����Ϊ�ֶ��ύƫ����
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        //ָ��������Ҫ���ѵ�topic
        consumer.subscribe(Arrays.asList("test_day02"));

        //����һ�����֣���ʾ��Ϣ�ﵽ���ٺ��ֶ��ύƫ����
        final int minBatchSize = 20;

        //����һ�����飬����һ������
        List<ConsumerRecord<String, String>> buffer = new ArrayList<ConsumerRecord<String, String>>();
        while (true) {
            ConsumerRecords<String, String> records =  consumer.poll(Duration.ofSeconds(3));
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
                System.out.printf("partition=%d, offset= %d, key = %s, value = %s%n",record.partition(),record.offset(), record.key(),record.value());
            }
            if (buffer.size() >= minBatchSize) {
                //insertIntoDb(buffer);  �õ�����֮�󣬽�������
                System.out.println("������������������"+buffer.size());
                System.out.println("���Ѿ���������һ��������...");
                consumer.commitSync();
                buffer.clear();
            }
        }
    }
}

