package com.praful.projects.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.praful.projects.utils.Constant;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author Prafulla Rathore
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private void getErrorResponse(HttpServletResponse httpServletResponse, String errorMessage)
        throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(httpServletResponse.getOutputStream(), errorMessage);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(Constant.AUTHORIZATION);
        if (requestTokenHeader != null && requestTokenHeader.startsWith(Constant.BEARER)
            && SecurityContextHolder.getContext().getAuthentication() == null) {
            String token = requestTokenHeader.substring(7);

            String name = null;
            try {
                name = jwtTokenUtil.getUsernameFromToken(token);
            } catch (ExpiredJwtException e) {
                getErrorResponse(response, "Invalid Access Token");
                return;
            }

            if (!ObjectUtils.isEmpty(name) && jwtTokenUtil.validateToken(token, name)) {
                UserInfo userInfo = (UserInfo) userDetailsService.loadUserByUsername(name);
                if (ObjectUtils.isEmpty(userInfo)) {
                    getErrorResponse(response, "User Not Exists");
                    return;
                }
                if (StringUtils.isEmpty(userInfo.getToken())) {
                    getErrorResponse(response, "Invalid Access Token");
                    return;
                }
                if (!userInfo.getToken().equalsIgnoreCase(token)) {
                    getErrorResponse(response, "Invalid Access Token");
                    return;
                }
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userInfo, null, userInfo.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
