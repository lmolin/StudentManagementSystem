package org.perscholas.services;

import org.perscholas.dao.ICourseRepo;
import org.perscholas.dao.IStudentRepo;
import org.perscholas.models.Course;
import org.perscholas.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public Student findByEmail(String email) {
        return repo.findBysEmail(email);
    }

    public Student getStudentById(Long id) {
        return repo.getById(id);
    }

    public Student saveStudent(Student s) {
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
