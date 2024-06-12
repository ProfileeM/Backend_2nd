package com.profileeM.profileeM.notice.service;

import com.profileeM.profileeM.notice.domain.Notice;
import com.profileeM.profileeM.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private final String NOTICE_CACHE_KEY_PREFIX = "Notice:";
    private final String NOTICE_LIST_CACHE_KEY = "NoticeList";

    // 공지사항 목록에서 제목과 생성일자를 가져오기
    public List<Notice.NoticeTitleDateDTO> findAllNoticeTitleDates() {
        List<String> noticeIds = (List<String>) redisTemplate.opsForValue().get(NOTICE_LIST_CACHE_KEY);
        if (noticeIds != null && !noticeIds.isEmpty()) {
            return noticeIds.stream()
                    .map(id -> (Notice) redisTemplate.opsForValue().get(NOTICE_CACHE_KEY_PREFIX + id))
                    .map(notice -> new Notice.NoticeTitleDateDTO(notice.getNoticeId(), notice.getTitle(), notice.getCreationDate()))
                    .collect(Collectors.toList());
        } else {
            List<Notice> notices = noticeRepository.findAll();
            redisTemplate.opsForValue().set(NOTICE_LIST_CACHE_KEY, notices.stream().map(notice -> notice.getNoticeId().toString()).collect(Collectors.toList()));
            notices.forEach(notice -> redisTemplate.opsForValue().set(NOTICE_CACHE_KEY_PREFIX + notice.getNoticeId(), notice));
            return notices.stream()
                    .map(notice -> new Notice.NoticeTitleDateDTO(notice.getNoticeId(), notice.getTitle(), notice.getCreationDate()))
                    .collect(Collectors.toList());
        }
    }

    // 공지사항 세부 내용 가져오기
    public Optional<Notice> findNoticeById(Long noticeId) {
        Notice cachedNotice = (Notice) redisTemplate.opsForValue().get(NOTICE_CACHE_KEY_PREFIX + noticeId);
        if (cachedNotice != null) {
            return Optional.of(cachedNotice);
        } else {
            Optional<Notice> noticeOptional = noticeRepository.findById(noticeId);
            noticeOptional.ifPresent(notice -> redisTemplate.opsForValue().set(NOTICE_CACHE_KEY_PREFIX + notice.getNoticeId(), notice));
            return noticeOptional;
        }
    }

//    public List<Notice> findAllNotices() {
//        // Redis 캐시에 공지사항 리스트가 있는지 확인
//        List<Notice> cachedNotices = (List<Notice>) redisTemplate.opsForValue().get(NOTICE_LIST_CACHE_KEY);
//        if (cachedNotices != null) {
//            return cachedNotices;
//        } else {
//            // Redis 캐시에 없으면 데이터베이스에서 조회하고 캐시에 저장
//            List<Notice> notices = noticeRepository.findAll();
//            redisTemplate.opsForValue().set(NOTICE_LIST_CACHE_KEY, notices);
//            return notices;
//        }
//    }

    public Notice createNotice(Notice notice) {
        Notice createdNotice = new Notice(notice.getTitle(), notice.getContent());
        Notice savedNotice = noticeRepository.save(createdNotice);
        redisTemplate.opsForValue().set(NOTICE_CACHE_KEY_PREFIX + savedNotice.getNoticeId(), savedNotice);

        // 공지사항 ID 리스트 업데이트
        List<String> noticeIds = (List<String>) redisTemplate.opsForValue().get(NOTICE_LIST_CACHE_KEY);
        if (noticeIds != null) {
            noticeIds.add(savedNotice.getNoticeId().toString());
        } else {
            noticeIds = List.of(savedNotice.getNoticeId().toString());
        }
        redisTemplate.opsForValue().set(NOTICE_LIST_CACHE_KEY, noticeIds);

        return savedNotice;
    }

    public Optional<Notice> updateNotice(Long noticeId, Notice updatedNotice) {
        Optional<Notice> existingNoticeOptional = noticeRepository.findById(noticeId);

        if (existingNoticeOptional.isPresent()) {
            Notice existingNotice = existingNoticeOptional.get();
            existingNotice = Notice.builder()
                    .noticeId(existingNotice.getNoticeId())
                    .title(updatedNotice.getTitle())
                    .content(updatedNotice.getContent())
                    .creationDate(existingNotice.getCreationDate()) // 생성일자는 그대로 유지
                    .build();
            Notice savedNotice = noticeRepository.save(existingNotice);
            redisTemplate.opsForValue().set(NOTICE_CACHE_KEY_PREFIX + savedNotice.getNoticeId(), savedNotice);

            // 공지사항 ID 리스트 업데이트
            List<String> noticeIds = (List<String>) redisTemplate.opsForValue().get(NOTICE_LIST_CACHE_KEY);
            if (noticeIds != null) {
                // 해당 공지사항의 ID가 캐시에 있다면 갱신
                if (noticeIds.contains(noticeId.toString())) {
                    // 기존 ID를 제거하고 수정된 공지사항의 ID를 다시 추가
                    noticeIds.remove(noticeId.toString());
                    noticeIds.add(savedNotice.getNoticeId().toString());
                    redisTemplate.opsForValue().set(NOTICE_LIST_CACHE_KEY, noticeIds);
                }
            }

            return Optional.of(savedNotice);
        } else {
            return Optional.empty();
        }
    }

//    @Async
//    public void updateNoticeListCache(){
//        List<Notice> notices = noticeRepository.findAll();
//        redisTemplate.opsForValue().set(NOTICE_LIST_CACHE_KEY, notices);
//    }

//    public Notice createNotice(Notice notice){
//        Notice createdNotice = new Notice(notice.getTitle(), notice.getContent());
//        return noticeRepository.save(createdNotice);
//    }

//    public Optional<Notice> findNoticeById(Long noticeId) {
//        return noticeRepository.findById(noticeId);
//    }

//    public Optional<Notice> updateNotice(Long noticeId, Notice updatedNotice){
//        Optional<Notice> existingNoticeOptional = noticeRepository.findById(noticeId);
//
//        if(existingNoticeOptional.isPresent()){
//            Notice existingNotice = existingNoticeOptional.get();
//            existingNotice = Notice.builder()
//                    .noticeId(existingNotice.getNoticeId())
//                    .title(updatedNotice.getTitle())
//                    .content(updatedNotice.getContent())
//                    .build();
//            return Optional.of(noticeRepository.save(existingNotice));
//        } else {
//            return Optional.empty();
//        }
//    }

    public boolean deleteNotice(Long noticeId) {
        Optional<Notice> existingNoticeOptional = noticeRepository.findById(noticeId);

        if (existingNoticeOptional.isPresent()) {
            noticeRepository.delete(existingNoticeOptional.get());
            redisTemplate.delete(NOTICE_CACHE_KEY_PREFIX + noticeId);

            // 공지사항 ID 리스트 업데이트
            List<String> noticeIds = (List<String>) redisTemplate.opsForValue().get(NOTICE_LIST_CACHE_KEY);
            if (noticeIds != null) {
                noticeIds.remove(noticeId.toString());
                redisTemplate.opsForValue().set(NOTICE_LIST_CACHE_KEY, noticeIds);
            }

            return true;
        } else {
            return false;
        }
    }


//    public boolean deleteNotice(Long noticeId){
//        Optional<Notice> existingNoticeOptional = noticeRepository.findById(noticeId);
//
//        if(existingNoticeOptional.isPresent()){
//            noticeRepository.delete(existingNoticeOptional.get());
//            return true;
//        } else {
//            return false;
//        }
//    }

}
