package io.odabas.ppmtool.services;

import io.odabas.ppmtool.domain.User;
import io.odabas.ppmtool.exceptions.UsernameAlreadyExistsException;
import io.odabas.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser (User newUser){

        try{
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            newUser.setUsername(newUser.getUsername());

            //Username has  to be unique (s)

            //Make sure that password  and confirmPassword match
            //We dont persist or show confirmPassword
            return userRepository.save(newUser);
        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exist!");
        }


    }




}
