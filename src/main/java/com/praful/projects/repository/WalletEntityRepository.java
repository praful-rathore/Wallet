package com.praful.projects.repository;

import com.praful.projects.entity.Wallet;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Prafulla Rathore
 */
@Repository
public interface WalletEntityRepository extends JpaRepository<Wallet, String> {

    @Query("select wallet from Wallet wallet where txnId = ?1")
    Optional<Wallet> findByTxnId(@Param("txnId") String txnId);

}
