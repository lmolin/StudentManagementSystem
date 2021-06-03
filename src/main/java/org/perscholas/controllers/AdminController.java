package org.perscholas.controllers;

import org.perscholas.models.Course;
import org.perscholas.models.Student;
import org.perscholas.services.CourseService;
import org.perscholas.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mkemiche
 * @created 29/05/2021
 */

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    CourseService courseService;

    @Autowired
    StudentService studentService;

    @ModelAttribute("course")
    public Course initCourse(){
        return new Course();
    }
    @ModelAttribute("student")
    public Student initStudent(){
        return new Student();
    }

    @ModelAttribute("sId")
    public Long initStudentId(){
        return 0l;
    }

    @ModelAttribute("sCourses")
    public List<Course> initCourses(){
        return new ArrayList<>();
    }

    @GetMapping("/getallcourses")
    public String getAllCourses(Model model){
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @GetMapping("/getallstudents")
    public String getAllStudents(Model model){
        List<Student> students = studentService.getAllStudents();
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("students", students);
        model.addAttribute("courses", courses);
        return "students";
    }

    @PostMapping("/registerstudenttocourse")
    public String registerStudentToCourse(@RequestParam("sId") Long id,
                                          @RequestParam("sCourses") List<Course> courses,
                                          Model model){

        if((id == null || id == 0) && courses.isEmpty()){
            model.addAttribute("error", "an error is occurred");
            return "students";
        }

        Student student = studentService.getStudentById(id);
        student.getSCourses().forEach(courses::add);
        student.setSCourses(courses);
        studentService.saveStudent(student);
        return "redirect:../admin/getallstudents";
    }
}
