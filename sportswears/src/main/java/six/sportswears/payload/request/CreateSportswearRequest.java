package six.sportswears.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSportswearRequest {
    private Long id;

    private String name;

    private Double price;

    private String main_image;

    private String description;

    private String categoryName;

    private Integer status;

    private List<String> list_of_related_sportswear_images;

}
