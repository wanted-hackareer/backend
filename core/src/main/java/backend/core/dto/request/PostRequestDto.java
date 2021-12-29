package backend.core.dto.request;

import backend.core.domain.Post;
import backend.core.domain.PostStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostRequestDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PostCreateRequestDto {
        private String title;
        private String description;
        private String dayOfTheWeek;
        private int maximum;

        public PostCreateRequestDto(String title, String description, int maximum, String dayOfTheWeek) {
            this.title = title;
            this.description = description;
            this.maximum = maximum;
            this.dayOfTheWeek = dayOfTheWeek;
        }

        public Post toEntity(Member member) {
            return Post.builder()
                    .member(member)
                    .title(title)
                    .description(description)
                    .dayOfTheWeek(dayOfTheWeek)
                    .maximum(maximum).build();
        }
    }

    @Getter
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
