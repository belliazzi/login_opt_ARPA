package org.aruba.login.notification;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
/**
 * La classe CountDownLatch viene utilizzata per sincronizzare più thread in modo che uno o più thread possano attendere fino a quando tutti i thread non hanno completato la loro esecuzione. Nel tuo caso, stai utilizzando CountDownLatch per garantire che il thread consumer non esca prima che l'invio della notifica sia stato completato con successo.

In particolare, stai impostando il valore del CountDownLatch su 1 (new CountDownLatch(1)) e lo decrementi di 1 all'interno del listener dopo l'invio della notifica (latch.countDown()). Questo significa che il thread consumer rimarrà in attesa fino a quando non viene chiamato countDown() e il valore del latch diventa 0.

In questo modo, il thread consumer non esce prima che la notifica sia stata inviata correttamente e puoi utilizzare il metodo getLatch() per controllare se il thread consumer ha completato con successo l'invio della notifica.

Quindi, l'uso di CountDownLatch in questo caso sembra essere una buona pratica per garantire che il thread consumer attenda l'invio della notifica prima di uscire.
 * @author damiano
 *
 */
@Service
public class KafkaNotificaConsumer
{
    private final CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private NotificationService notificationService;

    private final Logger logger = LoggerFactory.getLogger(KafkaNotificaConsumer.class);

    @KafkaListener(topics = "arubatopic", groupId = "group-id", containerFactory = "notificaKafkaListenerContainerFactory")
    public void listener(final Notifica notifica)
    {

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
