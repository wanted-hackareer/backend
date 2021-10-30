package backend.core.web.api;

import javassist.NotFoundException;
import lombok.Data;

public class BaseApiController {

    @Data
    protected class Result<T> {
        private int count;
        private T data;

        public Result(int count, T data) throws NotFoundException {
            if (count == 0){
                throw new NotFoundException("조회 내역이 없습니다.");
            }
            this.count = count;
            this.data = data;
        }
    }
}
