package six.sportswears.service;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import six.sportswears.payload.response.ListUserReviewResponse;

@Service
@Transactional
public interface UserReviewService {
    ResponseEntity<ListUserReviewResponse> getListUserReviewResponse(Long sportswearID, Long noPage);
}
