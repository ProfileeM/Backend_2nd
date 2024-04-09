package com.profileeM.profileeM.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HTTPSStatusController {
    @GetMapping("/healthcheck")
    public String healthcheck() {
        return "OK";
    }
}
