package six.sportswears.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserReviewResponse {
    private String userName;
    private String comment;
    private Integer rate;
    private String main_image;
    private Long time;
}
