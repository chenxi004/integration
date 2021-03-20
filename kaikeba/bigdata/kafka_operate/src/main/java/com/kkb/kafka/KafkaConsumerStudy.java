package com.kkb.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
public class KafkaConsumerStudy {
    public static void main(String[] args) {
        //׼����������
        Properties props = new Properties();
        //kafka��Ⱥ��ַ
        props.put("bootstrap.servers", "node01:9092,node02:9092,node03:9092");
        //��������id
        props.put("group.id", "consumer-test");
        //�Զ��ύƫ����
        props.put("enable.auto.commit", "true");
        //�Զ��ύƫ������ʱ����
        props.put("auto.commit.interval.ms", "1000");
        //Ĭ����latest
        //earliest: ���������������ύ��offsetʱ�����ύ��offset��ʼ���ѣ����ύ��offsetʱ����ͷ��ʼ����
        //latest: ���������������ύ��offsetʱ�����ύ��offset��ʼ���ѣ����ύ��offsetʱ�������²����ĸ÷����µ�����
        //none : topic���������������ύ��offsetʱ����offset��ʼ���ѣ�ֻҪ��һ���������������ύ��offset�����׳��쳣
        props.put("auto.offset.reset","earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        //ָ��������Щtopic
        consumer.subscribe(Arrays.asList("test"));
        while (true) {
            //���ϵ���ȡ����
            ConsumerRecords<String, String> records =  consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
                //����Ϣ���ڵķ�����
                int partition = record.partition();
                //����Ϣ��Ӧ��key
                String key = record.key();
                //����Ϣ��Ӧ��ƫ����
                long offset = record.offset();
                //����Ϣ���ݱ���
                String value = record.value();
                System.out.println("partition:"+partition+"\t key:"+key+"\toffset:"+offset+"\tvalue:"+value);
            }
        }
    }
}

