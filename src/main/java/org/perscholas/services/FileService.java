package org.perscholas.services;

import org.perscholas.exception.FileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    @Value("${app.upload.dir:${user.home}}")
    String uploadDir;

    public void uploadFile(MultipartFile file) {
       try {
           Path copyLocation = Paths.get(uploadDir + File.separator + file.getOriginalFilename());
           Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
       }
       catch (FileException | IOException e) {
           e.printStackTrace();
           throw new FileException("could not find file");
       }
    }
}
