package org.aruba.login.service;

import java.io.IOException;
import java.io.InputStream;


import org.aruba.login.domain.User;
import org.aruba.login.repository.OpenKmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.openkm.sdk4j.bean.Document;
import com.openkm.sdk4j.exception.AccessDeniedException;
import com.openkm.sdk4j.exception.DatabaseException;
import com.openkm.sdk4j.exception.ExtensionException;
import com.openkm.sdk4j.exception.LockException;
import com.openkm.sdk4j.exception.PathNotFoundException;
import com.openkm.sdk4j.exception.RepositoryException;
import com.openkm.sdk4j.exception.UnknowException;
import com.openkm.sdk4j.exception.WebserviceException;

@Service
public class OpenKMService
{
    @Autowired
    private OpenKmRepository openKMClient;

    public Document savefile(final User user, final MultipartFile file) throws Exception
    {

        return openKMClient.createDocumentSimple(user.getId() + "/" + file.getOriginalFilename(), file.getInputStream());

    }
    
    public InputStream getContent(final String path) throws Exception
    {
        try
        {
            return openKMClient.getContent(path);
        }
        catch (RepositoryException | AccessDeniedException | DatabaseException
                | UnknowException | WebserviceException | IOException | PathNotFoundException e)
        {
            throw new Exception(e.getMessage(), e);
        }
    }
    
    public void deleteDocument(final String path) throws Exception
    {
        try
        {
            openKMClient.deleteDocument(path);
        }
        catch (RepositoryException | AccessDeniedException | DatabaseException | UnknowException | WebserviceException
                | PathNotFoundException | LockException | ExtensionException e)
        {
            throw  new Exception(e.getMessage(), e);
        }
    }
    
    
    public void deleteFolderIfExists(final String path) throws Exception
    {
        try
        {
            openKMClient.deleteFolder(path);
        }
        catch (PathNotFoundException | RepositoryException e)
        {
            // Nothing to do
        }
        catch (LockException | AccessDeniedException | DatabaseException | UnknowException | WebserviceException e)
        {
            throw  new Exception(e.getMessage(), e);
        }
    }
}
