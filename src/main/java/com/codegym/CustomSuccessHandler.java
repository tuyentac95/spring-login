package com.codegym;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetURL = determineTargetUrl(authentication);

        if(response.isCommitted()){
            return;
        }

        redirectStrategy.sendRedirect(request,response,targetURL);
    }

    protected String determineTargetUrl(Authentication authentication) {
        String url;
        Collection<? extends GrantedAuthority> authorityList = authentication.getAuthorities();
        List<String> roles = new ArrayList<>();
        for(GrantedAuthority auth : authorityList){
            roles.add(auth.getAuthority());
        }
        if(roles.contains("ROLE_ADMIN")){
            url = "/admin";
        } else if(roles.contains("ROLE_USER")) {
            url = "/home";
        } else {
            url = "/access-denied";
        }
        return url;
    }


}
