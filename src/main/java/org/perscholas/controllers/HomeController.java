package org.perscholas.controllers;

import lombok.extern.java.Log;
import org.perscholas.models.Course;
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

@Log
@Controller
public class HomeController {

    @Autowired
    StudentService sService;

    @Autowired
    CourseService cService;

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

    @GetMapping("/student/course")
    public String registerCourse() {
        return "course";
    }

    @GetMapping("/student/register/{id}")
    public String coursePage(@PathVariable("id") Long id, Model model) {
        Student student = sService.getStudentById(id);
        model.addAttribute("student1", student);
        return "course";
    }

    @PostMapping("/student/register")
    public String registerNewStudent(@ModelAttribute("student") @Valid Student student, BindingResult result, Model model) {

        log.info("this is before if " + student);

        if (result.hasErrors()) {
            log.info("this is inside if " + student);
            log.warning("Invalid input");
            return "register";
        }

            model.addAttribute("student", student);
            sService.saveStudent(student);
            log.info("this is after if " + student);
            return "confirm";
        }

        @PutMapping("student/registerToCourse")
    public String registerStudentToCourse(@ModelAttribute("course") Course course, BindingResult result, Model model) {

            List<Course> courses = cService.getAllCourses();

            model.addAttribute("courses", courses);

            Student student = (Student) model.getAttribute("student1");

            sService.registerStudentToCourse(student, course);

            return "confirm";

        }
}
