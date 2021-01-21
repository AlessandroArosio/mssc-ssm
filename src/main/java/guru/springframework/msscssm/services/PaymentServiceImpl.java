package guru.springframework.msscssm.services;

import guru.springframework.msscssm.domain.Payment;
import guru.springframework.msscssm.domain.PaymentEvent;
import guru.springframework.msscssm.domain.PaymentState;
import guru.springframework.msscssm.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final StateMachineFactory<PaymentState, PaymentEvent> stateMachineFactory;

    @Override
    public Payment newPayment(Payment payment) {
        payment.setState(PaymentState.NEW);

        return paymentRepository.save(payment);
    }

    @Override
    public StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId) {
        var sm = build(paymentId);
        return null;
    }

    @Override
    public StateMachine<PaymentState, PaymentEvent> authorisePayment(Long paymentId) {
        var sm = build(paymentId);
        return null;
    }

    @Override
    public StateMachine<PaymentState, PaymentEvent> declineAuth(Long paymentId) {
        var sm = build(paymentId);
        return null;
    }

    // this restore the status of the StateMachine from the DB
    private StateMachine<PaymentState, PaymentEvent> build(Long paymentId) {
        var payment = paymentRepository.getOne(paymentId);

        var sm = stateMachineFactory.getStateMachine(Long.toString(payment.getId()));

        sm.stop();

        sm.getStateMachineAccessor()
                .doWithAllRegions(sma ->
                        sma.resetStateMachine(new DefaultStateMachineContext<>(payment.getState(), null, null, null)));

        sm.start();

        return sm;
    }

}
