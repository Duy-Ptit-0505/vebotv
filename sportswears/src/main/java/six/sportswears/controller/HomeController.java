package six.sportswears.controller;



import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import six.sportswears.model.UserReview;
import six.sportswears.payload.request.IDAndNoPage;
import six.sportswears.payload.request.SearchRequest;
import six.sportswears.payload.request.StatusAndNoPage;
import six.sportswears.payload.response.ListSportswearResponse;
import six.sportswears.payload.response.ListUserReviewResponse;
import six.sportswears.payload.response.SportswearResponse;
import six.sportswears.payload.response.SportswearRevenueResponse;
import six.sportswears.service.UserReviewService;
import six.sportswears.service.impl.SportswearServiceImpl;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/web")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)


public class HomeController {

    SportswearServiceImpl sportswearService;
    UserReviewService userReviewService;

    //    @GetMapping("/search")
//    public ResponseEntity<Object> getAllSportswear(@ModelAttribute SearchRequest searchRequest) {
//        if (searchRequest.getNoPage() == null) {
//            searchRequest.setNoPage(1L);
//        }
//        Page<SportswearResponse> sportswearDisplays = sportswearService.getAllSportswearDisplayForCustomer(searchRequest);
//        ServiceResponse<Page<SportswearResponse>> response = new ServiceResponse<>("success", sportswearDisplays);
//        return new ResponseEntity<Object>(response, HttpStatus.OK);
////        return  new ResponseEntity<>(new ListSportswearResponse(sportswearDisplays, searchRequest.getNoPage(), (long) sportswearDisplays.getTotalPages()), HttpStatus.OK);
//    }
    @GetMapping("/search")
    public ResponseEntity<ListSportswearResponse> getAllSportswear(@ModelAttribute SearchRequest searchRequest) {
        if (searchRequest.getNoPage() == null) {
            searchRequest.setNoPage(1L);
        }
        return sportswearService.getAllSportswearDisplayForCustomer(searchRequest);
    }

    //    @GetMapping("/searchtest")
//    public ResponseEntity<Object> getAllSportswearTest() {
//        SearchRequest searchRequest = new SearchRequest(null, 1L, null);
//        if (searchRequest.getNoPage() == null) {
//            searchRequest.setNoPage(1L);
//        }
//        Page<SportswearResponse> sportswearDisplays = sportswearService.getAllSportswearDisplayForCustomer(searchRequest);
//        ServiceResponse<Page<SportswearResponse>> response = new ServiceResponse<>("success", sportswearDisplays);
//        return new ResponseEntity<Object>(response, HttpStatus.OK);
//    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<SportswearResponse> detail(@PathVariable Long id) {
        return sportswearService.getSportswearByID(id);
    }
    @PostMapping("/user-review")
    public ResponseEntity<ListUserReviewResponse> getAllUserReview(@RequestBody IDAndNoPage idAndNoPage) {
        if(idAndNoPage.getNoPage() == null) {
            idAndNoPage.setNoPage(1L);
        }
        return userReviewService.getListUserReviewResponse(idAndNoPage.getSportswearID(), idAndNoPage.getNoPage());
    }

}

