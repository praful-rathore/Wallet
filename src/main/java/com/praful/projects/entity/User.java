package com.praful.projects.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author Prafulla Rathore
 */
@Entity
@Getter
@Setter
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer"})
@Table(name = "user", indexes = {
    @Index(name = "user_uk1", columnList = "id"),
    @Index(name = "user_uk2", columnList = "user_id")})
public class User {

    @GeneratedValue(generator = "stringSequence", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(
        name = "stringSequence",
        strategy = "com.praful.projects.utils.StringUniqueIdentityGenerator")
    @Column(name = "id", unique = true, length = 64, nullable = false)
    @JsonIgnore
    @Id
    private String id;

    @Column(name = "user_id", length = 64, nullable = false)
    private String userId;

    @JsonIgnore
    @Column(name = "password", length = 256, nullable = false)
    private String password;

    @Column(name = "token", length = 256)
    private String token;

    @Column(name = "balance", length = 64, nullable = false)
    private BigDecimal walletBalance;
}
