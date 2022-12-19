package org.bank.kata.app.app;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StatementLine implements Comparable<StatementLine> {

  private LocalDateTime dateTime;
  private BigDecimal amount;
  private StatementKind direction;

  public StatementLine(LocalDateTime dateTime, BigDecimal amount, StatementKind direction) {
    this.dateTime = dateTime;
    this.amount = amount;
    this.direction = direction;
  }

  
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
