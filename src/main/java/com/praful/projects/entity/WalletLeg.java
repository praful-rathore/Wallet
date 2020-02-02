package com.praful.projects.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.praful.projects.enums.SourceType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author Prafulla Rathore
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer"})
@Table(name = "wallet_leg", indexes = {
    @Index(name = "wallet_leg_uk1", columnList = "wallet_id"),
    @Index(name = "wallet_leg_uk2", columnList = "wallet_leg_id"),
    @Index(name = "wallet_leg_uk3", columnList = "user_id"),
    @Index(name = "wallet_leg_uk4", columnList = "source_type")
})
public class WalletLeg {

    @GeneratedValue(generator = "stringSequence", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(
        name = "stringSequence",
        strategy = "com.praful.projects.utils.StringUniqueIdentityGenerator")
    @JsonIgnore
    @Id
    @Column(name = "wallet_leg_id", nullable = false)
    private String walletLegId;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet walletId;

    @Column(name = "user_id", length = 256, nullable = false)
    private String userId;

    @Column(name = "source_type", length = 60, nullable = false)
    @Enumerated(EnumType.STRING)
    private SourceType sourceType;


}
