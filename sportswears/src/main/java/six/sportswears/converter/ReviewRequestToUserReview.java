package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import six.sportswears.model.Sportswear;
import six.sportswears.model.User;
import six.sportswears.model.UserReview;
import six.sportswears.payload.request.ReviewRequest;
import six.sportswears.repository.SportswearRepository;
import six.sportswears.repository.UserRepository;
import six.sportswears.security.services.UserDetailsImpl;

import java.util.Date;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewRequestToUserReview {
    ModelMapper modelMapper;
    UserRepository userRepository;
    SportswearRepository sportswearRepository;
    public UserReview toUserReview(ReviewRequest reviewRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetails.getUsername()));
        UserReview userReview = modelMapper.map(reviewRequest, UserReview.class);
        Sportswear sportswear = sportswearRepository.findById(reviewRequest.getSportswearID()).orElseThrow(null);
        userReview.setUser(user);
        userReview.setSportswear(sportswear);
        userReview.setTimeCreated(new Date());
        return userReview;

    }
}
