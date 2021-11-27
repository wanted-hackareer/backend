package backend.core.dto.response;

import backend.core.domain.Address;
import backend.core.domain.Staff;
import backend.core.domain.StaffStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StaffResponseDto {
    private Long id;
    private Long memberId;
    private Long postId;
    private StaffStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public StaffResponseDto(Staff entity) {
        id = entity.getId();
        memberId = entity.getMember().getId();
        postId = entity.getPost().getId();
        status = entity.getStaffStatus();
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();
    }

    @Getter
    public static class StaffPostInfoResponseDto {
        private Long id;
        private String title;
        private String description;
        private Address address;

        public StaffPostInfoResponseDto(Staff entity) {
            id = entity.getId();
            title = entity.getPost().getTitle();
            description = entity.getPost().getDescription();
            address = entity.getPost().getAddress();
        }
    }

    @Getter
    public static class StaffMemberInfoResponseDto {
        private Long id;
        private String nickname;
        private Address address;

        public StaffMemberInfoResponseDto(Staff entity) {
            id = entity.getId();
            nickname = entity.getMember().getNickName();
            address = entity.getMember().getAddress();
        }
    }
}
