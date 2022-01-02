package backend.core.post.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PostStatus {
    ACCESS("게시글 게시 승인"),
    CANCEL("게시글 게시 취소"),
    DONE("장바구니 약속 완료");

    private String description;
}

