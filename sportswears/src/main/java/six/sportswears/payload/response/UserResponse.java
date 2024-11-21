package six.sportswears.payload.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponse {
    private Long id;


    private String first_name;


    private String last_name;


    private String username;


    private String password;


    private String address;

    private String main_image;

    private String email;

    private String phone;
}
