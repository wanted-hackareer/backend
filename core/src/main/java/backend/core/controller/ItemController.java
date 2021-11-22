package backend.core.controller;

import backend.core.domain.Basket;
import backend.core.domain.Item;
import backend.core.dto.response.ItemResponseDto;
import backend.core.service.BasketService;
import backend.core.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static backend.core.dto.request.ItemRequestDto.ItemCreateRequestDto;

@RestController @Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final BasketService basketService;

    @PostMapping("/item")
    public ItemResponseDto save(
            @Valid @RequestBody ItemCreateRequestDto dto) {
        Basket basket = basketService.findByIdOrThrow(dto.getBasketId());
        dto.setBasket(basket);

        Long itemId = itemService.save(dto);
        Item item = itemService.findByIdOrThrow(itemId);
        return new ItemResponseDto(item);
    }
}
