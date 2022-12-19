package org.bank.kata.app;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Bank {
  @Getter
  public static final String bankName = "KATA TEST";
  private Map<Integer, Client> clients = new HashMap<Integer, Client>();

  public BigDecimal getClientBalance(int clientId) {
    Client client = clients.get(clientId);
    return client.getAccount().getBalance();
  }

  public void makeDeposit(int clientId, BigDecimal amount) {
    Client client = clients.get(clientId);
    client.getAccount().makeDeposit(amount);
  }

  public void makeWithdrawal(int clientId, BigDecimal amount) {
    Client client = clients.get(clientId);
    client.getAccount().makeWithdrawal(amount);
  }


  public Set<StatementLine> getAccountHistory(int clientId) {
    Client client = clients.get(clientId);
    return client.getAccount().getHistory();
  }

  public void createClient(int clientId) {
    clients.putIfAbsent(clientId, new Client());
  }
}
