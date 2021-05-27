package org.perscholas.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;


//Lombok
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
//Database
@Entity
//Spring Boot
@Component


public class Course implements Serializable {
    static final long serialVersionUID = 6381462249347345007L;

    /*
          note use annotation  '@ToString.Exclude' for Lists
          ----------------
          - add validation for fields
          - relationships
          - utilities methods if any
   */

    //fields
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long cId;

    @Column(name= "name")
    @Size(min= 3, max= 50, message= "Must be between 3 and 50 characters")
    String cName;

    @Column(name= "instructor", length = 50)
    @Size(min= 3, max= 50, message= "Must be between 3 and 50 characters")
    String cInstructorName;

    @ManyToMany(mappedBy = "sCourses")
    @ToString.Exclude
    List<Student> cStudents;


}
