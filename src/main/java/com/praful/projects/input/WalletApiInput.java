package com.praful.projects.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.praful.projects.utils.OnWalletRecharge;
import com.praful.projects.utils.OnWalletStatus;
import com.praful.projects.utils.OnWalletTransfer;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.math.NumberUtils;


/**
 * @author Prafulla Rathore
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletApiInput extends UserApiInput {

    @NotNull(message = "TxnId Invalid", groups = {OnWalletStatus.class})
    private String txnId;

    @NotNull(message = "Wallet Amount Invalid", groups = {OnWalletRecharge.class,
        OnWalletTransfer.class})
    private BigDecimal amount;

    private Integer offset = 0;

    public void setAmount(String amount) {
        if (NumberUtils.isNumber(amount)) {
            this.amount = new BigDecimal(amount);
        } else {
            this.amount = BigDecimal.ZERO;
        }
    }

}
