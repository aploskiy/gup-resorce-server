package com.meest.gupresourceserver.util.userinfo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class UserInfo {

    private static ResourceServerTokenServices resourceServerTokenServices;

    public static final String ROLE_PREFIX = "ROLE_";
    public static final String USER_ID = "user_id";


    private UserInfo() {
    }

    public static Long getId() {
        return Optional.ofNullable(getProperty(USER_ID)).map(Long::valueOf).orElse(null);
    }

    private static String getProperty(String propertyName) {
        String tokenValue = getToken();
        if (Objects.isNull(tokenValue)) {
            return null;
        }
        Map<String, Object> additionalInformation = resourceServerTokenServices.readAccessToken(tokenValue).getAdditionalInformation();
        Object result = additionalInformation.get(propertyName);
        if (result == null) {
            throw new IllegalArgumentException("Invalid OAuth2 Token details key: " + propertyName);
        }

        return String.valueOf(additionalInformation.get(propertyName));
    }

    private static String getToken() {
        OAuth2Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) authentication.getDetails();
        return oAuth2AuthenticationDetails.getTokenValue();
    }

    public static boolean hasRole(String roleName) {
        String authorityle = ROLE_PREFIX + roleName;
        return getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authorityle::equals);
    }

    private static OAuth2Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            return (OAuth2Authentication) authentication;
        }
        return null;
    }

    static void setResourceServerTokenServices(ResourceServerTokenServices resourceServerTokenServices) {
        UserInfo.resourceServerTokenServices = resourceServerTokenServices;
    }


}