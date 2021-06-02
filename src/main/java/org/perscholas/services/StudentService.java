package org.perscholas.services;

import org.perscholas.dao.ICourseRepo;
import org.perscholas.dao.IStudentRepo;
import org.perscholas.models.Course;
import org.perscholas.models.Student;
import org.perscholas.security.AppSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    /*
            - add class annotations
            - add @Transactional on class or on each method
            - add crud methods
     */

    @Autowired
    IStudentRepo repo;
    @Autowired
    ICourseRepo cRepo;



    public List<Student> getAllStudents() {
        return repo.findAll();
    }

    public Optional<Student> findByEmail(String email) {
        return repo.findBysEmail(email);
    }

    public Student getStudentById(Long id) {
        return repo.getById(id);
    }

    public Student saveStudent(Student s) {

        s.setSPassword(AppSecurityConfig.getPasswordEncoder().encode(s.getSPassword()));
        return repo.save(s);
    }

    public Student removeStudent(Student s) {
        repo.delete(s);
        return s;
    }

    public boolean registerStudentToCourse(Student s, Course c) {


        List<Course> courses = cRepo.findAllCourseBycStudents(s);

        if(courses.contains(c)) {
            return false;
        }

        courses.add(c);
        s.setSCourses(courses);
        repo.save(s);
        return true;
    }
}
