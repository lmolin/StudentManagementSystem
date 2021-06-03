package org.perscholas.controllers;

import org.perscholas.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping("/uploadForm")
    public String uploadForm() {
        return "uploadForm";
    }

    @PostMapping
    public String uploadFile(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes) {
        fileService.uploadFile(file);
        redirectAttributes.addFlashAttribute("message", file.getOriginalFilename() + " uploaded");
        return "redirect:/uploadForm";
    }
}
