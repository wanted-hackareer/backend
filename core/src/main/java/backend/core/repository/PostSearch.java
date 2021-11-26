package backend.core.repository;

import backend.core.domain.PostStatus;
import lombok.Getter;

@Getter
public class PostSearch {
    private String title;
    private String streetA;
    private String streetB;
    private PostStatus status;

    public PostSearch(String title, String streetA, String streetB, PostStatus status) {
        this.title = title;
        this.streetA = streetA;
        this.streetB = streetB;
        this.status = status;
    }
}
