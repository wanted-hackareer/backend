package backend.core.repository;

import backend.core.domain.PostStatus;
import lombok.Getter;

@Getter
public class PostSearch {
    private String title;
    private String district;
    private PostStatus status;

    public PostSearch(String title, String district, PostStatus status) {
        this.title = title;
        this.district = district;
        this.status = status;
    }
}
