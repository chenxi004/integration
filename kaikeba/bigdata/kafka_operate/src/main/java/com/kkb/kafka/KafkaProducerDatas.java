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
        //消息的确认机制
        props.put("acks", "all");
        props.put("retries", 0);
        //缓冲区的大小  //默认32M
        props.put("buffer.memory", 33554432);
        //批处理数据的大小，每次写入多少数据到topic   //默认16KB
        props.put("batch.size", 16384);
        //可以延长多久发送数据   //默认为0 表示不等待 ，立即发送
        props.put("linger.ms", 1);
        //指定数据序列化和反序列化
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<String,String>(props);

        int messagNo = 1;

        while(true){
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("test", "helloworld"+messagNo);
            //同步发送
            //producer.send(producerRecord);

            //异步发送
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
            System.out.println("有异常，需要重新处理");
            //发送失败的数据写入指定文件
            saveFailData(recordMetadata);
        }else{
            System.out.println("没有异常，正常发送数据");
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
