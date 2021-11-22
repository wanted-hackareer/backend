package backend.core.controller;

import backend.core.domain.Basket;
import backend.core.domain.Member;
import backend.core.dto.response.BasketResponseDto;
import backend.core.service.BasketService;
import backend.core.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static backend.core.dto.request.BasketRequestDto.BasketCreateRequestDto;

@RestController @Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final MemberService memberService;

    @PostMapping("/basket")
    public BasketResponseDto save(
            @Valid @RequestBody BasketCreateRequestDto dto) {
        Member member = memberService.findByIdOrThrow(dto.getMemberId());
        dto.setMember(member);

        Long basketId = basketService.save(dto);
        Basket basket = basketService.findByIdOrThrow(basketId);

        return new BasketResponseDto(basket);
    }
}
