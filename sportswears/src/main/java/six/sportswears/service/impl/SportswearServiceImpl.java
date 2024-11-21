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
import six.sportswears.converter.CreateSportswearRequestToSportswear;
import six.sportswears.converter.SportswearToSportswearResponse;
import six.sportswears.model.*;
import six.sportswears.payload.request.CreateSportswearRequest;
import six.sportswears.payload.request.SearchRequest;
import six.sportswears.payload.response.*;
import six.sportswears.repository.*;
import six.sportswears.service.SportswearService;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class SportswearServiceImpl implements SportswearService {
    SportswearRepository sportswearRepository;
    CreateSportswearRequestToSportswear createSportswearRequestToSportswear;
    SportswearToSportswearResponse sportswearToSportswearResponse;
    UserReviewRepository userReviewRepository;
    CartItemRepository cartItemRepository;
    OrderItemRepository orderItemRepository;
//    CategoryRepository categoryRepository;
    RelatedImageSportswearRepository relatedImageSportswearRepository;
//    UserRepository userRepository;
//    OrderItemRequestToOrderItem orderItemRequestToOrderItem;
//    CartRepository cartRepository;
    @Override
    public ResponseEntity<MessageResponse> createSportswearByAdmin(CreateSportswearRequest createSportswearRequest) {
        Sportswear sportswear = createSportswearRequestToSportswear.toSportswear(createSportswearRequest);
        sportswearRepository.save(sportswear);

        if(createSportswearRequest.getId() != null) {
            List<RelatedImageSportswear> relatedImageSportswearList = relatedImageSportswearRepository.findAllBySportswear(sportswear);
            relatedImageSportswearRepository.deleteAllInBatch(relatedImageSportswearList);
        }
        List<String> related_photo = createSportswearRequest.getList_of_related_sportswear_images();
        if(related_photo != null) {
            for (String x : related_photo) {
                RelatedImageSportswear relatedImageSportswear = new RelatedImageSportswear();
                relatedImageSportswear.setRelated_image(x);
                relatedImageSportswear.setSportswear(sportswear);
                relatedImageSportswearRepository.save(relatedImageSportswear);

            }
        }

        return ResponseEntity.ok(new MessageResponse("oke"));
    }

    // get all sportswear to display for customer (status = 1)
    @Override
    public ResponseEntity<ListSportswearResponse> getAllSportswearDisplayForCustomer(SearchRequest searchRequest) {
        // get all sportswear by search request
        List<Sportswear> sportswearList = sportswearRepository.findAllSportswearBySearchForCustomer(searchRequest);

        // convert to sportswear response
        List<SportswearResponse> sportswearResponseList = new ArrayList<>();
        for(Sportswear x : sportswearList) {
            sportswearResponseList.add(sportswearToSportswearResponse.toSportswearResponse(x));
        }


        Pageable pageable = PageRequest.of((int)(searchRequest.getNoPage() -1L), 16);
        long start = pageable.getOffset();
        long end;
        if(start + pageable.getPageSize() > sportswearResponseList.size()) {
            end = (long) sportswearResponseList.size();
        } else {
            end = start + pageable.getPageSize();
        }

        // cut
        sportswearResponseList = sportswearResponseList.subList(Math.toIntExact(start), Math.toIntExact(end));


        Page<SportswearResponse> sportswearResponsePage = new PageImpl<>(sportswearResponseList, pageable, sportswearList.size());


        ListSportswearResponse listSportswearResponse = new ListSportswearResponse();
        listSportswearResponse.setSportswearResponseList(sportswearResponseList);
        listSportswearResponse.setCurrentPage(searchRequest.getNoPage());
        listSportswearResponse.setTotalPage((long) sportswearResponsePage.getTotalPages());
        return ResponseEntity.ok(listSportswearResponse);
    }
//
//   0 @Override
//    public List<SportswearResponse> getAllSportswearForCustomerBySearching(SearchRequest searchRequest) {
//        Category category = categoryRepository.findByName(searchRequest.getCategoryName());
//        List<Sportswear> sportswearList = sportswearRepository.findByNameContainingAndCategory(searchRequest.getKey(), category);
//        List<SportswearResponse> sportswearResponseList = new ArrayList<>();
//        for(Sportswear x : sportswearList) {
//            sportswearResponseList.add(sportswearToSportswearDisplay.toSportswearDisplay(x));
//        }
//        return sportswearResponseList;
//
//    }

//   0 @Override
//    public void deleteSportswearByAdmin(Long id) {
//        sportswearRepository.deleteById(id);
//    }
//
//   0 @Override
//    public void disableSportswearByAdmin(Long id) {
//        Sportswear sportswear = sportswearRepository.findById(id).get();
//        sportswear.setStatus(0);
//        sportswearRepository.save(sportswear);
//    }

    @Override
    public ResponseEntity<SportswearResponse> getSportswearByID(Long id) {
        Sportswear sportswear = sportswearRepository.findById(id).orElse(null);
        return ResponseEntity.ok( sportswearToSportswearResponse.toSportswearResponse(sportswear));
    }

//   0 @Override
//    public List<SportswearResponse> getRelatedSportswearDisplay(Long currentProductId) {
//        Pageable pageable = PageRequest.of(0, 4);
//        List<Sportswear> sportswearList =  sportswearRepository.findAllByIdNot(currentProductId, pageable).getContent();
//        List<SportswearResponse> sportswearResponseList = new ArrayList<>();
//        for(Sportswear x : sportswearList) {
//            sportswearResponseList.add(sportswearToSportswearDisplay.toSportswearDisplay(x));
//        }
//        return sportswearResponseList;
//    }

    @Override
    public ResponseEntity<MessageResponse> deleteSportswearByID(Long id) {
        Sportswear sportswear = sportswearRepository.findById(id).orElse(null);
        List<RelatedImageSportswear> imageSportswearList = relatedImageSportswearRepository.findAllBySportswear(sportswear);
        if(!imageSportswearList.isEmpty()) {
            relatedImageSportswearRepository.deleteAllInBatch(imageSportswearList);
        }
        assert sportswear != null;
        List<UserReview> userReviewList = sportswear.getUserReviewList();
        if(!userReviewList.isEmpty()) {
            userReviewRepository.deleteAllInBatch(userReviewList);
        }
        List<OrderItem> orderItemList = sportswear.getOrderItemList();
        List<CartItem> cartItemList = sportswear.getCartItemList();

        if(!orderItemList.isEmpty()) {
            orderItemRepository.deleteAllInBatch(orderItemList);
        }
        if(!cartItemList.isEmpty()) {
            for(CartItem x : cartItemList) {
                cartItemRepository.delete(x);
            }
        }
        sportswearRepository.delete(sportswear);
        return ResponseEntity.ok(new MessageResponse("oke"));
    }


    @Override
    public ResponseEntity<ListSportswearResponse> getAllSportswearDisplayForAdmin(SearchRequest searchRequest) {


        List<Sportswear> sportswearList = sportswearRepository.findAllSportswearBySearchForAdmin(searchRequest);
//

        List<SportswearResponse> sportswearResponseList = new ArrayList<>();
        for(Sportswear x : sportswearList) {
            sportswearResponseList.add(sportswearToSportswearResponse.toSportswearResponse(x));
        }

        Pageable pageable = PageRequest.of((int)(searchRequest.getNoPage() -1L), 16);
        long start = pageable.getOffset();
        long end;
        if(start + pageable.getPageSize() > sportswearResponseList.size()) {
            end = (long) sportswearResponseList.size();
        } else {
            end = start + pageable.getPageSize();
        }

        // cut
        sportswearResponseList = sportswearResponseList.subList(Math.toIntExact(start), Math.toIntExact(end));


        Page<SportswearResponse> sportswearResponsePage = new PageImpl<>(sportswearResponseList, pageable, sportswearList.size());


        ListSportswearResponse listSportswearResponse = new ListSportswearResponse();
        listSportswearResponse.setSportswearResponseList(sportswearResponseList);
        listSportswearResponse.setCurrentPage(searchRequest.getNoPage());
        listSportswearResponse.setTotalPage((long) sportswearResponsePage.getTotalPages());
        return ResponseEntity.ok(listSportswearResponse);
    }

    @Override
    public ResponseEntity<SportswearRevenueResponse> getSportswearRevenueResponseByIDAndTime(Long id, String time) {
        return ResponseEntity.ok(sportswearRepository.getSportswearRevenueByIDAndTime(id, time));
    }

    @Override
    public ResponseEntity<ListSportswearRevenueResponseInAMonth> getListSportswearRevenueResponseInAMonth(Long id, Integer year) {
        return ResponseEntity.ok(sportswearRepository.getListSportswearRevenueResponseInAMonth(id, year));
    }

    @Override
    public ResponseEntity<ListSportswearRevenueResponseInAMonth> getAllSportswearRevenueResponse(Integer time) {
        return ResponseEntity.ok(sportswearRepository.getAllSportswearRevenueByTime(time));
    }

    @Override
    public ResponseEntity<ListLabelAndValue> getListLabelAndValue(String month, Integer year) {
        return ResponseEntity.ok(sportswearRepository.getListLabelAndValue(month, year));
    }

}
