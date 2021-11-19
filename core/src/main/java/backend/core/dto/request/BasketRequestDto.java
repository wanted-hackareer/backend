package backend.core.dto.request;

import backend.core.domain.Basket;
import backend.core.domain.Member;
import lombok.Getter;

public class BasketRequestDto {

    @Getter
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

        public void setMember(Member member) {
            this.member = member;
        }
    }
}
