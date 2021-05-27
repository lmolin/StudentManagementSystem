package org.perscholas.controllers;

import lombok.extern.java.Log;
import org.perscholas.models.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Log
@Controller
public class HomeController {

    @ModelAttribute("student")
    public Student newStudent() {
        return new Student();
    }

    /*
            - controllers should be separated e.g. @RequestMapping("admin"), @RequestMapping("student")
            - provide as much as possible e.g. get/post/put/delete mappings
     */

    @GetMapping("/template")
    public String template(){
        return "template";
    }

    @GetMapping("/student/register")
    public String registerPage() {
        return "register"; }

    @PostMapping("/student/confirm")
    public String registerNewStudent(@ModelAttribute("student") @Valid Student student, BindingResult result, Model model) {

        log.info("this is before if " + student);

        if (result.hasErrors()) {
            log.info("this is inside if " + student);
            log.warning("Invalid input");
            return "register";
        }

            model.addAttribute("student", student);
            log.info("this is after if " + student);
            return "confirm";
        }

}
