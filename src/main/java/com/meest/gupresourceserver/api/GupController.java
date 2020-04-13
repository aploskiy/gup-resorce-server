package com.meest.gupresourceserver.api;

import com.meest.gupresourceserver.util.userinfo.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gup")
public class GupController {

    @GetMapping
    @RequestMapping("/secure")
    public String secureEndpoint() {
        Long id = UserInfo.getId();
        return "secured, user id: " + id;
    }

    @GetMapping
    @RequestMapping("/public")
    public String publicEndpoint() {
        return "public";
    }

}
