package backend.core.dto.request;

import backend.core.domain.Basket;
import backend.core.domain.Member;
import lombok.Data;

public class BasketRequestDto {

    @Data
    public static class BasketCreateRequestDto {
        private Long memberId;

        private Member member;

        public BasketCreateRequestDto(Long memberId) {
            this.memberId = memberId;
        }

        public Basket toEntity() {
            return Basket.builder()
                    .member(member)
                    .build();
        }
    }
}
