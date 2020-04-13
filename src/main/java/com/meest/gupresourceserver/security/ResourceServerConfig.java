package com.meest.gupresourceserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;


@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    public static final String[] SWAGGER_RESOURCES = {"/v2/api-docs", "/swagger-ui.html", "/webjars/**", "/swagger-ui.html/**", "/swagger-resources/**"};

    @Autowired
    private Environment acceptsProfiles;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] swaggerUrls = acceptsProfiles.acceptsProfiles(Profiles.of("local", "dev")) ? SWAGGER_RESOURCES : new String[]{};
        http.cors().and().authorizeRequests()
                .antMatchers(swaggerUrls).permitAll()
                .antMatchers(HttpMethod.GET, "/gup/public").permitAll()
                .anyRequest().authenticated();
    }

}