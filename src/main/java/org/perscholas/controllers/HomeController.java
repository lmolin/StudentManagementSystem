package org.perscholas.controllers;

import lombok.extern.java.Log;
import org.perscholas.models.Course;
import org.perscholas.models.LoginInfos;
import org.perscholas.models.Student;
import org.perscholas.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Log
@Controller
@SessionAttributes({"logininfos", "student"})
public class HomeController {

    @Autowired
    private StudentService studentService;

    /*
            - controllers should be separated e.g. @RequestMapping("admin"), @RequestMapping("student")
            - provide as much as possible e.g. get/post/put/delete mappings
     */

    @ModelAttribute("logininfos")
    public LoginInfos initLogininfos(){
        return new LoginInfos(null, null, null, false);
    }

    @ModelAttribute("email")
    public String initEmail(){
        return "";
    }

    @ModelAttribute("password")
    public String initPassword(){
        return "";
    }

    @ModelAttribute("student")
    public Student initStudent(){
        return new Student();
    }

    @GetMapping({"/template", "/"})
    public String template(){
        return "template";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String authentification(@RequestParam("email") @Valid String email,
                                   @RequestParam("password") @Valid String password,
                                   Model model){

        Student student = studentService.findByEmail(email);
        if(email.isBlank() && password.isBlank()){
            model.addAttribute("logininfosRequired", "Email and Password fields are required");
            return "login";
        }
        if(student == null){
            model.addAttribute("emailNotFound", "Email enterred does not exists in the databases");
            return "login";
        }
        if(!email.equalsIgnoreCase(student.getSEmail()) || !password.equals(student.getSPassword())){
            model.addAttribute("LoginError", "Email/Password combination is incorrect");
            return "login";
        }
        LoginInfos loginInfos = new LoginInfos(student.getSEmail(), student.getSPassword(), student.getSRole(), true);
        model.addAttribute("logininfos", loginInfos);
        model.addAttribute("student", student);
        return "redirect:student/registertocourse/"+student.getSId();
    }

}
