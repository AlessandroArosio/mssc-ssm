package guru.springframework.msscssm.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Payment {

  @Id
  @GeneratedValue
  private Long id;

  @Enumerated(EnumType.STRING)
  private PaymentState state;

  private BigDecimal amount;
}
