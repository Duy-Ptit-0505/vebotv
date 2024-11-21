package six.sportswears.service.impl;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import six.sportswears.converter.UserReviewToUserReviewResponse;
import six.sportswears.model.Sportswear;
import six.sportswears.model.UserReview;
import six.sportswears.payload.response.ListSportswearResponse;
import six.sportswears.payload.response.ListUserReviewResponse;
import six.sportswears.payload.response.SportswearResponse;
import six.sportswears.payload.response.UserReviewResponse;
import six.sportswears.repository.SportswearRepository;
import six.sportswears.repository.UserReviewRepository;
import six.sportswears.service.UserReviewService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class UserReviewServiceImpl implements UserReviewService {
    UserReviewRepository userReviewRepository;
    SportswearRepository sportswearRepository;
    UserReviewToUserReviewResponse userReviewToUserReviewResponse;
    @Override
    public ResponseEntity<ListUserReviewResponse> getListUserReviewResponse(Long sportswearID, Long noPage) {
        Sportswear sportswear = sportswearRepository.findById(sportswearID).orElse(null);
        List<UserReview> userReviewList = userReviewRepository.findAllBySportswear(sportswear);
        List<UserReviewResponse> userReviewResponseList = new ArrayList<>();
        for(UserReview x : userReviewList) {
            userReviewResponseList.add(userReviewToUserReviewResponse.toUserReviewResponse(x));
        }

        Pageable pageable = PageRequest.of((int)(noPage - 1L), 4);
        long start = pageable.getOffset();
        long end;
        if(start + pageable.getPageSize() > userReviewResponseList.size()) {
            end = (long) userReviewResponseList.size();
        } else {
            end = start + pageable.getPageSize();
        }

        // cut
        userReviewResponseList = userReviewResponseList.subList(Math.toIntExact(start), Math.toIntExact(end));


        Page<UserReviewResponse> userReviewResponsePage = new PageImpl<>(userReviewResponseList, pageable, userReviewList.size());


        ListUserReviewResponse listSportswearResponse = new ListUserReviewResponse();
        listSportswearResponse.setUserReviewResponseList(userReviewResponseList);
        listSportswearResponse.setCurrentPage(noPage);
        listSportswearResponse.setTotalPage((long) userReviewResponsePage.getTotalPages());

        return ResponseEntity.ok(listSportswearResponse);
    }
}
