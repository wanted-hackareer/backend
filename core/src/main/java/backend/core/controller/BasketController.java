package backend.core.controller;

import backend.core.domain.Basket;
import backend.core.dto.response.BasketResponseDto;
import backend.core.global.response.ApiResponse;
import backend.core.service.BasketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static backend.core.dto.response.BasketResponseDto.BasketDeleteResponseDto;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @DeleteMapping("/basket/{id}")
    public BasketDeleteResponseDto deleteBasketV1(
            @PathVariable Long id) {
        return new BasketDeleteResponseDto(basketService.delete(id));
    }

    @GetMapping("/baskets")
    public ApiResponse findAllBasketV1(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "100") int limit) {
        List<Basket> basketList = basketService.findAllOrThrow(offset, limit);
        List<BasketResponseDto> result = basketList.stream()
                .map(basket -> new BasketResponseDto(basket))
                .collect(Collectors.toList());

        return ApiResponse.builder().count(result.size()).data(result).build();
    }

    @GetMapping("/basket/{id}")
    public BasketResponseDto findByIdBasketV1(
            @PathVariable Long id) {
        Basket basket = basketService.findByIdOrThrow(id);
        return new BasketResponseDto(basket);
    }
}
