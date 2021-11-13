package backend.core.controller.post.dto;

import backend.core.domain.Member;
import backend.core.domain.Post;
import lombok.Data;

@Data
public class PostCreateRequestDto {

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
