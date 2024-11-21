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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import six.sportswears.converter.ReviewRequestToUserReview;
import six.sportswears.converter.UserRequestToUser;
import six.sportswears.converter.UserToUserResponse;
import six.sportswears.model.User;
import six.sportswears.model.UserReview;
import six.sportswears.payload.request.ReviewRequest;
import six.sportswears.payload.request.UserRequest;
import six.sportswears.payload.response.*;
import six.sportswears.repository.UserRepository;
import six.sportswears.repository.UserReviewRepository;
import six.sportswears.security.services.UserDetailsImpl;
import six.sportswears.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserToUserResponse userToUserResponse;
    ReviewRequestToUserReview reviewRequestToUserReview;
    UserReviewRepository userReviewRepository;
    PasswordEncoder encoder;
    UserRequestToUser userRequestToUser;
    @Override
    public ResponseEntity<ListUserResponse> getALlUserForAdmin(Long noPage) {
        List<User> userList = userRepository.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();
        for(User x : userList) {
            userResponseList.add(userToUserResponse.toUserResponse(x));
        }

        Pageable pageable = PageRequest.of((int)(noPage -1L), 16);
        long start = pageable.getOffset();
        long end;
        if(start + pageable.getPageSize() > userResponseList.size()) {
            end = (long) userResponseList.size();
        } else {
            end = start + pageable.getPageSize();
        }

        // cut
        userResponseList = userResponseList.subList(Math.toIntExact(start), Math.toIntExact(end));


        Page<UserResponse> userResponsePage = new PageImpl<>(userResponseList, pageable, userList.size());


        ListUserResponse listUserResponse = new ListUserResponse();
        listUserResponse.setUserResponseList(userResponseList);
        listUserResponse.setCurrentPage(noPage);
        listUserResponse.setTotalPage((long) userResponsePage.getTotalPages());

//        ListUserResponse listUserResponse = new ListUserResponse();
//        listUserResponse.setUserResponseList(userResponseList);
//        Long total = (long) userResponseList.size();
//        listUserResponse.setTotalUser(total);
        return ResponseEntity.ok(listUserResponse);
    }

    @Override
    public ResponseEntity<UserResponse> getProfileUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetails.getUsername()));
        System.out.println(user.getRoleList().size());
        return ResponseEntity.ok(userToUserResponse.toUserResponse(user));
    }

    @Override
    public ResponseEntity<MessageResponse> feedBack(List<ReviewRequest> reviewRequestList) {
        for(ReviewRequest x : reviewRequestList) {
            UserReview userReview = reviewRequestToUserReview.toUserReview(x);
            userReviewRepository.save(userReview);
        }


        return ResponseEntity.ok(new MessageResponse("oke"));
    }

    @Override
    public ResponseEntity<MessageResponse> changeProfile(UserRequest userRequest) {
//        String checkPassword = encoder.encode(userRequest.getPasswordCurrent());
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetails.getUsername()));
//        if (!user.getPassword().equals(checkPassword)) {
//            return ResponseEntity.ok(new MessageResponse("wrong"));
//        }
        userRepository.save(userRequestToUser.toUser(userRequest));
        return ResponseEntity.ok(new MessageResponse("oke"));

    }

    @Override
    public ResponseEntity<MessageResponse> updateUser(UserResponse userResponse) {
        return null;
    }
}
