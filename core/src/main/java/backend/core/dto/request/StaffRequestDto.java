package backend.core.dto.request;

import backend.core.domain.Member;
import backend.core.domain.Post;
import backend.core.domain.Staff;
import backend.core.domain.StaffStatus;
import lombok.Getter;

public class StaffRequestDto {

    @Getter
    public static class StaffCreateRequestDto {
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

        public void setMemberAndPost(Member member, Post post) {
            this.member = member;
            this.post = post;
        }
    }

    @Getter
    public static class StaffUpdateRequestDto {
        private Long staffId;
        private StaffStatus status;

        public StaffUpdateRequestDto(Long staffId, StaffStatus status) {
            this.staffId = staffId;
            this.status = status;
        }
    }
}
