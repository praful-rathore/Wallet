package com.praful.projects.utils;

import com.praful.projects.exception.CustomException;
import com.praful.projects.input.ApiInput;
import com.praful.projects.input.WalletApiInput;
import com.praful.projects.session.UserInfo;
import java.math.BigDecimal;

/**
 * @author Prafulla Rathore
 */
public class Utils {

    public static UserInfo getUserInfo(ApiInput input) {
        return (UserInfo) input.getAuthenticationPrincipal().getPrincipal();
    }

    public static void validateAmount(WalletApiInput walletApiInput) {
        if (null == walletApiInput.getAmount()
            || walletApiInput.getAmount().compareTo(BigDecimal.ONE) < 0
            || walletApiInput.getAmount().compareTo(Constant.MAX_AMOUNT) >= 0) {
            throw new CustomException("Invalid Amount");
        }
    }

    public static void validateTransferAmount(WalletApiInput walletApiInput, UserInfo userInfo) {
        if (walletApiInput.getAmount().compareTo(userInfo.getWalletBalance()) > 0) {
            throw new CustomException("Invalid Transfer Amount");
        }
    }

}
