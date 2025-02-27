package six.sportswears.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReviewRequest {
    private Long sportswearID;
    private Integer rating_value;
    private String comment;
}
