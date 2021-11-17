package backend.core.controller.post.dto;

import backend.core.domain.Member;
import backend.core.domain.Post;
import backend.core.domain.PostStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PostRequestDto {

    @Data
    public static class PostCreateRequestDto {

        private Long memberId;
        private String title;
        private String description;
        private String dayOfTheWeek;
        private int maximum;

        private Member member;

        public PostCreateRequestDto(String title, String description, Long memberId, int maximum, String dayOfTheWeek) {
            this.title = title;
            this.description = description;
            this.memberId = memberId;
            this.maximum = maximum;
            this.dayOfTheWeek = dayOfTheWeek;
        }

        public Post toEntity() {
            return Post.builder()
                    .member(member)
                    .title(title)
                    .description(description)
                    .dayOfTheWeek(dayOfTheWeek)
                    .maximum(maximum).build();
        }
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PostUpdateRequestDto {
        private Long postId;
        private String title;
        private String description;
        private String dayOfTheWeek;
        private int maximum;
        private PostStatus status;

        public PostUpdateRequestDto(Long postId, String title, String description, int maximum, PostStatus status, String dayOfTheWeek) {
            this.title = title;
            this.postId = postId;
            this.description = description;
            this.maximum = maximum;
            this.status = status;
            this.dayOfTheWeek = dayOfTheWeek;
        }
    }
}
