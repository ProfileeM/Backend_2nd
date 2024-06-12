package com.profileeM.profileeM.notice.service;

import com.profileeM.profileeM.notice.domain.Notice;
import com.profileeM.profileeM.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private final String NOTICE_LIST_CACHE_KEY = "NoticeList";

    public List<Notice> findAllNotices() {
        // Redis 캐시에 공지사항 리스트가 있는지 확인
        List<Notice> cachedNotices = (List<Notice>) redisTemplate.opsForValue().get(NOTICE_LIST_CACHE_KEY);
        if (cachedNotices != null) {
            return cachedNotices;
        } else {
            // Redis 캐시에 없으면 데이터베이스에서 조회하고 캐시에 저장
            List<Notice> notices = noticeRepository.findAll();
            redisTemplate.opsForValue().set(NOTICE_LIST_CACHE_KEY, notices);
            return notices;
        }
    }

    @Async
    public void updateNoticeListCache(){
        List<Notice> notices = noticeRepository.findAll();
        redisTemplate.opsForValue().set(NOTICE_LIST_CACHE_KEY, notices);
    }

    public Notice createNotice(Notice notice){
        Notice createdNotice = new Notice(notice.getTitle(), notice.getContent());
        return noticeRepository.save(createdNotice);
    }

    public Optional<Notice> findNoticeById(Long noticeId) {
        return noticeRepository.findById(noticeId);
    }

    public Optional<Notice> updateNotice(Long noticeId, Notice updatedNotice){
        Optional<Notice> existingNoticeOptional = noticeRepository.findById(noticeId);

        if(existingNoticeOptional.isPresent()){
            Notice existingNotice = existingNoticeOptional.get();
            existingNotice = Notice.builder()
                    .noticeId(existingNotice.getNoticeId())
                    .title(updatedNotice.getTitle())
                    .content(updatedNotice.getContent())
                    .build();
            return Optional.of(noticeRepository.save(existingNotice));
        } else {
            return Optional.empty();
        }
    }


    public boolean deleteNotice(Long noticeId){
        Optional<Notice> existingNoticeOptional = noticeRepository.findById(noticeId);

        if(existingNoticeOptional.isPresent()){
            noticeRepository.delete(existingNoticeOptional.get());
            return true;
        } else {
            return false;
        }
    }

}
