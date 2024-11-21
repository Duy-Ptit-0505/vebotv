package six.sportswears.service;



import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import six.sportswears.payload.request.LoginRequest;
import six.sportswears.payload.request.RegisterRequest;
import six.sportswears.payload.response.JwtResponse;
import six.sportswears.payload.response.MessageResponse;

@Service
@Transactional
public interface AuthService {
    ResponseEntity<JwtResponse> signIn(LoginRequest loginRequest);
    ResponseEntity<MessageResponse> signUp(RegisterRequest registerRequest);

}

