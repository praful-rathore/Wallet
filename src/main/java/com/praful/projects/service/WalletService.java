package com.praful.projects.service;

import com.praful.projects.input.WalletApiInput;
import org.springframework.http.ResponseEntity;

/**
 * @author Prafulla Rathore
 */
public interface WalletService {

    ResponseEntity recharge(WalletApiInput walletApiInput);

    ResponseEntity transfer(WalletApiInput walletApiInput);

    ResponseEntity status(WalletApiInput walletApiInput);

    ResponseEntity reverse(WalletApiInput walletApiInput);

    ResponseEntity passbook(WalletApiInput walletApiInput);

}
