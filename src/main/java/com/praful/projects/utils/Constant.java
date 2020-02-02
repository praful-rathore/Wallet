package com.praful.projects.utils;

import java.math.BigDecimal;

/**
 * @author Prafulla Rathore
 */
public interface Constant {

    String API = "/api";

    String USER = "/user";
    String WALLET = "/wallet";

    String ACTION_SIGN_IN = "/signIn";
    String ACTION_SIGN_UP = "/signUp";
    String ACTION_SIGN_OUT = "/signOut";

    String ACTION_RECHARGE = "/recharge";
    String ACTION_TRANSFER = "/transfer";
    String ACTION_STATUS = "/status";
    String ACTION_REFUND = "/refund";
    String ACTION_PASSBOOK = "/passbook";


    String AUTHORIZATION = "Authorization";
    String BEARER = "Bearer ";

    BigDecimal MAX_AMOUNT = new BigDecimal(99999999999999999.99);

}
