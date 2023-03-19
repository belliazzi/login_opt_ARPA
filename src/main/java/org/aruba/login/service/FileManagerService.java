package org.aruba.login.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.aruba.login.controller.AppController;
import org.aruba.login.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * il metodo in base alla properties upload.filesystem.enable decide se salvare in open km o su file systrem
 * il file pdf allegato nella registrazione
 * 
 * @author damiano
 */
@Service
public class FileManagerService
{
    private static final Logger logger = LoggerFactory.getLogger(FileManagerService.class);

    @Value("${upload.filesystem.enable}") // directory principale in cui salvare i file
    private boolean isEnableFileSystem;

    @Value("${upload.dir}") // directory principale in cui salvare i file
    private String uploadDir;

    @Autowired
    private OpenKMService openKMService;

    public String uploadFile(final MultipartFile file, final Long id, final User savedUser) throws Exception
    {
        String docPath="";
        logger.debug("uploadFile  inizio");
        if (isEnableFileSystem) {
            logger.debug("uploadFile  su file system");
            // salav su file sistem
            // crea la directory se non esiste
            File directory = new File(uploadDir, id.toString());
            boolean dirCreated = directory.mkdirs();
            if (!dirCreated) {
                logger.error("Impossibile creare la directory per l'upload del file");
                throw new IOException("Impossibile creare la directory per l'upload del file");
            }

            // salva il file nella directory creata
            String filename = null;
            if (file != null && file.getOriginalFilename() != null && !file.isEmpty()) {
                filename = file.getOriginalFilename();
            }

            if (filename == null || filename.isEmpty()) {
                filename = "file_" + System.currentTimeMillis(); // nome generico per il file
            }
            docPath=directory.getAbsolutePath() + File.separator + filename;
            File destFile = new File(docPath);
            if (file == null) {
                logger.error("Il file e' nullo");
                throw new IllegalArgumentException("Il file e' nullo");
            }
            file.transferTo(destFile);

        } else {
            logger.debug("uploadFile  su file open km");
            docPath=savedUser.getId() + "/" + file.getOriginalFilename();
            openKMService.savefile(savedUser, file);
        }
        logger.debug("uploadFile docPath: "+docPath);
        return docPath;

    }

    public InputStream downloadFile(final String filename, final String id) throws Exception
    {
        logger.debug("downloadFile  inizio");
        if (isEnableFileSystem) {
            logger.debug("downloadFile  da file sistem");
            // recupera il file dalla sottodirectory specificata
            File directory = new File(uploadDir, id);
            File file = new File(directory.getAbsolutePath() + File.separator + filename);
            // verifica se il file esiste e può essere letto
            if (file.exists() && file.canRead()) {
                // restituisce un InputStream che può essere utilizzato per leggere il contenuto del file
                return new FileInputStream(file);
            } else {
                logger.error("File not found or not readable");
                throw new IOException("File not found or not readable");
            }
        } else {
            logger.debug("downloadFile  da openkm");
            return openKMService.getContent(id + "/" + filename);
        }

    }
}
