package org.perscholas.controllers;

import lombok.extern.java.Log;
import org.perscholas.models.Course;
import org.perscholas.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author mkemiche
 * @created 28/05/2021
 */

@Controller
@RequestMapping("course")
@Log
public class CourseController {

    @Autowired
    CourseService courseService;


    @ModelAttribute("course")
    public Course initCourse(){
        return new Course();
    }

    @PostMapping("/register")
    public String saveCourse(@ModelAttribute("course") @Valid Course course, BindingResult result, Model model){
        if (result.hasErrors()) {
            log.warning("Invalid input");
            return "courses";
        }

        model.addAttribute("course", course);
        courseService.saveCourse(course);
        return "redirect:../admin/getallcourses";
    }

}
