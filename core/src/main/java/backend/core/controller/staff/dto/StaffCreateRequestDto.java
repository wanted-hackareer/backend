package backend.core.controller.staff.dto;

import backend.core.domain.Member;
import backend.core.domain.Post;
import backend.core.domain.Staff;
import lombok.Data;

@Data
public class StaffCreateRequestDto {
    private Long postId;
    private Long memberId;

    private Member member;
    private Post post;

    public StaffCreateRequestDto(Long postId, Long memberId) {
        this.postId = postId;
        this.memberId = memberId;
    }

    public Staff toEntity() {
        return Staff.builder()
                .member(member)
                .post(post)
                .build();
    }
}
