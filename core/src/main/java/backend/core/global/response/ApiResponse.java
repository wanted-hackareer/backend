package backend.core.global.response;

public class ApiResponse<T> {
    private int count;
    private T data;

    public ApiResponse(int count, T data) {
        this.count = count;
        this.data = data;
    }
}