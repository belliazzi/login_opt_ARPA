package org.aruba.login.service;

import java.io.InputStream;
import java.util.List;

import org.aruba.login.domain.User;
import org.aruba.login.dto.UserDTO;
import org.aruba.login.notification.KafkaNotificaProducer;
import org.aruba.login.notification.Notifica;
import org.aruba.login.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagedRegistrationUserService
{
    private static final Logger logger = LoggerFactory.getLogger(ManagedRegistrationUserService.class);

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private FileManagerService fileManagerService;

    @Autowired
    private KafkaNotificaProducer kafkaNotificaProducer;

    public User save(final UserDTO userDTO) throws Exception
    {
        logger.debug("save  inizio");

        User userPersist = new User(userDTO.getEmail(), userDTO.getPassword(), userDTO.getFirstName(),
            userDTO.getLastName(), userDTO.getCf(), userDTO.getFile().getOriginalFilename());

        User savedUser = userRepo.save(userPersist);
        try {
            fileManagerService.uploadFile(userDTO.getFile(), savedUser.getId(), savedUser);
        } catch (Exception e) {
            userRepo.delete(userPersist);
            logger.error(e.getMessage(), e);
        }
        if (savedUser != null) {
            // Send notification to Kafka topic
            // String testo, String destinatario, String oggetto, String tipo
            Notifica notifica = new Notifica("belliazzi@tiscali.it",
                "User " + savedUser.getFirstName() + " " + savedUser.getLastName() + " has logged in",
                "belliazzi@tiscali.it", "notifica aruba login", "Email");
            // rabbitMQNotificaProducer.sendNotifica(notifica);
            kafkaNotificaProducer.sendCustomMessage(notifica, "arubatopic");
        }
        logger.debug("save  fine");
        return savedUser;
    }

    public List<User> findAll()
    {
        return userRepo.findAll();
    }

    public User findByCF(String cf)
    {
        return userRepo.findByCF(cf);
    }

    public InputStream download(final String filename, final Long id) throws Exception
    {
        logger.debug("chiamata download filename:" + filename);
        return fileManagerService.downloadFile(filename, id.toString());

    }
}
