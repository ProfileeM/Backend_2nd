package com.profileeM.profileeM.group.service;

import com.profileeM.profileeM.group.domain.Group;
import com.profileeM.profileeM.group.repository.GroupRepository;
import com.profileeM.profileeM.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class GroupService {

    private final GroupRepository groupRepository;

    // 그룹 생성
    public Group createGroup(Group group, Long userId) {

        Group newGroup = Group.builder()
                .groupName(group.getGroupName())
                .theme(group.getTheme())
                .userId(userId)
                .groupDate(new Date())
                .link(group.getLink())
                .build();
        
        return groupRepository.save(newGroup);
    }

}
