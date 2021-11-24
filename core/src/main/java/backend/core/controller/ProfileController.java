package backend.core.controller;

import backend.core.domain.Member;
import backend.core.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
        Member member = memberService.findByIdOrThrow(id);
        return member.getProfile().getStoreFileName();
    }

    @PostMapping("/profile")
    public String saveProfile(
            @RequestParam MultipartFile file) throws IOException {
        String storeFilename = createStoreFilename(file.getOriginalFilename());
        log.info("saveProfile storeFilename = {}", storeFilename);
        log.info("saveProfile originalFilename = {}", file.getOriginalFilename());

        if (!file.isEmpty()) {
            String fullPath = fileDir + storeFilename;
            log.info("saveProfile fullPath = {}", fullPath);
            file.transferTo(new File(fullPath));
        }
        return storeFilename;
    }

    private String createStoreFilename(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
