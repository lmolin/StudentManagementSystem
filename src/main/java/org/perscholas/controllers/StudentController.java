package org.perscholas.controllers;

import lombok.extern.java.Log;
import org.perscholas.models.Course;
import org.perscholas.models.LoginInfos;
import org.perscholas.models.Student;
import org.perscholas.services.CourseService;
import org.perscholas.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author mkemiche
 * @created 28/05/2021
 */

@Controller
@Log
@RequestMapping("student")
public class StudentController {

    @Autowired
    StudentService sService;

    @Autowired
    CourseService courseService;

    @ModelAttribute("student")
    public Student initStudent(){
        return new Student();
    }

    @ModelAttribute("course")
    public Course initCourse(){
        return new Course();
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register"; }

    @PostMapping("/register")
    public String registerNewStudent(@ModelAttribute("student") @Valid Student student, BindingResult result, Model model) {

        if (result.hasErrors()) {
            log.warning("Invalid input");
            return "register";
        }

        model.addAttribute("student", student);
        sService.saveStudent(student);
        return "confirm";
    }


    @GetMapping("/registertocourse/{id}")
    public String coursePage(@PathVariable("id") Long id, Model model) {
        Student student = sService.getStudentById(id);
       // Student student = (Student) model.getAttribute("student");

        List<Course> courseList = courseService.getAllCourses();
        model.addAttribute("student", student);
        model.addAttribute("courses", courseList);
        return "course";
    }

    @PostMapping("/progress")
    public String registerStudentToCourse(@RequestParam("sId") Long id,
                                          @RequestParam("sCourses") List<Course> courses,
                                          Model model) {

        Student student = sService.getStudentById(id);
        student.setSCourses(courses);
        sService.saveStudent(student);
        return "confirm";

    }
}
