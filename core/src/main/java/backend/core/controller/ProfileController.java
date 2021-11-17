package backend.core.controller;

import backend.core.domain.Member;
import backend.core.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProfileController {

    @Value("${file.dir}")
    private String fileDir;

    private final MemberService memberService;

    @GetMapping("/profile/{id}")
    public String profileV1(@PathVariable Long id) {
        Member member = memberService.findById(id);
        return member.getProfile().getStoreFileName();
    }

    @PostMapping("/profile")
    public String saveProfile(@RequestParam String profileName,
                              @RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        log.info("saveProfile profileName = {}", profileName);
        log.info("saveProfile file = {}", file);
        log.info("saveProfile request = {}", request);

        if (!file.isEmpty()) {
            String fullPath = fileDir + file.getOriginalFilename();
            log.info("saveProfile fullPath = {}", fullPath);

            file.transferTo(new File(fullPath));
        }

        return profileName;
    }


}
