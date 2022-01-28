package backend.core.member;

import backend.core.domain.Profile;
import backend.core.member.domain.Member;
import backend.core.member.dto.MemberUpdateRequestDto;
import backend.core.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/profile/{memberId}")
    public String profileV1(@PathVariable Long memberId) {
        Member member = memberService.findByIdOrThrow(memberId);
        return member.getProfile().getStoreFileName();
    }

    @PostMapping("/profile/{memberId}")
    public String saveProfile(
            @PathVariable Long memberId,
            @RequestParam MultipartFile file) throws IOException {
        String storeFilename = createStoreFilename(file.getOriginalFilename());
        String fullPath = "";
        if (!file.isEmpty()) {
            fullPath = fileDir + storeFilename;
            log.info("saveProfile fullPath = {}", fullPath);
            file.transferTo(new File(fullPath));
        }

        Profile profile = createProfile(file, fullPath);
        findMemberAndUpdateProfile(memberId, profile);

        log.info("saveProfile storeFilename = {}, originalFilename = {}, fullPath = {}", storeFilename, file.getOriginalFilename(), fullPath);
        return HttpStatus.OK.toString();
    }

    private void findMemberAndUpdateProfile(Long memberId, Profile profile) {
        Member member = memberService.findByIdOrThrow(memberId);
        MemberUpdateRequestDto dto = new MemberUpdateRequestDto(member.getId(), profile, member.getNickName(), member.getAddress());
        memberService.updateOrThrow(dto);
    }

    private Profile createProfile(MultipartFile file, String fullPath) {
        Profile profile = Profile.builder().uploadFileName(file.getOriginalFilename()).storeFileName(fullPath).build();
        return profile;
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
