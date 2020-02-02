package com.praful.projects.controller;

import com.praful.projects.input.WalletApiInput;
import com.praful.projects.service.WalletService;
import com.praful.projects.utils.Constant;
import com.praful.projects.utils.OnWalletRecharge;
import com.praful.projects.utils.OnWalletReverse;
import com.praful.projects.utils.OnWalletStatus;
import com.praful.projects.utils.OnWalletTransfer;
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
@RequestMapping(value = Constant.API + Constant.WALLET, method = RequestMethod.POST,
    consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class WalletController {

    @Autowired
    private WalletService walletService;

    @RequestMapping(value = Constant.ACTION_RECHARGE)
    @Validated(OnWalletRecharge.class)
    public ResponseEntity recharge(@RequestBody @Valid WalletApiInput walletApiInput) {
        return new ResponseEntity<>(walletService.recharge(walletApiInput), HttpStatus.OK);
    }

    @RequestMapping(value = Constant.ACTION_TRANSFER)
    @Validated(OnWalletTransfer.class)
    public ResponseEntity transfer(@RequestBody @Valid WalletApiInput walletApiInput) {
        return new ResponseEntity<>(walletService.transfer(walletApiInput), HttpStatus.OK);
    }

    @RequestMapping(value = Constant.ACTION_STATUS)
    @Validated(OnWalletStatus.class)
    public ResponseEntity status(@RequestBody @Valid WalletApiInput walletApiInput) {
        return new ResponseEntity<>(walletService.status(walletApiInput), HttpStatus.OK);
    }

    @RequestMapping(value = Constant.ACTION_REFUND)
    @Validated(OnWalletReverse.class)
    public ResponseEntity refund(@RequestBody @Valid WalletApiInput walletApiInput) {
        return new ResponseEntity<>(walletService.reverse(walletApiInput), HttpStatus.OK);
    }

    @RequestMapping(value = Constant.ACTION_PASSBOOK)
    public ResponseEntity passbook(@RequestBody @Valid WalletApiInput walletApiInput) {
        return new ResponseEntity<>(walletService.passbook(walletApiInput), HttpStatus.OK);
    }

}
