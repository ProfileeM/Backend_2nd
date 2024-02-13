package com.profileeM.profileeM.group.controller;

import com.profileeM.profileeM.ApiResponse;
import com.profileeM.profileeM.group.domain.Group;
import com.profileeM.profileeM.group.service.GroupService;
import com.profileeM.profileeM.jwt.JwtAuthenticationProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Group Controller", description = "그룹 관련 컨트롤러")
public class GroupController {

    private final GroupService groupService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @PostMapping("/")
    @Operation(summary = "그룹 생성", description = "요청한 사용자가 그룹장이 되어 그룹을 생성합니다.")
    public ApiResponse<?> createGroup(@RequestBody @Schema(example = "{ \"groupName\": \"My Group\", \"theme\": \"My Theme\" }")Group group) {
        log.debug(group.toString());
        return ApiResponse.ok(groupService.createGroup(group, jwtAuthenticationProvider.getUserId()));
    }
}
