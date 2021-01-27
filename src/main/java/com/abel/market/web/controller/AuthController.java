package com.abel.market.web.controller;

import com.abel.market.domain.dto.AutenticationRequest;
import com.abel.market.domain.dto.AutenticationResponse;
import com.abel.market.domain.service.MarketUserDetailsService;
import com.abel.market.web.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MarketUserDetailsService marketUserDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<AutenticationResponse> createToken(@RequestBody AutenticationRequest request){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUser(), request.getPassword()));
            UserDetails userDetails = marketUserDetailsService.loadUserByUsername(request.getUser());
            String jwt = jwtUtil.generateToken(userDetails);

            return new ResponseEntity<>(new AutenticationResponse(jwt), HttpStatus.OK);
        }catch(BadCredentialsException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }
}
