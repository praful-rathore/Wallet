package com.praful.projects.impl;

import com.praful.projects.entity.QWallet;
import com.praful.projects.entity.QWalletLeg;
import com.praful.projects.entity.User;
import com.praful.projects.entity.Wallet;
import com.praful.projects.entity.WalletLeg;
import com.praful.projects.enums.Mode;
import com.praful.projects.enums.SourceType;
import com.praful.projects.enums.TxnStatus;
import com.praful.projects.exception.CustomException;
import com.praful.projects.input.WalletApiInput;
import com.praful.projects.repository.UserEntityRepository;
import com.praful.projects.repository.WalletEntityRepository;
import com.praful.projects.service.WalletService;
import com.praful.projects.session.UserInfo;
import com.praful.projects.utils.Utils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Prafulla Rathore
 */
@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletEntityRepository walletEntityRepository;
    @Autowired
    UserEntityRepository userEntityRepository;
    @Autowired
    EntityManager entityManager;

    @Override
    public ResponseEntity recharge(WalletApiInput walletApiInput) {
        UserInfo userInfo = Utils.getUserInfo(walletApiInput);
        Utils.validateAmount(walletApiInput);
        Wallet wallet = new Wallet();
        wallet.setAmount(walletApiInput.getAmount());
        wallet.setMode(Mode.RECHARGE);
        wallet.setTxnStatus(TxnStatus.SUCCESS);

        Set walletLegSet = new HashSet();
        WalletLeg walletLeg = new WalletLeg();
        walletLeg.setSourceType(SourceType.CREDIT);
        walletLeg.setUserId(userInfo.getUserId());
        walletLeg.setWalletId(wallet);
        walletLegSet.add(walletLeg);

        wallet.setWalletLegs(walletLegSet);
        wallet = walletEntityRepository.save(wallet);
        Optional<User> user = userEntityRepository.findByUserId(userInfo.getUserId());
        user.get().setWalletBalance(user.get().getWalletBalance().add(walletApiInput.getAmount()));
        userEntityRepository.save(user.get());
        return ResponseEntity.ok(wallet);
    }

    @Override
    public ResponseEntity transfer(WalletApiInput walletApiInput) {
        UserInfo userInfo = Utils.getUserInfo(walletApiInput);
        Utils.validateAmount(walletApiInput);
        Utils.validateTransferAmount(walletApiInput, userInfo);
        Optional<User> user = userEntityRepository.findByUserId(walletApiInput.getUserId());
        if (!user.isPresent()) {
            throw new CustomException("User Not Exist");
        }
        Wallet transferWallet = new Wallet();
        transferWallet.setAmount(walletApiInput.getAmount());
        transferWallet.setMode(Mode.TRANSFER);
        transferWallet.setTxnStatus(TxnStatus.SUCCESS);

        Set walletLegSet = new HashSet();
        WalletLeg walletLegDebit = new WalletLeg();
        walletLegDebit.setSourceType(SourceType.DEBIT);
        walletLegDebit.setUserId(userInfo.getUserId());
        walletLegDebit.setWalletId(transferWallet);
        walletLegSet.add(walletLegDebit);

        WalletLeg walletLegCredit = new WalletLeg();
        walletLegCredit.setSourceType(SourceType.CREDIT);
        walletLegCredit.setUserId(walletApiInput.getUserId());
        walletLegCredit.setWalletId(transferWallet);
        walletLegSet.add(walletLegCredit);

        transferWallet.setWalletLegs(walletLegSet);

        transferWallet = walletEntityRepository.save(transferWallet);
        if (transferWallet.getTxnStatus().equals(TxnStatus.SUCCESS)) {
            Set userSet = new HashSet();
            Optional<User> user1 = userEntityRepository.findByUserId(userInfo.getUserId());
            user1.get().setWalletBalance(
                user1.get().getWalletBalance().subtract(walletApiInput.getAmount()));
            userSet.add(user1.get());
            Optional<User> user2 = userEntityRepository.findByUserId(walletApiInput.getUserId());
            user2.get().setWalletBalance(
                user2.get().getWalletBalance().add(walletApiInput.getAmount()));
            userSet.add(user2.get());
            userEntityRepository.saveAll(userSet);
        }
        return ResponseEntity.ok(transferWallet);
    }

    @Override
    public ResponseEntity status(WalletApiInput walletApiInput) {
        UserInfo userInfo = Utils.getUserInfo(walletApiInput);
        Wallet wallet = walletEntityRepository.findByTxnId(walletApiInput.getTxnId())
            .orElseThrow(() -> new CustomException("TxnId not Found"));
        Set<WalletLeg> walletLeg = wallet.getWalletLegs();
        for (WalletLeg value : walletLeg) {
            if (value.getUserId().equalsIgnoreCase(userInfo.getUserId())) {
                return ResponseEntity.ok(wallet);
            }
        }
        return ResponseEntity.badRequest().body("TxnId not Found");
    }

    //requirement is not having much more information like ---
    //1. who will do the reversal?
    //2. what will be the reversal time period?
    //3. Full/Partial amount reversal?
    @Override
    public ResponseEntity reverse(WalletApiInput walletApiInput) {
        return null;
    }

    @Override
    public ResponseEntity passbook(WalletApiInput walletApiInput) {
        UserInfo userInfo = Utils.getUserInfo(walletApiInput);
        BooleanBuilder walletBuilder = new BooleanBuilder();
        QWallet qWallet = QWallet.wallet;
        BooleanBuilder walletLegBuilder = new BooleanBuilder();
        QWalletLeg qWalletLeg = new QWalletLeg("walletLegs");
        User user = userEntityRepository.findByUserId(userInfo.getUserId())
            .orElseThrow(() -> new CustomException("UserId not Found"));
        walletLegBuilder.and(qWalletLeg.userId.eq(user.getUserId()));
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        JPAQuery query = queryFactory.from(qWallet)
            .join(qWallet.walletLegs, qWalletLeg).distinct()
            .where(walletBuilder.and(walletLegBuilder));
        query.limit(20)
            .offset(walletApiInput.getOffset() * 20);
        QueryResults walletList = query.fetchResults();
        return ResponseEntity.ok(walletList);
    }
}
