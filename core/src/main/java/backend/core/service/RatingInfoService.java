package backend.core.service;

import backend.core.domain.RatingInfo;
import backend.core.repository.RatingInfoRepository;
import backend.core.web.api.ratingInfo.dto.RatingInfoRequestCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RatingInfoService {

    private final RatingInfoRepository ratingInfoRepository;

    /**
     * 평가 내용 생성
     */
    @Transactional
    public Long RatingInfo(RatingInfoRequestCreateDto requestDto) {
        RatingInfo ratingInfo = requestDto.toEntity();
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
}
