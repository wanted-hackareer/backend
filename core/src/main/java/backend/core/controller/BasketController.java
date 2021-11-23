package backend.core.controller;

import backend.core.domain.Basket;
import backend.core.dto.response.BasketResponseDto;
import backend.core.global.response.ApiResponse;
import backend.core.service.BasketService;
import backend.core.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final MemberService memberService;

/*    @PostMapping("/basket")
    public BasketResponseDto saveBasketV1(
            @Valid @RequestBody BasketCreateRequestDto dto) {
        Member member = memberService.findByIdOrThrow(dto.getMemberId());
        dto.setMember(member);

        Long basketId = basketService.save(dto);
        Basket basket = basketService.findByIdOrThrow(basketId);

        return new BasketResponseDto(basket);
    }*/

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
