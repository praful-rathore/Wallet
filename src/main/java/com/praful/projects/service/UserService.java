package com.praful.projects.service;

import com.praful.projects.input.UserApiInput;
import org.springframework.http.ResponseEntity;

/**
 * @author Prafulla Rathore
 */
public interface UserService {

    ResponseEntity signIn(UserApiInput apiInput);

    ResponseEntity signUp(UserApiInput apiInput);

    ResponseEntity signOut(UserApiInput apiInput);

}
