package org.aruba.login.dto;

import java.io.Serializable;

import org.aruba.login.domain.User;
import org.springframework.web.multipart.MultipartFile;

public class UserDTO extends User implements Serializable
{
    public UserDTO()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public UserDTO(final String email, final String password, final String firstName, final String lastName,
        final String cf, final MultipartFile file)
    {
        super(email, password, firstName, lastName, cf, file.getOriginalFilename());
        this.file = file;
    }

    MultipartFile file;

    public MultipartFile getFile()
    {
        return file;
    }

    public void setFile(final MultipartFile file)
    {
        this.file = file;
    }

}
