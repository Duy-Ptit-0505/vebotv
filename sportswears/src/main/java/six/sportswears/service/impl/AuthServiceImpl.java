package six.sportswears.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import six.sportswears.constant.ERole;
import six.sportswears.model.*;
import six.sportswears.payload.request.LoginRequest;
import six.sportswears.payload.request.RegisterRequest;
import six.sportswears.payload.response.JwtResponse;
import six.sportswears.payload.response.MessageResponse;
import six.sportswears.repository.*;
import six.sportswears.security.jwt.JwtUtils;
import six.sportswears.security.services.UserDetailsImpl;
import six.sportswears.service.AuthService;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;
    AdminRepository adminRepository;

    CustomerRepository customerRepository;
    CartRepository cartRepository;

    public ResponseEntity<JwtResponse> signIn(LoginRequest loginRequest) {
        Authentication authentication;
        UserDetailsImpl userDetails;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            userDetails = (UserDetailsImpl) authentication.getPrincipal();
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }


        String jwt = jwtUtils.generateJwtToken(authentication);
        //String jwt1 = jwtUtils.generateJwtToken(SecurityContextHolder.getContext().getAuthentication());
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    public ResponseEntity<MessageResponse> signUp(RegisterRequest registerRequest) {
            if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // create new user
        List<String> stringList = new ArrayList<>();
        stringList.add("CUSTOMER");
        List<Role> roles = new ArrayList<>();
        Role adminRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(adminRole);
//        stringList.forEach(role -> {
//            switch (role) {
//                case "ADMIN":
//                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                    roles.add(adminRole);
//                    break;
//                default:
//                    Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
//                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                    roles.add(userRole);
//            }
//        });


        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(encoder.encode(registerRequest.getPassword()))
                .status(1)
                .roleList(roles)
                .build();
        userRepository.save(user);
        if(stringList.contains("ADMIN")) {
            Admin admin = new Admin();
            admin.setUser(user);
            adminRepository.save(admin);
        }

        if (stringList.contains("CUSTOMER")) {
            Customer customer = new Customer();
            customer.setUser(user);
            customerRepository.save(customer);
            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
        return ResponseEntity.ok(new MessageResponse("oke"));
    }


}
