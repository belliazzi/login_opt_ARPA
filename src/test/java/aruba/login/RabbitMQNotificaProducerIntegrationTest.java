package aruba.login;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.aruba.login.SpringBootServer;
import org.aruba.login.config.RabbitMQConfig;
import org.aruba.login.notification.Notifica;
import org.aruba.login.producer.RabbitMQNotificaProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringBootServer.class, RabbitMQConfig.class})
@ActiveProfiles("unit")
public class RabbitMQNotificaProducerIntegrationTest
{

    @Autowired
    private RabbitMQNotificaProducer rabbitMQNotificaProducer;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.routingkey}")
    private String routingkey;

    @Test
    public void testSendNotifica() throws Exception
    {
        Notifica notifica = new Notifica("email","Titolo", "Corpo del messaggio", "login", "Email");

        // Deserializzare il POJO Java in una stringa JSON
        String jsonString = objectMapper.writeValueAsString(notifica);

        rabbitMQNotificaProducer.sendNotifica(notifica);

        // Verifica che il messaggio sia stato inviato a RabbitMQ
        Object message = rabbitTemplate.receiveAndConvert(routingkey);
        assertNotNull(message);
        assertTrue(message instanceof byte[]);
        Notifica receivedNotifica = objectMapper.readValue((byte[]) message, Notifica.class);
        assertEquals("Titolo", receivedNotifica.getTesto());
        assertEquals("Corpo del messaggio", receivedNotifica.getTesto());
    }

    @Test
    public void testMapper() throws Exception
    {
        Notifica notifica = new Notifica("email","Titolo", "Corpo del messaggio", "login", "");

        // Deserializzare il POJO Java in una stringa JSON
        String jsonString = objectMapper.writeValueAsString(notifica);
        
        byte[] messageBytes = jsonString.getBytes();
        Message message = MessageBuilder.withBody(messageBytes)
                                        .setContentType(MediaType.APPLICATION_JSON_VALUE)
                                        .build();
        try {
                rabbitTemplate.send(exchangeName,routingkey, message);
            
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail();
        }
        assertNotNull(jsonString);
    
    }
}
