package backend.core.service;

import backend.core.domain.Basket;
import backend.core.global.error.exception.CustomException;
import backend.core.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.core.dto.request.BasketRequestDto.BasketCreateRequestDto;
import static backend.core.global.error.exception.ErrorCode.BASKET_NOT_FOUND;

@Slf4j @Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasketService {

    private final BasketRepository basketRepository;

    @Transactional
    public Long save(BasketCreateRequestDto dto) {
        Basket basket = dto.toEntity();
        basketRepository.save(basket);

        return basket.getId();
    }

    public Basket findById(Long id) {
        Basket basket = basketRepository.findById(id)
                .orElseThrow(() -> new CustomException(BASKET_NOT_FOUND));

        return basket;
    }

    public List<Basket> findAll(int offset, int limit) {
        List<Basket> baskets = basketRepository.findAll(offset, limit)
                .orElseThrow(() -> new CustomException(BASKET_NOT_FOUND));
        return baskets;
    }
}
