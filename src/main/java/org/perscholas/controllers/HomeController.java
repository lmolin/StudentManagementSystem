package org.perscholas.controllers;

import lombok.extern.java.Log;
import org.perscholas.exception.StudentNotFoundException;
import org.perscholas.models.Course;
import org.perscholas.models.LoginInfos;
import org.perscholas.models.Student;
import org.perscholas.security.AppUserDetails;
import org.perscholas.security.AppUserDetailsService;
import org.perscholas.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
@Controller
@SessionAttributes({"logininfos", "student"})
public class HomeController {

    @Autowired
    private StudentService studentService;

    @Autowired
    AppUserDetailsService appUserDetailsService;

    /*
            - controllers should be separated e.g. @RequestMapping("admin"), @RequestMapping("student")
            - provide as much as possible e.g. get/post/put/delete mappings
     */

    @ModelAttribute("logininfos")
    public LoginInfos initLogininfos(){
        return new LoginInfos(null, null, null);
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
    public String template(Model model, @AuthenticationPrincipal AppUserDetails details) throws StudentNotFoundException {
        if(details == null){
            return "template";
        }
        Optional<Student> student = studentService.findByEmail(details.getUsername());

        if(student.isEmpty()){
            throw new StudentNotFoundException("student not found");
        }

        log.severe("current user is : "+details);
        log.severe("current student is : "+student);
        model.addAttribute("student", student.get());
        return "template";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

//    @PostMapping("/login/process")
//    public String authentification(@RequestParam("email") @Valid String email,
//                                   @RequestParam("password") @Valid String password,
//                                   Model model) throws StudentNotFoundException {
//
//
//        UserDetails userDetails = appUserDetailsService.loadUserByUsername(email);
//        Optional<Student> student = studentService.findByEmail(userDetails.getUsername());
//
//        if(student.isEmpty()){
//            throw new StudentNotFoundException("Student not found");
//        }
//
//        return "redirect:student/registertocourse/"+student.get().getSId();
//    }

}
