package org.aruba.login.repository;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.bean.Document;
import com.openkm.sdk4j.bean.Folder;
import com.openkm.sdk4j.exception.AccessDeniedException;
import com.openkm.sdk4j.exception.AutomationException;
import com.openkm.sdk4j.exception.DatabaseException;
import com.openkm.sdk4j.exception.ExtensionException;
import com.openkm.sdk4j.exception.FileSizeExceededException;
import com.openkm.sdk4j.exception.ItemExistsException;
import com.openkm.sdk4j.exception.LockException;
import com.openkm.sdk4j.exception.PathNotFoundException;
import com.openkm.sdk4j.exception.RepositoryException;
import com.openkm.sdk4j.exception.UnknowException;
import com.openkm.sdk4j.exception.UnsupportedMimeTypeException;
import com.openkm.sdk4j.exception.UserQuotaExceededException;
import com.openkm.sdk4j.exception.VirusDetectedException;
import com.openkm.sdk4j.exception.WebserviceException;

@Repository
public class OpenKmRepository
{
    @Autowired
    private OKMWebservices repository;

    public Document createDocumentSimple(final String path, final InputStream content) throws Exception
    {
        try {
            return repository.createDocumentSimple(path, content);
        } catch (ItemExistsException e) {
            e.printStackTrace();
        } catch (DatabaseException | IOException | UnsupportedMimeTypeException | FileSizeExceededException
            | UserQuotaExceededException | VirusDetectedException | PathNotFoundException | AccessDeniedException
            | RepositoryException | ExtensionException | AutomationException | UnknowException
            | WebserviceException e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }

    public void deleteDocument(final String path) throws Exception
    {
        try {
            repository.deleteDocument(path);
        } catch (RepositoryException | AccessDeniedException | DatabaseException | UnknowException | WebserviceException
            | PathNotFoundException | LockException | ExtensionException e) {
            throw new Exception(e.getMessage());
        }
    }

    public Folder createFolderSimple(final String path) throws Exception
    {
        try {
            return repository.createFolderSimple(path);
        } catch (ItemExistsException | AccessDeniedException | RepositoryException | PathNotFoundException
            | DatabaseException | ExtensionException | AutomationException | UnknowException | WebserviceException e) {
            throw new Exception(e.getMessage());
        }
    }

    public void createMissingFoldersIfNotExist(final String path) throws Exception
    {
        try {
            repository.createMissingFolders(path);
        } catch (ItemExistsException e) {
            // Nothing to do
        } catch (AccessDeniedException | RepositoryException | PathNotFoundException | DatabaseException
            | ExtensionException | AutomationException | UnknowException | WebserviceException e) {
            throw new Exception(e.getMessage());
        }
    }

    public void deleteFolderIfExists(final String path) throws Exception
    {
        try {
            repository.deleteFolder(path);
        } catch (PathNotFoundException | RepositoryException e) {
            // Nothing to do
        } catch (LockException | AccessDeniedException | DatabaseException | UnknowException | WebserviceException e) {
            throw new Exception(e.getMessage());
        }
    }

    public InputStream getContent(final String path) throws RepositoryException, IOException, PathNotFoundException,
        AccessDeniedException, DatabaseException, UnknowException, WebserviceException
    {
        // TODO Auto-generated method stub
        return repository.getContent(path);
    }

    public void deleteFolder(final String path) throws LockException, PathNotFoundException, AccessDeniedException,
        RepositoryException, DatabaseException, UnknowException, WebserviceException
    {
        // TODO Auto-generated method stub
        repository.deleteFolder(path);
    }
}
