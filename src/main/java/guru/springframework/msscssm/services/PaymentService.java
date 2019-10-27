package guru.springframework.msscssm.services;

import guru.springframework.msscssm.domain.Payment;
import guru.springframework.msscssm.domain.PaymentEvent;
import guru.springframework.msscssm.domain.PaymentState;
import org.springframework.statemachine.StateMachine;

public interface PaymentService {

  Payment newPayment(Payment payment);

  StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId);

  StateMachine<PaymentState, PaymentEvent> authorisePayment(Long paymentId);

  StateMachine<PaymentState, PaymentEvent> declineAuth(Long paymentId);
}
