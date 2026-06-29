package com.elderdev.orderFlow.service;

import com.elderdev.orderFlow.entity.Order;
import com.elderdev.orderFlow.entity.Payment;
import com.elderdev.orderFlow.entity.PaymentMethod;
import com.elderdev.orderFlow.entity.PaymentStatus;
import com.elderdev.orderFlow.exception.NotFoundException;
import com.elderdev.orderFlow.repository.OrderRepository;
import com.elderdev.orderFlow.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.exception.StripeException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public Payment findById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found"));
    }

    public Payment findByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NotFoundException("Payment not found"));
    }

    public String createPayment(Long orderId) throws StripeException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        //Converte BigDecimal para centimos(long)
        //Stripe nao aceita decimais(10,99€ -> 1099€)
        long amountInCents = order.getTotalAmount()
                .multiply(new BigDecimal("100"))
                .longValue();

        // Constrói os parâmetros do PaymentIntent
        // É um builder pattern — defines o valor, moeda e descrição
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency("eur")
                .setDescription("Order #" + order.getId())
                .build();
        // Chama a API do Stripe — pode lançar StripeException
        // Se falhar (rede, chave inválida, etc.) a exceção propaga-se
        PaymentIntent paymentIntent = PaymentIntent.create(params);


        Payment payment = Payment.builder()
                .order(order)
                .method(PaymentMethod.STRIPE)
                .status(PaymentStatus.PENDING)
                .amount(order.getTotalAmount())
                .stripePaymentId(paymentIntent.getId())
                .build();
        paymentRepository.save(payment);

        // Devolve o clientSecret — o frontend precisa disto
        // para mostrar o formulário de pagamento do Stripe
        return paymentIntent.getClientSecret();
    }

    public void confirmPayment(String stripePaymentId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(stripePaymentId);
        if (paymentIntent.getStatus().equals("succeeded")) {
            var payment = paymentRepository.findByStripePaymentId(stripePaymentId)
                    .orElseThrow(() -> new NotFoundException("Payment not found"));
            payment.setStatus(PaymentStatus.COMPLETED);
            paymentRepository.save(payment);
        }
    }

    public void refundPayment(Long id) throws StripeException {
        var payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found"));
        if (!payment.getStatus().equals(PaymentStatus.COMPLETED)) {
            throw new IllegalArgumentException("Only completed payments can be refunded");
        }
        payment.setStatus(PaymentStatus.REFUNDED);
        paymentRepository.save(payment);
    }
}
