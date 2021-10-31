package backend.core.web.api.ratingInfo;

import backend.core.domain.RatingInfo;
import backend.core.service.RatingInfoService;
import backend.core.web.Result;
import backend.core.web.api.ratingInfo.dto.RequestCreateDto;
import backend.core.web.api.ratingInfo.dto.RequestUpdateDto;
import backend.core.web.api.ratingInfo.dto.ResponseDto;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class RatingInfoApiController {

    private final RatingInfoService ratingInfoService;

    /**
     * 평가 내용 생성
     */
    @PostMapping("/rating-info")
    public ResponseDto createRatingInfoV1(
            @Valid @RequestBody RequestCreateDto request) {
        log.info("request = {}", request);
        Long id = ratingInfoService.RatingInfo(request);
        return new ResponseDto(ratingInfoService.findOne(id));
    }

    /**
     * 평가 내용 조회
     */
    @GetMapping("/rating-info/{id}")
    public Result ratingInfoV1(
            @PathVariable Long id) {
        List<RatingInfo> ratingInfoList = ratingInfoService.findById(id);
        List<ResponseDto> result = ratingInfoList.stream()
                .map(ratingInfo -> new ResponseDto(ratingInfo))
                .collect(Collectors.toList());

        return new Result(result.size(), result);
    }

    /**
     * 평가 내용 수정
     */
    @PutMapping("/rating-info/{id}")
    public ResponseDto updateRatingInfoV1(
            @Valid @RequestBody RequestUpdateDto request,
            @PathVariable Long id) {
        request.setId(id);
        Long ratingInfoId = ratingInfoService.updateRatingInfo(request);
        return new ResponseDto(ratingInfoService.findOne(ratingInfoId));
    }
}
