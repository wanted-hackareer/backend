package backend.core.web.api.ratingInfo;

import backend.core.domain.RatingInfo;
import backend.core.service.RatingInfoService;
import backend.core.web.api.BaseApiController;
import backend.core.web.api.ratingInfo.dto.RatingInfoRequestCreateDto;
import backend.core.web.api.ratingInfo.dto.RatingInfoResponseDto;
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
public class RatingInfoApiController extends BaseApiController {

    private final RatingInfoService ratingInfoService;

    /**
     * 평가 내용 생성
     */
    @PostMapping("/rating-info")
    public RatingInfoResponseDto createRatingInfoV1(
            @Valid @RequestBody RatingInfoRequestCreateDto request) {
        log.info("request = {}", request);
        Long id = ratingInfoService.RatingInfo(request);
        return new RatingInfoResponseDto(ratingInfoService.findOne(id));
    }

    /**
     * 평가 내용 조회
     */
    @GetMapping("/rating-info/{id}")
    public Result RatingInfoV1(
            @PathVariable Long id) throws NotFoundException {
        List<RatingInfo> ratingInfoList = ratingInfoService.findById(id);
        List<RatingInfoResponseDto> result = ratingInfoList.stream()
                .map(ratingInfo -> new RatingInfoResponseDto(ratingInfo))
                .collect(Collectors.toList());
        return new Result(result.size(), result);
    }
}
