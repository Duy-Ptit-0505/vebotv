package six.sportswears.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListUserResponse {
    private List<UserResponse> userResponseList;
    private Long totalUser;
    private Long currentPage;
    private Long totalPage;
}
