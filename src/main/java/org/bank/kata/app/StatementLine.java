package org.bank.kata.app;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StatementLine implements Comparable<StatementLine> {

  private LocalDateTime dateTime;
  private BigDecimal amount;
  private StatementKind direction;

  
  @Override
  public int compareTo(StatementLine o) {
    int compDates = dateTime.compareTo(o.getDateTime());
    if (compDates != 0) {
      return compDates;
    } else {
      int compAmounts = amount.compareTo(o.getAmount());
      if (compAmounts != 0) {
        return compAmounts;
      } else {
        return direction.compareTo(o.getDirection());
      }
    }
  }
}
