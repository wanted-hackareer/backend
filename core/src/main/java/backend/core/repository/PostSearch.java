package backend.core.repository;

import backend.core.domain.PostStatus;
import lombok.Getter;

@Getter
public class PostSearch {
    private String title;
    private String district;
    private PostStatus status;
}
