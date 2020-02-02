package com.praful.projects.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.praful.projects.utils.OnUserSignIn;
import com.praful.projects.utils.OnUserSignUp;
import com.praful.projects.utils.OnWalletTransfer;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Prafulla Rathore
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserApiInput extends ApiInput {

    @NotNull(message = "UserID Invalid", groups = {OnUserSignUp.class, OnUserSignIn.class,
        OnWalletTransfer.class})
    @Size(min = 10, max = 10, message = "UserID Invalid")
    @Pattern(regexp = "\\d+", message = "UserID Invalid")
    private String userId;

    @NotNull(message = "Password Invalid", groups = {OnUserSignUp.class, OnUserSignIn.class})
    @Size(min = 8, max = 20, message = "Password Invalid", groups = {OnUserSignUp.class,
        OnUserSignIn.class})
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password Invalid")
    private String password;

}
