package com.profileeM.profileeM.notice.service;

import com.profileeM.profileeM.notice.domain.Notice;
import com.profileeM.profileeM.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<Notice> findAllNotices() {
        return noticeRepository.findAll();
    }

    public Notice createNotice(Notice notice){
        Notice createdNotice = new Notice(notice.getTitle(), notice.getContent());
        return noticeRepository.save(createdNotice);
    }

    public Optional<Notice> findNoticeById(Long noticeId) {
        return noticeRepository.findById(noticeId);
    }
}
