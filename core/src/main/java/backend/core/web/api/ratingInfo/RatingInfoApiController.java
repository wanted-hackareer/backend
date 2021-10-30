package backend.core.web.api.ratingInfo;

import backend.core.service.RatingInfoService;
import backend.core.web.api.BaseApiController;
import backend.core.web.api.ratingInfo.dto.RatingInfoRequestCreateDto;
import backend.core.web.api.ratingInfo.dto.RatingInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class RatingInfoApiController extends BaseApiController {

    private final RatingInfoService ratingInfoService;

    @PostMapping("/ratingInfo")
    public RatingInfoResponseDto ratingInfoV1 (
            @Valid @RequestBody RatingInfoRequestCreateDto request) {
        Long id = ratingInfoService.RatingInfo(request);
        return new RatingInfoResponseDto(ratingInfoService.findOne(id));
    }
}
