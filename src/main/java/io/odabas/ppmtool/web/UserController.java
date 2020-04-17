package io.odabas.ppmtool.web;

import io.odabas.ppmtool.domain.User;
import io.odabas.ppmtool.services.MapValidationErrorService;
import io.odabas.ppmtool.services.UserService;
import io.odabas.ppmtool.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
        //Validate password match
        userValidator.validate(user,result);

        ResponseEntity<?>  errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;
        User newUser = userService.saveUser(user);

        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);


    }
}
