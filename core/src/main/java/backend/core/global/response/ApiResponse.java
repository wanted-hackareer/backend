package backend.core.global.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private int count;
    private T data;

    @Builder
    public ApiResponse(int count, T data) {
        this.count = count;
        this.data = data;
    }
}