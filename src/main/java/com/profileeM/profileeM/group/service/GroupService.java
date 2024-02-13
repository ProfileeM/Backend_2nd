package com.profileeM.profileeM.group.service;

import com.profileeM.profileeM.group.domain.Group;
import com.profileeM.profileeM.group.domain.RequestGroupDTO;
import com.profileeM.profileeM.group.repository.GroupRepository;
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
    public Group createGroup(RequestGroupDTO requestGroupDTO, Long userId) {

        Group newGroup = Group.builder()
                .groupName(requestGroupDTO.getGroupName())
                .theme(requestGroupDTO.getTheme())
                .userId(userId)
                .groupDate(new Date())
                .link("생성된 링크")
                .build();
        
        return groupRepository.save(newGroup);
    }

}
