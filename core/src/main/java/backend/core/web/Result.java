package backend.core.web;

import lombok.Data;

@Data
public class Result<T>{
    private int count;
    private T data;

    public Result(int count, T data) {
        this.count = count;
        this.data = data;
    }

}