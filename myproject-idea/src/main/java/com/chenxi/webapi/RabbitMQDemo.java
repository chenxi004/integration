package com.chenxi.webapi;

import com.chenxi.utils.AMQP.QueueConsumer;
import com.chenxi.utils.AMQP.Producer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

@Path("/rabbitmq")
public class RabbitMQDemo {
    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public void RabbitMQTest() {
        QueueConsumer consumer = null;
        try {
            consumer = new QueueConsumer("queue");
            Thread consumerThread = new Thread(consumer);
            consumerThread.start();

            Producer producer = new Producer("queue");

            for (int i = 0; i < 100000; i++) {
                HashMap message = new HashMap();
                message.put("message number", i);
                producer.sendMessage(message);
                System.out.println("Message Number "+ i +" sent.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
