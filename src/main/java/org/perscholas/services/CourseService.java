package org.perscholas.services;


import org.perscholas.dao.ICourseRepo;
import org.perscholas.models.Course;
import org.perscholas.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseService {

     /*
            - add class annotations
            - add @Transactional on class or on each method
            - add crud methods
     */
    @Autowired
    ICourseRepo repo;

    public List<Course> getAllCourses() {
        return repo.findAll();
    }

    public Course findByName(String name) {
        return repo.findBycName(name);
    }

    public Course getCoursebyId(Long id) {
        return repo.getById(id);
    }

    public Course saveCourse(Course c) {
        return repo.save(c);
    }

    public Course removeCourse(Course c) {
        repo.delete(c);
        return c;
    }

    public List<Course> findCourseByStudents(Student s) {
      return repo.findAllCourseBycStudents(s);
    }
}
