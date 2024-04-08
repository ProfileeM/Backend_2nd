package com.profileeM.profileeM.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HTTPSStatusController {
    @GetMapping("/healthcheck")
    public String healthcheck() {
        return "OK";
    }
}
