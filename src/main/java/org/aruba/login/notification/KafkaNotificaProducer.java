package org.aruba.login.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaNotificaProducer
{

    @Autowired
    private KafkaTemplate<String, Notifica> notificaKafkaTemplate;

    public void sendCustomMessage(final Notifica user, final String topicName)
    {
        notificaKafkaTemplate.send(topicName, user);
    }
}
