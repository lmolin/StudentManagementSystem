package org.perscholas.security;

import org.perscholas.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mkemiche
 * @created 02/06/2021
 */

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;

    private DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(getPasswordEncoder());
        return provider;
    }

    @Bean
    public static PasswordEncoder getPasswordEncoder() {
        PasswordEncoder defaultEncoder = new BCryptPasswordEncoder();
        String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(defaultEncoder);
        return passwordEncoder;
    }


        @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
            filter process
            1. antMatcher : define the user authority that allows access to url
            2. form login: define login page, user and password form and define access and failure page redirection
            3. logout : clear session, clear authentfication, and clear cookies
            4. exception handling : define denied access page
         */
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAnyAuthority(Role.ROLE_ADMIN.toString())
                .antMatchers("/student/**").hasAuthority(Role.ROLE_STUDENT.toString())
                .antMatchers("/").permitAll()
                .antMatchers("/student/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").usernameParameter("email").passwordParameter("password")
                .loginProcessingUrl("/login/authenticate")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout().invalidateHttpSession(true)
                //.deleteCookies()
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .permitAll()
                .and().exceptionHandling().accessDeniedPage("/403");

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/imgs/**");
    }
}
