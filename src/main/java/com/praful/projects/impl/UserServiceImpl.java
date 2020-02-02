package com.praful.projects.impl;

import com.praful.projects.entity.User;
import com.praful.projects.exception.CustomException;
import com.praful.projects.input.UserApiInput;
import com.praful.projects.repository.UserEntityRepository;
import com.praful.projects.service.UserService;
import com.praful.projects.session.JwtTokenUtil;
import com.praful.projects.session.UserInfo;
import com.praful.projects.utils.Utils;
import io.jsonwebtoken.ExpiredJwtException;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author Prafulla Rathore
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserEntityRepository userEntityRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public ResponseEntity signIn(UserApiInput apiInput) {
        User user = userEntityRepository.findByUserId(apiInput.getUserId())
            .orElseThrow(() -> new CustomException("UserID not Exist"));
        if (!passwordEncoder.matches(apiInput.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid Credentials");
        }
        String newToken = null;
        try {
            if (StringUtils.isEmpty(user.getToken()) ||
                !jwtTokenUtil.validateToken(user.getToken(), user.getUserId())) {
                newToken = jwtTokenUtil.generateToken(user.getUserId(), 86400);
            }
        } catch (ExpiredJwtException expiredJwtException) {
            newToken = jwtTokenUtil.generateToken(user.getUserId(), 86400);
        }
        if (!StringUtils.isEmpty(newToken)) {
            user.setToken(newToken);
            user = userEntityRepository.save(user);
        }
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity signUp(UserApiInput apiInput) {
        Optional<User> user = userEntityRepository.findByUserId(apiInput.getUserId());
        if (user.isPresent()) {
            return ResponseEntity.badRequest().body("UserID already Exist");
        }
        User user1 = new User();
        user1.setWalletBalance(new BigDecimal(0));
        user1.setPassword(passwordEncoder.encode(apiInput.getPassword()));
        user1.setUserId(apiInput.getUserId());
        return ResponseEntity.ok(userEntityRepository.save(user1));
    }

    @Override
    public ResponseEntity signOut(UserApiInput apiInput) {
        UserInfo userInfo = Utils.getUserInfo(apiInput);
        User user = userEntityRepository.findByUserId(userInfo.getUserId()).orElseThrow(() ->
            new CustomException("UserID not Exist"));
        user.setToken(null);
        return ResponseEntity.ok(userEntityRepository.save(user));
    }
}
