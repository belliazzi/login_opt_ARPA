package org.aruba.login.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * note di sviluppo perche non ci sta key store : Il keystore viene utilizzato per gestire le chiavi di firma digitale e
 * i relativi certificati. Nel codice che hai fornito, tuttavia, non viene utilizzato il keystore perché la firma
 * digitale viene estratta direttamente dal documento in input. In generale, il keystore è utile quando si desidera
 * firmare digitalmente un documento e verificare la firma utilizzando la chiave pubblica corrispondente al certificato
 * utilizzato per la firma. In questo caso, il keystore viene utilizzato per gestire le chiavi di firma digitali e i
 * relativi certificati, consentendo la selezione della chiave di firma corretta in base all'alias specificato. In
 * sintesi, il keystore è uno strumento utile per gestire le chiavi di firma digitale e i relativi certificati, ma non è
 * strettamente necessario per la validazione della firma digitale di un documento.
 * 
 * @author damiano
 */
@Service
public class FileValidationService
{
    private static final Logger logger = LoggerFactory.getLogger(FileValidationService.class);

    public boolean validateFile(final MultipartFile file) throws IOException
    {
        try (InputStream input = file.getInputStream()) {
            PdfReader reader = new PdfReader(input);
            AcroFields fields = reader.getAcroFields();
            return fields.getSignatureNames().size() > 0;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IOException("Siete pregati di inserire un file firmato", e);
        }
    }

    // altri metodi del servizio...

}
