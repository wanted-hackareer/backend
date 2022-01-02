package backend.core.post.dto;

import backend.core.member.domain.Member;
import backend.core.post.domain.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRequestDto {
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
