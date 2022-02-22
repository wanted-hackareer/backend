package backend.core.post.dto;

import backend.core.basket.dto.BasketResponseDto;
import backend.core.Staff.dto.StaffResponseDto;
import backend.core.post.domain.Post;
import backend.core.tag.dto.TagResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String description;
    private String author;
    private Long memberId;
    private String dayOfTheWeek;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // TODO: staffList, tagList 추가
    private BasketResponseDto basket;
    private List<StaffResponseDto> staffList;
    private List<TagResponseDto> tagList;

    public PostResponseDto(Post entity) {
        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        author = entity.getMember().getNickName();
        memberId = entity.getMember().getId();
        dayOfTheWeek = entity.getDayOfTheWeek();
        updatedAt = entity.getUpdatedAt();
        createdAt = entity.getCreatedAt();

        basket = new BasketResponseDto(entity.getMember().getBasket());
        staffList = entity.getStaffList().stream()
                .map(staff -> new StaffResponseDto(staff))
                .collect(Collectors.toList());
        tagList = entity.getTagList().stream()
                .map(tag -> new TagResponseDto(tag))
                .collect(Collectors.toList());
    }
}
