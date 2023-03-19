package org.aruba.login.notification;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * RabbitMQReceiver 
 * classe consumer delle queue che contiene i messaggi da spedire via email ,whatzapp smsm slack 
 * @author damiano
 *
 */
@Component
public class RabbitMQReceiver
{
    private final CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveMessage(final byte[] messageBytes) throws IOException
    {
        final Notifica notifica = objectMapper.readValue(messageBytes, Notifica.class);
        try {
            notificationService.sendNotification(notifica);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        latch.countDown();
    }

    public CountDownLatch getLatch()
    {
        return latch;
    }
}
