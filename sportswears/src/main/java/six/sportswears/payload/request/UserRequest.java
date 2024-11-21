package six.sportswears.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private Long id;

    private String first_name;

    private String last_name;

    private String username;

    private String password;

    private String address;

    private String main_image;

    private String email;

    private String phone;

//    private String passwordCurrent;
//
//    private String newPassword;
}
