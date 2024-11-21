package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import six.sportswears.model.Payment;
import six.sportswears.payload.response.PaymentResponse;


@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentToPaymentResponse {
    ModelMapper modelMapper;
    public PaymentResponse toPaymentResponse(Payment payment) {
        return modelMapper.map(payment, PaymentResponse.class);
    }
}
