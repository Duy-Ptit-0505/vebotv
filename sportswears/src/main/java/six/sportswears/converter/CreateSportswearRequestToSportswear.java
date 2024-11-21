package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import six.sportswears.model.Sportswear;
import six.sportswears.model.SportswearCategory;
import six.sportswears.payload.request.CreateSportswearRequest;
import six.sportswears.repository.SportswearCategoryRepository;
import six.sportswears.utils.ImageUpload;
import six.sportswears.utils.StringUtils;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateSportswearRequestToSportswear {
    ModelMapper modelMapper;
    SportswearCategoryRepository sportswearCategoryRepository;
//    ImageUpload imageUpload;
//    ImageSportswearRepository imageSportswearRepository;
    public Sportswear toSportswear(CreateSportswearRequest createSportswearRequest) {
        Sportswear sportswear = modelMapper.map(createSportswearRequest, Sportswear.class);
        if (!StringUtils.check(createSportswearRequest.getCategoryName())) {
            createSportswearRequest.setCategoryName("CLB");
        }
        SportswearCategory sportswearCategory = sportswearCategoryRepository.findByName(createSportswearRequest.getCategoryName());
        sportswear.setSportswearCategory(sportswearCategory);
        sportswear.setMain_image(createSportswearRequest.getMain_image());
        sportswear.setStatus(createSportswearRequest.getStatus());

        return sportswear;
    }
}
