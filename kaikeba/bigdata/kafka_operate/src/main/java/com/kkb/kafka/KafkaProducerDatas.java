package com.kkb.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Properties;

public class KafkaProducerDatas {
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "node01:9092,node02:9092,node03:9092");
        //��Ϣ��ȷ�ϻ���
        props.put("acks", "all");
        props.put("retries", 0);
        //�������Ĵ�С  //Ĭ��32M
        props.put("buffer.memory", 33554432);
        //���������ݵĴ�С��ÿ��д��������ݵ�topic   //Ĭ��16KB
        props.put("batch.size", 16384);
        //�����ӳ���÷�������   //Ĭ��Ϊ0 ��ʾ���ȴ� ����������
        props.put("linger.ms", 1);
        //ָ���������л��ͷ����л�
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<String,String>(props);

        int messagNo = 1;

        while(true){
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("test", "helloworld"+messagNo);
            //ͬ������
            //producer.send(producerRecord);

            //�첽����
            producer.send(producerRecord,new DataCallBack(messagNo,"helloworld" + messagNo));

            messagNo ++;
            Thread.sleep(3000);
        }
    }
}

class  DataCallBack implements  Callback{
    private final int key;
    private final String message;


    public DataCallBack(int key,String message){
        this.key = key;
        this.message = message;
    }

    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception exception) {
        if(exception != null){
            System.out.println("���쳣����Ҫ���´���");
            //����ʧ�ܵ�����д��ָ���ļ�
            saveFailData(recordMetadata);
        }else{
            System.out.println("û���쳣��������������");
        }
        if(recordMetadata !=null ){
            System.out.println("topic:" +recordMetadata.topic() +" | partition: "+recordMetadata.partition() + " | offset:" + recordMetadata.offset());
        }

    }

    private void saveFailData(RecordMetadata metadata){
        String path = "D:/kafka_fail/";
        String fileName = "kafka_fail.txt";
        if(null != this.message){
            try {
                File file = new File(path+fileName);
                if (!file.exists()) {
                    File dir = new File(file.getParent());
                    dir.mkdirs();
                    file.createNewFile();
                }

                String content = "topic:" +metadata.topic() +" | partition: "+metadata.partition() + " | offset:" + metadata.offset()+" | message:"+this.message+"\n";
                FileWriter fileWriter =new FileWriter(file, true);
                fileWriter.write(content);
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
                // do something
            } finally {
                // do something
            }
        }
    }
}
