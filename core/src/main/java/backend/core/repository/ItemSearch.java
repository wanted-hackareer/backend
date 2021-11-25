package backend.core.repository;

import lombok.Getter;

@Getter
public class ItemSearch {

    private String name;

    public ItemSearch(String name) {
        this.name = name;
    }
}
