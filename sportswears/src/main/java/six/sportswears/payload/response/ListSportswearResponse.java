package six.sportswears.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListSportswearResponse {
    private List<SportswearResponse> sportswearResponseList;
    private Long currentPage;
    private Long totalPage;
}
