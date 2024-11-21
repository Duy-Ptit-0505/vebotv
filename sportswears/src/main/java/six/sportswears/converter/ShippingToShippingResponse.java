package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import six.sportswears.model.Shipping;
import six.sportswears.payload.response.ShippingResponse;

import java.text.NumberFormat;
import java.util.Locale;


@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShippingToShippingResponse {
    ModelMapper modelMapper;
    public String editPrice(Double number) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return currencyFormatter.format(number);
    }
    public ShippingResponse toShippingResponse(Shipping shipping) {

        return modelMapper.map(shipping, ShippingResponse.class);
    }
}
