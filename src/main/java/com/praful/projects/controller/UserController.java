package com.praful.projects.controller;

import com.praful.projects.input.UserApiInput;
import com.praful.projects.service.UserService;
import com.praful.projects.utils.Constant;
import com.praful.projects.utils.OnUserSignIn;
import com.praful.projects.utils.OnUserSignUp;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Prafulla Rathore
 */
@RestController
@Validated
@RequestMapping(value = Constant.API + Constant.USER, method = RequestMethod.POST,
    consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = Constant.ACTION_SIGN_IN)
    @Validated(OnUserSignIn.class)
    public ResponseEntity signIn(@RequestBody @Valid UserApiInput userApiInput) {
        return new ResponseEntity<>(userService.signIn(userApiInput), HttpStatus.OK);
    }

    @RequestMapping(value = Constant.ACTION_SIGN_UP)
    @Validated(OnUserSignUp.class)
    public ResponseEntity signUp(@RequestBody @Valid UserApiInput userApiInput) {
        return new ResponseEntity<>(userService.signUp(userApiInput), HttpStatus.OK);
    }

    @RequestMapping(value = Constant.ACTION_SIGN_OUT)
    public ResponseEntity signOut(@RequestBody @Valid UserApiInput userApiInput) {
        return new ResponseEntity<>(userService.signOut(userApiInput), HttpStatus.OK);
    }

}
