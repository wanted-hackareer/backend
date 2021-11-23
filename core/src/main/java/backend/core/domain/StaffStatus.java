package backend.core.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StaffStatus {
    DENIED("게시글 요청 거절"),
    WAIT("게시글 신청"),
    ACCESS("게시글 신청 승인"),
    CANCEL("게시글 신청 취소");

    private String description;
}
