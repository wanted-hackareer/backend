package backend.core.service;

import backend.core.domain.Rating;
import backend.core.domain.RatingInfo;
import backend.core.repository.RatingInfoRepository;
import backend.core.repository.RatingRepository;
import backend.core.web.api.ratingInfo.dto.RequestCreateDto;
import backend.core.web.api.ratingInfo.dto.RequestUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RatingInfoService {

    private final RatingInfoRepository ratingInfoRepository;
    private final RatingRepository ratingRepository;

    /**
     * 평가 내용 생성
     */
    @Transactional
    public Long RatingInfo(RequestCreateDto dto) {
        Rating rating = ratingRepository.findOne(dto.getRatingId());
        dto.setRating(rating);

        RatingInfo ratingInfo = dto.toEntity();
        ratingInfoRepository.save(ratingInfo);

        return ratingInfo.getId();
    }

    public RatingInfo findOne(Long id) {
        return ratingInfoRepository.findOne(id);
    }

    /**
     * GET API 조회 함수
     */
    public List<RatingInfo> findById(Long id) {
        return ratingInfoRepository.findById(id);
    }

    public List<RatingInfo> findAll(int offset, int limit) {
        return ratingInfoRepository.findAll(offset, limit);
    }

    /**
     * 평가 내용 수정
     */
    @Transactional
    public Long updateRatingInfo(RequestUpdateDto dto) {
        RatingInfo ratingInfo = ratingInfoRepository.findOne(dto.getId());
        ratingInfo.update(dto.getIsLike(), dto.getOpinion());

        return ratingInfo.getId();
    }
}
