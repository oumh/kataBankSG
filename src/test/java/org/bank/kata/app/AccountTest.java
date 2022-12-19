package org.bank.kata.app;

import org.assertj.core.api.Assertions;
import org.bank.kata.app.Account;
import org.bank.kata.app.StatementLine;
import org.bank.kata.app.App;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {

  private Account account;

  @BeforeEach
  public void setUp() {
    account = new Account();
  }


  @AfterEach
  void tearDown() {
    account = null;
  }

  @Test
  void testMakeDeposit() {
    account.makeDeposit(new BigDecimal(10.21));
    Assertions.assertThat(App.AMOUNT_FORMATER.format(account.getBalance())).isEqualTo("10,21");
    account.makeDeposit(new BigDecimal(11.56));
    Assertions.assertThat(App.AMOUNT_FORMATER.format(account.getBalance())).isEqualTo("21,77");
  }

  @Test
  void testMakeWithdrawal() {
    account.makeWithdrawal(new BigDecimal(10.21));
    Assertions.assertThat(App.AMOUNT_FORMATER.format(account.getBalance())).isEqualTo("-10,21");
    account.makeWithdrawal(new BigDecimal(11.56));
    Assertions.assertThat(App.AMOUNT_FORMATER.format(account.getBalance())).isEqualTo("-21,77");
  }

  @Test
  void testDepositAndWithdraw() {
    account.makeDeposit(new BigDecimal(7800.56));
    account.makeDeposit(new BigDecimal(150));
    Assertions.assertThat(App.AMOUNT_FORMATER.format(account.getBalance())).isEqualTo("7950,56");
    account.makeWithdrawal(new BigDecimal(200.14));
    Assertions.assertThat(App.AMOUNT_FORMATER.format(account.getBalance())).isEqualTo("7750,42");
  }

  @Test
  void testAccountHistory() throws InterruptedException {

    account.makeDeposit(new BigDecimal(7800.56));
    Assertions.assertThat(App.AMOUNT_FORMATER.format(account.getBalance())).isEqualTo("7800,56");
    Set<StatementLine> accountHistory = account.getHistory();
    assertThat(accountHistory.size()).isEqualTo(1);

    account.makeDeposit(new BigDecimal(160));
    Assertions.assertThat(App.AMOUNT_FORMATER.format(account.getBalance())).isEqualTo("7960,56");
    accountHistory = account.getHistory();
    assertThat(accountHistory.size()).isEqualTo(2);

    account.makeWithdrawal(new BigDecimal(200.14));
    Assertions.assertThat(App.AMOUNT_FORMATER.format(account.getBalance())).isEqualTo("7760,42");
    accountHistory = account.getHistory();
    assertThat(accountHistory.size()).isEqualTo(3);

    Object[] accountHistoryArray = accountHistory.toArray();

    Assertions.assertThat(App.AMOUNT_FORMATER.format(((StatementLine) accountHistoryArray[0]).getAmount())).isEqualTo("200,14");
    Assertions.assertThat(App.AMOUNT_FORMATER.format(((StatementLine) accountHistoryArray[1]).getAmount())).isEqualTo("160,00");
    Assertions.assertThat(App.AMOUNT_FORMATER.format(((StatementLine) accountHistoryArray[2]).getAmount())).isEqualTo("7800,56");
  }

}
