package backend.core.controller;

import backend.core.basket.domain.Basket;
import backend.core.item.dto.ItemCreateRequestDto;
import backend.core.item.dto.ItemUpdateRequestDto;
import backend.core.item.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Transactional
public class ItemControllerTest extends ApiDocumentationTest {

    @Autowired
    private ItemService itemService;

    private Basket basket;

    @Before
    public void init() {
        basket = Basket.builder().build();
        em.persist(basket);
    }

    @Test
    @DisplayName("[api] item 등록")
    public void saveTestV1() throws Exception {
        //given
        ItemCreateRequestDto dto = new ItemCreateRequestDto(basket.getId(), "사이다", 2);

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/item")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(dto.getName()))
                .andExpect(jsonPath("$.quantity").value(dto.getQuantity()))
                .andDo(document("item_생성",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("basketId").type(JsonFieldType.NUMBER).description("장바구니 id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("아이템 이름"),
                                fieldWithPath("quantity").type(JsonFieldType.NUMBER).description("아이템 수량")
                        )));
    }

    @Test
    @DisplayName("[api] item 다수 등록")
    public void saveItemsTestV1() throws Exception {
        //given
        List<ItemCreateRequestDto> dtoList = Arrays.asList(
                new ItemCreateRequestDto(basket.getId(), "사이다", 2),
                new ItemCreateRequestDto(basket.getId(), "콜라", 2));

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoList))
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andDo(document("item_다수_등록",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("[].basketId").type(JsonFieldType.NUMBER).description("장바구니 id"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("아이템 이름"),
                                fieldWithPath("[].quantity").type(JsonFieldType.NUMBER).description("아이템 수량")
                        )));
    }

    @Test
    @DisplayName("[api] item 수정")
    public void updateItemTestV1() throws Exception {
        //given
        ItemCreateRequestDto dto = new ItemCreateRequestDto(basket.getId(), "제로 콜라", 3);
        Long id = itemService.save(dto);

        ItemUpdateRequestDto updateDto = new ItemUpdateRequestDto(id, "펩시 라임", dto.getQuantity());

        //when
        ResultActions result = mockMvc.perform(put("/api/v1/item/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto))
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(updateDto.getName()))
                .andExpect(jsonPath("$.quantity").value(updateDto.getQuantity()))
                .andDo(document("item_수정",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이템 id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("아이템 이름"),
                                fieldWithPath("quantity").type(JsonFieldType.NUMBER).description("아이템 수량")
                        )));
    }

    @Test
    @DisplayName("[api] item 단일 조회")
    public void findIdV1() throws Exception {
        //given
        ItemCreateRequestDto dto = new ItemCreateRequestDto(basket.getId(), "제로 콜라", 3);
        Long id = itemService.save(dto);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/item/" + id)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(dto.getName()))
                .andExpect(jsonPath("$.quantity").value(dto.getQuantity()))
                .andDo(document("item_단일_조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("[api] item 전체 조회")
    public void findAllTestV1() throws Exception {
        //given
        itemService.save(new ItemCreateRequestDto(basket.getId(), "사이다", 3));
        itemService.save(new ItemCreateRequestDto(basket.getId(), "콜라", 2));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/items")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andDo(document("item_전체_조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("[api] item 이름 검색")
    public void searchByNameTestV1() throws Exception {
        //given
        itemService.save(new ItemCreateRequestDto(basket.getId(), "코카 콜라", 3));
        itemService.save(new ItemCreateRequestDto(basket.getId(), "콜라", 2));

        //when
        String name = "콜라";
        ResultActions result = mockMvc.perform(get("/api/v1/item/search?name=" + name)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andDo(document("item_이름_검색",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("name").description("조회될 이름")
                        )));
    }
}
