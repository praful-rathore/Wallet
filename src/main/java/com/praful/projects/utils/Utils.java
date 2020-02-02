package com.praful.projects.utils;

import com.praful.projects.input.ApiInput;
import com.praful.projects.input.UserApiInput;
import com.praful.projects.session.UserInfo;

/**
 * @author Prafulla Rathore
 */
public class Utils {

    public static UserInfo getUserInfo(ApiInput input) {
        return (UserInfo) input.getAuthenticationPrincipal().getPrincipal();
    }

}
