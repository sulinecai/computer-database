package com.excilys.formation.java.cdb.restcontrollers;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.java.cdb.restcontrollers.security.JwtTokenUtil;
import com.excilys.formation.java.cdb.restcontrollers.security.dto.JwtRequest;
import com.excilys.formation.java.cdb.restcontrollers.security.dto.JwtResponse;

@RestController
@CrossOrigin
public class AuthenticateRestController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(
        value = "/authenticate",
        method = RequestMethod.POST,
        consumes = "application/json")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(),
                authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        final String role = getRole(userDetails).orElse("ROLE_NONE");

        return ResponseEntity.ok(new JwtResponse(token, role));
    }

    private Optional<String> getRole(UserDetails userDetails) {
        Iterator<? extends GrantedAuthority> it =
                userDetails.getAuthorities().iterator();
        if (it.hasNext()) {
            return Optional.of(it.next().getAuthority());
        } else {
            return Optional.empty();
        }
    }

    private void authenticate(String username, String password)
            throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,
                            password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
