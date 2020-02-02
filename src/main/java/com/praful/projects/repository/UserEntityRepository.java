package com.praful.projects.repository;

import com.praful.projects.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Prafulla Rathore
 */
@Repository
public interface UserEntityRepository extends JpaRepository<User, String> {

    @Query("select user from User user where userId = ?1")
    Optional<User> findByUserId(@Param("userId") String userId);
}
