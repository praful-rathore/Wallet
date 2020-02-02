package com.praful.projects.session;

import com.praful.projects.entity.User;
import com.praful.projects.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Prafulla Rathore
 */
@Service
public class UserInfoServiceImpl implements UserDetailsService {

    @Autowired
    UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userEntityRepository.findByUserId(username).orElse(null);
        if (null != user) {
            return UserInfo.create(user);
        }
        return null;
    }
}
