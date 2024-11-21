package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import six.sportswears.model.Role;
import six.sportswears.model.User;
import six.sportswears.payload.request.UserRequest;
import six.sportswears.repository.UserRepository;
import six.sportswears.utils.StringUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRequestToUser {
    ModelMapper modelMapper;
    PasswordEncoder encoder;
    UserRepository userRepository;
    public User toUser(UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        User x = userRepository.findById(userRequest.getId()).get();
        List<Role> roleList = x.getRoleList();
        user.setRoleList(roleList);
//        if(StringUtils.check(userRequest.getNewPassword())) {
//            user.setPassword(encoder.encode(userRequest.getNewPassword()));
//        }
        return user;
    }
}
