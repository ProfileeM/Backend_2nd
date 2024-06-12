package com.profileeM.profileeM.notice.controller;

import com.profileeM.profileeM.ApiResponse;
import com.profileeM.profileeM.jwt.JwtAuthenticationProvider;
import org.springframework.http.HttpStatus;
import com.profileeM.profileeM.notice.domain.Notice;
import com.profileeM.profileeM.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notices")
@RequiredArgsConstructor
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;
//    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    // 모든 공지사항 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<Notice.NoticeTitleDateDTO>>> getAllNotices() {
        List<Notice.NoticeTitleDateDTO> notices = noticeService.findAllNoticeTitleDates();
//        log.info("Notices\n조회한 userId: {}", jwtAuthenticationProvider.getUserId());
        ApiResponse<List<Notice.NoticeTitleDateDTO>> response = ApiResponse.ok(notices);
        return ResponseEntity.ok(response);
    }

    // 공지사항 하나 조회
    @GetMapping("/{noticeId}")
    public ResponseEntity<ApiResponse<Notice>> getNoticeById(@PathVariable Long noticeId) {
        Optional<Notice> notice = noticeService.findNoticeById(noticeId);

        if (notice.isPresent()) {
//            log.info("Notice\n조회한 noticeId: {} userId: {}", notice.get().getNoticeId(), jwtAuthenticationProvider.getUserId());
            return ResponseEntity.ok(ApiResponse.ok(notice.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(HttpStatus.NOT_FOUND, "공지사항을 찾을 수 없습니다."));
        }
    }

    // 공지사항 생성
    @PostMapping
    public ResponseEntity<ApiResponse<Notice>> createNotice(@RequestBody Notice notice) {
        Notice createdNotice = noticeService.createNotice(notice);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(createdNotice)); // 201 Created
    }

    // 공지사항 수정
    @PutMapping("/{noticeId}")
    public ResponseEntity<ApiResponse<Notice>> updateNotice(
            @PathVariable Long noticeId, @RequestBody Notice noticeDetails) {
        Optional<Notice> updatedNotice = noticeService.updateNotice(noticeId, noticeDetails);

        if (updatedNotice.isPresent()) {
            return ResponseEntity.ok(ApiResponse.ok(updatedNotice.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(HttpStatus.NOT_FOUND, "공지사항을 찾을 수 없습니다."));
        }
    }

    // 공지사항 삭제
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<ApiResponse<Void>> deleteNotice(@PathVariable Long noticeId) {
        boolean isDeleted = noticeService.deleteNotice(noticeId);

        if (isDeleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(HttpStatus.NOT_FOUND, "공지사항을 찾을 수 없습니다."));
        }
    }
}
