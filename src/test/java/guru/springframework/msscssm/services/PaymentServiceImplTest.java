package guru.springframework.msscssm.services;

import guru.springframework.msscssm.domain.Payment;
import guru.springframework.msscssm.domain.PaymentEvent;
import guru.springframework.msscssm.domain.PaymentState;
import guru.springframework.msscssm.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceImplTest {

  @Autowired
  PaymentService paymentService;

  @Autowired
  PaymentRepository paymentRepository;

  Payment payment;

  @BeforeEach
  void setUp() {
    payment = Payment.builder()
        .amount(new BigDecimal("12.99"))
        .build();
  }

  @Transactional
  @Test
  void preAuth() {
    Payment savedPayment = paymentService.newPayment(payment);

    assertEquals("NEW", savedPayment.getState().toString());

    StateMachine<PaymentState, PaymentEvent> sm = paymentService.preAuth(savedPayment.getId());

    Payment preAuthPayment = paymentRepository.getOne(savedPayment.getId());

    assertEquals("PRE_AUTH", preAuthPayment.getState().toString());
  }
}