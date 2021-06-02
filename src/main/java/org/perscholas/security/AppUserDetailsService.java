package org.perscholas.security;

import lombok.extern.java.Log;
import org.perscholas.dao.IStudentRepo;
import org.perscholas.enums.Role;
import org.perscholas.models.Student;
import org.perscholas.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author mkemiche
 * @created 02/06/2021
 */

@Service
@Log
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    IStudentRepo iStudentRepo;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Student> student = iStudentRepo.findBysEmail(s);
        if(student.isEmpty()){
            throw new UsernameNotFoundException("Email does not found in the database");
        }

        log.severe("user by email : "+student);

        List<String> roleList = Arrays.asList(student.get().getSRole().split(","));

        log.severe("roles : "+roleList);

        return new AppUserDetails(student.get(), roleList);
    }
}
