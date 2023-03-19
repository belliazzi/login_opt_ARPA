package org.aruba.login.controller;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.aruba.login.domain.User;
import org.aruba.login.dto.UserDTO;
import org.aruba.login.service.FileValidationService;
import org.aruba.login.service.ManagedRegistrationUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sogei.utility.UCheckDigit;

/**
 * Questa classe è una classe controller per un'applicazione Spring Boot. Qui vengono gestite le richieste HTTP in
 * arrivo dai client e vengono restituite le risposte adeguate. Inoltre, la classe ha un'annotazione @Controller che la
 * identifica come una classe controller, ovvero una classe che gestisce le richieste HTTP in arrivo. La classe ha anche
 * diverse dipendenze, come il UserRepository e BCryptPasswordEncoder, che sono annotate con @Autowired per indicare a
 * Spring di iniettare queste dipendenze durante l'esecuzione dell'applicazione.
 * 
 * @author damiano
 */
@Controller
public class AppController
{
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private ManagedRegistrationUserService managedRegistrationUserService;

    @Autowired
    private FileValidationService fileValidationService;

    /**
     * * Il metodo viewHomePage() gestisce una richiesta GET all'endpoint "/". Questo metodo viene utilizzato per
     * visualizzare la pagina principale
     * 
     * @return
     */
    @GetMapping("/")
    public String viewHomePage()
    {

        return "index";
    }

    /**
     * Il metodo showRegistrationForm() gestisce una richiesta GET all'endpoint "/register". Questo metodo viene
     * utilizzato per visualizzare il form di registrazione per un nuovo utente.
     * 
     * @param model
     * @return
     */
    @GetMapping("/register")
    public String showRegistrationForm(final Model model)
    {
        model.addAttribute("userDTO", new UserDTO());

        return "signup_form";
    }

    /**
     * Il metodo processRegister() gestisce una richiesta POST all'endpoint "/process_register". Questo metodo viene
     * utilizzato per elaborare i dati inviati dal form di registrazione e creare un nuovo utente nel database.
     * 
     * @param userDTO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/process_register")
    public String processRegister(final UserDTO userDTO, final Model model) throws Exception
    {
        try {
            logger.debug("controllo file inizio");
            if (userDTO.getFile().isEmpty()) {
                // verifica presenza file
                logger.error("file assente");
                throw new IOException("siete pregati di inserire un file");

            } else {
                if (!FilenameUtils.getExtension(userDTO.getFile().getOriginalFilename()).endsWith("pdf")) {
                    logger.error("file non in pdf :" + userDTO.getFile().getOriginalFilename());
                    throw new Exception("ammessi solo file pdf");
                }
                // Verifica la firma digitale del file se è in formato P7M o PDF
                if (!fileValidationService.validateFile(userDTO.getFile())) {
                    throw new Exception("nessuna firma nel file pdf");
                }
            }
            logger.debug("controllo file fine");
            logger.debug("controllo password encoder inizio");

            if (userDTO.getPassword().length() >= 6 && userDTO.getPassword().length() <= 12) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
                userDTO.setPassword(encodedPassword);
            } else {
                logger.error("password lunghezza errata");
                throw new Exception("lunghezza password >=6 a <=12");
            }

            logger.debug("controllo password encoder fine");
            logger.debug("controllo CF  inizio");
            UCheckDigit checkCf = new UCheckDigit(userDTO.getCf());
            if (checkCf.controllaCorrettezza())

            {
                logger.debug("controllo se CF già presente ");
                if (managedRegistrationUserService.findByCF(userDTO.getCf()) != null) {
                    throw new Exception("trovato doppione CF");
                }

                managedRegistrationUserService.save(userDTO);

            } else {
                throw new IOException("Cf errato");

            }
            logger.debug("controllo CF  fine");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("message", e.getMessage());
            return "signup_form";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("message", e.getMessage());
            return "signup_form";
        }

        return "register_success";
    }

    @GetMapping("/users")
    public String listUsers(final Model model)
    {
        List<User> listUsers = managedRegistrationUserService.findAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @GetMapping("/users/download/{id}/{filename}")
    public ResponseEntity<Resource> downloadUserDocument(final @PathVariable Long id,
        final @PathVariable("filename") String filename) throws Exception
    {
        logger.debug("downloadUserDocument   fine");
        InputStreamResource resource = new InputStreamResource(managedRegistrationUserService.download(filename, id));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
            .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);

    }
}
