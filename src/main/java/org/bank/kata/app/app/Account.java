package org.bank.kata.app.app;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

public class Account {
  private Set<StatementLine> statementLines = new TreeSet<StatementLine>().descendingSet();
  private BigDecimal balance = new BigDecimal(0);

  public void makeDeposit(BigDecimal amount) {
    StatementLine deposit = new StatementLine(LocalDateTime.now(), amount, StatementKind.CREDIT);
    statementLines.add(deposit);
    balance = balance.add(amount);
  }

  public void makeWithdrawal(BigDecimal amount) {
    StatementLine withdraw = new StatementLine(LocalDateTime.now(), amount, StatementKind.WITHDRAW);
    statementLines.add(withdraw);
    balance = balance.subtract(amount);
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public Set<StatementLine> getHistory() {
    return statementLines;
  }
}
