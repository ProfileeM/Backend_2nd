package com.profileeM.profileeM.notice.controller;

import com.profileeM.profileeM.ApiResponse;
import com.profileeM.profileeM.jwt.JwtAuthenticationProvider;
import com.profileeM.profileeM.notice.domain.Notice;
import com.profileeM.profileeM.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;

    // 모든 공지사항 조회
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Notice>>> getAllNotices() {
        List<Notice> notices = noticeService.findAllNotices();
        ApiResponse<List<Notice>> response = ApiResponse.ok(notices);
        return ResponseEntity.ok(response);
    }

    // 공지하상 하나 조회
    @GetMapping("/{noticeId}")
    public ResponseEntity<ApiResponse<Notice>> getNoticeById(@PathVariable Long noticeId) {
        Optional<Notice> notice = noticeService.findNoticeById(noticeId);

        if (notice.isPresent()) {
            log.info("조회한 noticeId: {}", notice.get().getNoticeId());
            return ResponseEntity.ok(ApiResponse.ok(notice.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 공지사항 생성
    // 관리자만 으로 수정
    @PostMapping("/")
    public ApiResponse<?> createNotice(@RequestBody Notice notice) {
        Notice createdNotice = noticeService.createNotice(notice);
        noticeService.updateNoticeListCache();
        return ApiResponse.ok(createdNotice);
    }
}
