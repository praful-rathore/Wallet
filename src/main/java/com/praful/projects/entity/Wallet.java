package com.praful.projects.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.praful.projects.enums.Mode;
import com.praful.projects.enums.TxnStatus;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
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
@Table(name = "wallet", indexes = {
    @Index(name = "wallet_uk1", columnList = "mode"),
    @Index(name = "wallet_uk2", columnList = "txn_status"),
    @Index(name = "wallet_uk3", columnList = "txn_id"),
    @Index(name = "wallet_uk4", columnList = "original_txn_id")

})
public class Wallet {

    @GeneratedValue(generator = "stringSequence", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(
        name = "stringSequence",
        strategy = "com.praful.projects.utils.StringUniqueIdentityGenerator")
    @Column(name = "txn_id", unique = true, length = 64, nullable = false)
    @Id
    private String txnId;

    @Column(name = "original_txn_id")
    private String originalTxnId;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode", length = 40, nullable = false)
    private Mode mode;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "txn_status", length = 60, nullable = false)
    @Enumerated(EnumType.STRING)
    private TxnStatus txnStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "walletId", cascade = CascadeType.ALL,
        orphanRemoval = true)
    private Set<WalletLeg> walletLegs = new HashSet<>();
}
