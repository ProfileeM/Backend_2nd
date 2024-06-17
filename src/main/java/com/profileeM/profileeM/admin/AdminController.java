package com.profileeM.profileeM.admin;

import com.profileeM.profileeM.notice.domain.Notice;
import com.profileeM.profileeM.notice.service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final NoticeService noticeService;

    @GetMapping("/admin")
    public String getNoticesAdmin(Model model) {
        List<Notice.NoticeTitleDateDTO> noticeTitleDates = noticeService.findAllNoticeTitleDates();

        model.addAttribute("noticeTitleDates", noticeTitleDates);
        return "admin";
    }

    @GetMapping("/admin/create")
    public String createAdminNotice(@RequestParam(required = false) Long id, Model model) {
        // id가 있으면 수정
        if (id != null) {
            Optional<Notice> notice = noticeService.findNoticeById(id);
            if (notice.isPresent()) {
                model.addAttribute("notice", notice.get());
            }
        }
        // id가 없으면 생성
        else {
            model.addAttribute("notice", new Notice());
        }

        return "admin_create";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}
