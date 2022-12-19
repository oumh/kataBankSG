package org.bank.kata.app;

import lombok.Data;

@Data
public class Client {

  private Account account;

  public Client() {
    account = new Account();
  }

}
