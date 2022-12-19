package org.bank.kata.app;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class App {

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String SIGN_PLUS = "+";

  public static final DecimalFormat AMOUNT_FORMATER = new DecimalFormat("0.00");
  public static final DateTimeFormatter DATETIME_FORMATTER_MONTH = DateTimeFormatter.ofPattern("LLL");
  public static final DateTimeFormatter DATETIME_FORMATTER_DAY = DateTimeFormatter.ofPattern("E d HH:mm:ss");
  private static final String YEAR_ALIGNMENT = "  ";
  public static final String MONTH_ALIGNMENT_RELATIVE_TO_YEAR = YEAR_ALIGNMENT + "  ";
  public static final String DAY_ALIGNMENT_RELATIVE_TO_MONTH = MONTH_ALIGNMENT_RELATIVE_TO_YEAR + "  ";
  public static Bank bank = new Bank();



  private  int getDesiredOperation() {
    displayAvailableOperationsMenu();
    return getIdFromKeyBoard();
  }

  private  void displayAvailableOperationsMenu() {
    String menu = """

          ########## CHOOSE AN OPERATION ##############
             1- Make a deposit
             2- Make a withdrawal
             3- See my history
             4- Exit
      """;
    System.out.println(menu);


  }

  private int getIdFromKeyBoard() {
    Scanner consoleIn = new Scanner(System.in);
    return consoleIn.nextInt();
  }

  private BigDecimal getAmountFromKeyBoard() {
    Scanner consoleIn = new Scanner(System.in);
    if (consoleIn.hasNextBigDecimal())
      return consoleIn.nextBigDecimal();
    else if (consoleIn.hasNextDouble())
      return new BigDecimal(consoleIn.nextDouble());
    else
      return new BigDecimal(consoleIn.next());

  }

  private void displayHistory(Set<StatementLine> history, BigDecimal clientAccountBalance) {
    String balanceSign = clientAccountBalance.doubleValue() > 0.00 ? SIGN_PLUS : "";
    String AMOUNT_COLOR = clientAccountBalance.doubleValue() > 0.00 ? ANSI_GREEN : ANSI_RED;
    System.out.println("YOUR ACCOUNT BALANCE IS : " + AMOUNT_COLOR + balanceSign + AMOUNT_FORMATER.format(clientAccountBalance) + ANSI_RESET);
    System.out.print("-----------------------------------------------------------------------");
    System.out.println();

    LocalDateTime currentDate = LocalDateTime.now();
    AtomicInteger currentYear = new AtomicInteger(currentDate.getYear());
    AtomicInteger currentMonth = new AtomicInteger(currentDate.getMonthValue());
    AtomicBoolean firstLine = new AtomicBoolean(true);
    history.forEach(statementLine -> {

      if (statementLine.getDateTime().getYear() != currentYear.get() || firstLine.get()) {
        currentYear.set(statementLine.getDateTime().getYear());
        System.out.println(YEAR_ALIGNMENT + currentYear);

      }
      if (statementLine.getDateTime().getMonthValue() != currentMonth.get() || firstLine.get()) {
        currentMonth.set(statementLine.getDateTime().getMonthValue());
        System.out.println(MONTH_ALIGNMENT_RELATIVE_TO_YEAR + DATETIME_FORMATTER_MONTH.format(statementLine.getDateTime()));
      }
      firstLine.set(false);


      int sign = statementLine.getDirection() == StatementKind.CREDIT ? 1 : -1;
      System.out.printf("%s%-20s                          %+20.2f",
        DAY_ALIGNMENT_RELATIVE_TO_MONTH,
        DATETIME_FORMATTER_DAY.format(statementLine.getDateTime()),
        statementLine.getAmount().doubleValue() * sign
      );
      System.out.println();
    });
  }

  private void exitApp() {
    System.out.println(" Thank's for your visit. See you soon");
    System.exit(0);
  }

  private void withdrawalMoney(int clientId) {
    BigDecimal clientBalance = bank.getClientBalance(clientId);
    String sign = clientBalance.doubleValue() > 0.00 ? SIGN_PLUS : "";
    System.out.println("Your current account balance : " + sign + AMOUNT_FORMATER.format(clientBalance));
    System.out.print("How much do you want to withdraw ? : ");
    BigDecimal amountToWithdraw = getAmountFromKeyBoard();
    bank.makeWithdrawal(clientId, amountToWithdraw);

    System.out.printf("withdrawal of %s with SUCCESS", amountToWithdraw);
    System.out.println();
    clientBalance = bank.getClientBalance(clientId);
    sign = clientBalance.doubleValue() > 0.00 ? SIGN_PLUS : "";
    System.out.println("The new balance is : " + sign + AMOUNT_FORMATER.format(clientBalance));
    System.out.println();
    serveClient(clientId);
  }

  private void showHistory(int clientId) {
    displayHistory(bank.getAccountHistory(clientId), bank.getClientBalance(clientId));
    serveClient(clientId);
  }

  public void start() {
    int clientId = getClientId();
    bank.createClient(clientId);
    serveClient(clientId);
  }

  private void depositMoney(int clientId) {
    BigDecimal clientBalance = bank.getClientBalance(clientId);
    String sign = clientBalance.doubleValue() > 0.00 ? SIGN_PLUS : "";
    System.out.println("Your current account balance : " + sign + AMOUNT_FORMATER.format(clientBalance));
    System.out.print("How much do you want to deposit ? : ");
    BigDecimal amountToDeposit = getAmountFromKeyBoard();
    bank.makeDeposit(clientId, amountToDeposit);

    System.out.printf("Deposit of %n with SUCCESS", amountToDeposit);
    System.out.println();
    clientBalance = bank.getClientBalance(clientId);
    sign = clientBalance.doubleValue() > 0.00 ? SIGN_PLUS : "";
    System.out.println("The new balance is : " + sign + AMOUNT_FORMATER.format(clientBalance));
    System.out.println();

    serveClient(clientId);
  }

  private void serveClient(int clientId) {
    int userActionChoice = getDesiredOperation();

    switch (userActionChoice) {
      case 1 -> depositMoney(clientId);
      case 2 -> withdrawalMoney(clientId);
      case 3 -> showHistory(clientId);
      case 4 -> exitApp();
      default -> {
        System.out.println("Bad choice, please choose 1, 2 or 3 only ");
        serveClient(clientId);
      }
    }
  }

  private int getClientId() {
    System.out.println();
    System.out.printf("######################## Welcome to %s Bank ######################", bank.getBankName());
    System.out.println();
    System.out.println();
    System.out.print(" Please enter your accound id (integer , ex: 14528 ): ");
    return getIdFromKeyBoard();
  }

}
