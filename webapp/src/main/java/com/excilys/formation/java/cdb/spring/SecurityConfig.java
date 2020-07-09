package com.excilys.formation.java.cdb.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

import com.excilys.formation.java.cdb.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    UserService userService;

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        List<User> users = userService.getAll();
//        for (User user : users) {
//            auth.inMemoryAuthentication()
//            .withUser(user.getUsername()).password(user.getPassword()).roles(user.getRole());
//        }
//        auth.inMemoryAuthentication()
//        .withUser("user").password(passwordEncoder.encode("123456")).roles("USER")
//        .and()
//        .withUser("admin").password(passwordEncoder.encode("123456")).roles("ADMIN");
//    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
       
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilter(digestAuthenticationFilter())       
        .authorizeRequests()
        .antMatchers("/login").permitAll()
        .antMatchers(HttpMethod.GET, "/").hasAnyRole("USER", "ADMIN")
        .antMatchers(HttpMethod.POST, "/").hasRole("ADMIN")
        .antMatchers(HttpMethod.GET, "/ListComputers/**").hasAnyRole("USER", "ADMIN")
        .antMatchers(HttpMethod.POST, "/ListComputers/**").hasRole("ADMIN")
        .antMatchers("/AddComputer/**").hasRole("ADMIN")
        .antMatchers("/EditComputer/**").hasRole("ADMIN")
        .and().formLogin()
        .and().csrf().disable();        
    }

    DigestAuthenticationFilter digestAuthenticationFilter() throws Exception {
        DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
        digestAuthenticationFilter.setUserDetailsService(userService);
        digestAuthenticationFilter.setAuthenticationEntryPoint(digestEntryPoint());
        return digestAuthenticationFilter;
    }
    
    @Bean
    DigestAuthenticationEntryPoint digestEntryPoint() {
        DigestAuthenticationEntryPoint bauth = new DigestAuthenticationEntryPoint();
        bauth.setRealmName("digest auth");
        bauth.setKey("MyKey");
        logger.info("TEST");
        return bauth;
    }
}