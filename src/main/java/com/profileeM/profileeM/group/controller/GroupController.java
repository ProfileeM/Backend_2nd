package com.profileeM.profileeM.group.controller;

import com.profileeM.profileeM.ApiResponse;
import com.profileeM.profileeM.group.domain.Group;
import com.profileeM.profileeM.group.service.GroupService;
import com.profileeM.profileeM.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
@Slf4j
public class GroupController {

    private final GroupService groupService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @PostMapping("/")
    public ApiResponse<?> createGroup(@RequestBody Group group) {
        log.debug(group.toString());
        return ApiResponse.ok(groupService.createGroup(group, jwtAuthenticationProvider.getUserId()));
    }
}
