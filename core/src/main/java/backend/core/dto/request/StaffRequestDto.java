package backend.core.dto.request;

import backend.core.domain.Staff;
import backend.core.domain.StaffStatus;
import backend.core.member.domain.Member;
import backend.core.post.domain.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StaffRequestDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class StaffCreateRequestDto {
        private Long postId;
        private Long memberId;

        public StaffCreateRequestDto(Long postId, Long memberId) {
            this.postId = postId;
            this.memberId = memberId;
        }

        public Staff toEntity(Member member, Post post) {
            return Staff.builder()
                    .member(member)
                    .post(post)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class StaffUpdateRequestDto {
        private Long staffId;
        private StaffStatus status;

        public StaffUpdateRequestDto(Long staffId, StaffStatus status) {
            this.staffId = staffId;
            this.status = status;
        }
    }
}
