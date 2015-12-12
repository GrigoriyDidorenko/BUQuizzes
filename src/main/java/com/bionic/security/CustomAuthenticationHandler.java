package com.bionic.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Sergiy on 11.12.2015.
 */
@Component
public class CustomAuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler {
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String studentTargetUrl = "/pages/UserPage.html";
        String restrictedTargetUrl = "/pages/UserPage.html";;
        String targetUrl=getDefaultTargetUrl();
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        try {
            targetUrl = savedRequest.getRedirectUrl();
        }
        catch (NullPointerException e){
            targetUrl=determineTargetUrl(request,response);
        }

        Collection<? extends GrantedAuthority> authorities =  authentication.getAuthorities();
        List<String> roles = new ArrayList<String>();
        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        if (roles.contains("ROLE_STUDENT")) {
            getRedirectStrategy().sendRedirect(request, response, studentTargetUrl);
        } else if (roles.contains("ROLE_RESTRICTED_ADMINISTRATOR")||(roles.contains("ROLE_RESTRICTED_TRAINER"))) {
            getRedirectStrategy().sendRedirect(request, response, restrictedTargetUrl);
        } else {
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
            super.onAuthenticationSuccess(request,response,authentication);
        }
    }
}
