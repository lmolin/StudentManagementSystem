package org.perscholas.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

//lombok
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
//database
@Entity
//springboot
@Component
public class Student implements Serializable {
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long sId;

    @Column(name= "email", length = 50)
    @NotNull
    @NotBlank
    @Email(regexp = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b",message = "Invalid email address")
    String sEmail;

    @Column(name = "name", length = 50)
    @Size(min= 3, max= 50, message= "Must be between 3 and 50 characters")
    String sName;

    @Column(name = "password", length = 50)
    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", message = "- at least 8 characters\n- must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number\n- Can contain special characters")
    String sPassword;

    @ManyToMany
    @JoinTable(name= "studentcourse",
            joinColumns = @JoinColumn(name = "sId"),
            inverseJoinColumns = @JoinColumn(name = "cId"))
    @ToString.Exclude
    List<Course> sCourses;

}
