package com.profileeM.profileeM.group.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Schema(description = "Group Request Body")
public class RequestGroupDTO {

    @Schema(description = "그룹 이름")
    private String groupName;

    @Schema(description = "그룹 테마")
    private String theme;
}
