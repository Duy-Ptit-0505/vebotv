package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import six.sportswears.model.RelatedImageSportswear;
import six.sportswears.model.Sportswear;
import six.sportswears.payload.response.SportswearResponse;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SportswearToSportswearResponse {
    ModelMapper modelMapper;
    public String editPrice(Double number) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return currencyFormatter.format(number);
    }
    public SportswearResponse toSportswearResponse(Sportswear sportswear) {
        SportswearResponse sportswearResponse = modelMapper.map(sportswear, SportswearResponse.class);
        sportswearResponse.setMain_image(sportswear.getMain_image());
        List<RelatedImageSportswear> relatedImageSportswearList = sportswear.getRelatedImageSportswearList();
        List<String> related_photo = new ArrayList<>();
        for(RelatedImageSportswear x : relatedImageSportswearList) {
            related_photo.add(x.getRelated_image());
        }
        sportswearResponse.setList_of_related_sportswear_images(related_photo);
        sportswearResponse.setCategoryName(sportswear.getSportswearCategory().getName());
        sportswearResponse.setPrice_vnd(editPrice(sportswear.getPrice()));

        return sportswearResponse;
    }
}
