package org.perscholas.dao;

/*
        - add annotation
        - extend spring jpa
        - add custom methods if needed

 */

import org.perscholas.models.Course;
import org.perscholas.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICourseRepo extends JpaRepository<Course, Long> {

    Course findBycName(String name);

    List<Course> findAllCourseBycStudents(Student student);
}
