package six.sportswears.controller;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import six.sportswears.constant.ERole;
import six.sportswears.model.Role;
import six.sportswears.model.User;
import six.sportswears.payload.request.LoginRequest;
import six.sportswears.payload.request.RegisterRequest;
import six.sportswears.payload.response.JwtResponse;
import six.sportswears.payload.response.ListSportswearResponse;
import six.sportswears.payload.response.MessageResponse;
import six.sportswears.repository.RoleRepository;
import six.sportswears.repository.UserRepository;
import six.sportswears.security.services.UserDetailsImpl;
import six.sportswears.service.impl.AuthServiceImpl;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthServiceImpl authService;
    RoleRepository roleRepository;
    UserRepository userRepository;
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.signIn(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return authService.signUp(registerRequest);

    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }
    @PostMapping("/update")
    public ResponseEntity<MessageResponse> update() {
        Role role = roleRepository.findByName(ERole.ROLE_CUSTOMER).get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        List<Role> roleList = user.getRoleList();
        roleList.add(role);
        user.setRoleList(roleList);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("oke"));
    }

}

