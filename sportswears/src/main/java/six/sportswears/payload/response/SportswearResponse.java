package six.sportswears.payload.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SportswearResponse {

    private Long id;

    private String name;

    private String price;

    private String price_vnd;

    private String description;

    private String main_image;

    private Integer status;

    private List<String> list_of_related_sportswear_images;

    private String categoryName;

}
