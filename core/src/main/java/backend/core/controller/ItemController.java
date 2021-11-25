package backend.core.controller;

import backend.core.domain.Basket;
import backend.core.domain.Item;
import backend.core.dto.response.ItemResponseDto;
import backend.core.global.response.ApiResponse;
import backend.core.repository.ItemSearch;
import backend.core.service.BasketService;
import backend.core.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static backend.core.dto.request.ItemRequestDto.ItemCreateRequestDto;
import static backend.core.dto.request.ItemRequestDto.ItemUpdateRequestDto;

@Slf4j
@RestController
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
        List<Item> itemList = itemService.findAllOrThrow(offset, limit);

        List<ItemResponseDto> result = itemList.stream()
                .map(item -> new ItemResponseDto(item))
                .collect(Collectors.toList());
        return ApiResponse.builder().count(result.size()).data(result).build();
    }

    @GetMapping("/item/search")
    public ApiResponse itemSearchV1(
            @RequestParam(name = "name", defaultValue = "") String name) {
        List<Item> itemList = itemService.findAllBySearch(new ItemSearch(name));

        List<ItemResponseDto> result = itemList.stream()
                .map(item -> new ItemResponseDto(item))
                .collect(Collectors.toList());
        return ApiResponse.builder().count(result.size()).data(result).build();
    }

    @PostMapping("/item")
    public ItemResponseDto saveItemV1(
            @Valid @RequestBody ItemCreateRequestDto dto) {
        return new ItemResponseDto(saveItem(dto));
    }

    @PostMapping("/items")
    public HttpStatus saveItemsV1(
            @Valid @RequestBody List<ItemCreateRequestDto> dtoList) {
        dtoList.stream()
                .map(dto -> saveItem(dto))
                .collect(Collectors.toList());
        return HttpStatus.OK;
    }

    private Item saveItem(ItemCreateRequestDto dto) {
        Basket basket = basketService.findByIdOrThrow(dto.getBasketId());
        dto.setBasket(basket);

        Long itemId = itemService.save(dto);
        Item item = itemService.findByIdOrThrow(itemId);
        return item;
    }

    @PutMapping("/item")
    public ItemResponseDto updateItemV1(
            @Valid @RequestBody ItemUpdateRequestDto dto) {
        Long id = itemService.update(dto);
        Item item = itemService.findByIdOrThrow(id);
        return new ItemResponseDto(item);
    }
}
