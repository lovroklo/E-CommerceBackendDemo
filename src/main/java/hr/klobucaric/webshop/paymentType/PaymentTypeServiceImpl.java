package hr.klobucaric.webshop.paymentType;

import hr.klobucaric.webshop.utils.exception.ApiBadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;

@Service
@RequiredArgsConstructor
public class PaymentTypeServiceImpl implements PaymentTypeService{

    private final PaymentTypeRepository paymentTypeRepository;

    @Override
    public PaymentType save(PaymentType type){
        try {

            return paymentTypeRepository.save(type);
        }catch (Exception e){
            throw new ApiBadRequestException("Type should be unique! " + e.getMessage());
        }
    }


}
