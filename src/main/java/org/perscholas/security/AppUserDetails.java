package org.perscholas.security;

import org.perscholas.enums.Role;
import org.perscholas.models.Student;
import org.perscholas.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author mkemiche
 * @created 02/06/2021
 */
public class AppUserDetails implements UserDetails {


    Student student;
    List<String> roleList;

    public AppUserDetails(Student student, List<String> roleList) {
        this.student = student;
        this.roleList = roleList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> grList = new ArrayList<>();
        roleList.forEach(role -> grList.add(new SimpleGrantedAuthority(role)));
        return grList;
    }

    @Override
    public String getPassword() {
        return student.getSPassword();
    }

    @Override
    public String getUsername() {
        return student.getSEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
