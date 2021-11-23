package backend.core.controller;

import backend.core.domain.Basket;
import backend.core.domain.Item;
import backend.core.dto.response.ItemResponseDto;
import backend.core.global.response.ApiResponse;
import backend.core.service.BasketService;
import backend.core.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static backend.core.dto.request.ItemRequestDto.ItemCreateRequestDto;
import static backend.core.dto.request.ItemRequestDto.ItemUpdateRequestDto;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final BasketService basketService;

    @GetMapping("/item/{id}")
    public ItemResponseDto itemV1(
            @PathVariable Long id) {
        Item item = itemService.findByIdOrThrow(id);
        return new ItemResponseDto(item);
    }

    @GetMapping("/items")
    public ApiResponse itemsV1(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "100") int limit) {
        List<Item> items = itemService.findAllOrThrow(offset, limit);

        return ApiResponse.builder().count(items.size()).data(items).build();
    }

    @PostMapping("/item")
    public ItemResponseDto saveItemV1(
            @Valid @RequestBody ItemCreateRequestDto dto) {
        Basket basket = basketService.findByIdOrThrow(dto.getBasketId());
        dto.setBasket(basket);

        Long itemId = itemService.save(dto);
        Item item = itemService.findByIdOrThrow(itemId);
        return new ItemResponseDto(item);
    }

    @PutMapping("/item")
    public ItemResponseDto updateItemV1(
            @Valid @RequestBody ItemUpdateRequestDto dto) {
        Long id = itemService.update(dto);
        Item item = itemService.findByIdOrThrow(id);
        return new ItemResponseDto(item);
    }
}
