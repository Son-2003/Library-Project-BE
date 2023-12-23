package com.example.library.service;

import com.example.library.dao.PaymentRepository;
import com.example.library.entity.Payment;
import com.example.library.requestmodels.PaymentInfoRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PaymentService {
    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository,
                          @Value("${stripe.key.secret}") String secretKey){
        this.paymentRepository = paymentRepository;

        //initialize Stripe API with secret key
        Stripe.apiKey = secretKey;
    }

    public PaymentIntent createPaymentIntent(PaymentInfoRequest paymentinfo) throws StripeException {

        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentinfo.getAmount());
        params.put("currency", paymentinfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);

        return PaymentIntent.create(params);
    }

    public ResponseEntity<String> stripePayment(String userEmail) throws Exception{
        Payment payment = paymentRepository.findByUserEmail(userEmail);

        if(payment == null){
            throw  new Exception("Payment information is missing");
        }

        payment.setAmount(0);
        paymentRepository.save(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
