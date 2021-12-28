package backend.core.basket.service;

import backend.core.basket.domain.Basket;
import backend.core.basket.exception.BasketNotFoundException;
import backend.core.basket.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasketService {

    private final BasketRepository basketRepository;

    @Transactional
    public Long save() {
        Basket basket = Basket.builder().build();
        basketRepository.save(basket);
        return basket.getId();
    }

    @Transactional
    public Long deleteById(Long id) {
        basketRepository.deleteById(id);
        return id;
    }

    public Basket findByIdOrThrow(Long id) {
        Basket basket = basketRepository.findById(id)
                .orElseThrow(() -> new BasketNotFoundException());
        return basket;
    }

    public List<Basket> findAllOrThrow(int offset, int limit) {
        List<Basket> baskets = basketRepository.findAll(offset, limit)
                .orElseThrow(() -> new BasketNotFoundException());
        return baskets;
    }
}
