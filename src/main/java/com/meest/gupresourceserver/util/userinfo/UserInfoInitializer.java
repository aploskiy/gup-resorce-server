package com.meest.gupresourceserver.util.userinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserInfoInitializer {

    @Autowired
    private ResourceServerTokenServices resourceServerTokenServices;


    @PostConstruct
    public void initialize() {
        UserInfo.setResourceServerTokenServices(resourceServerTokenServices);
    }

}