package ru.netology.web.data;

import com.codeborne.selenide.Selenide;
import lombok.Value;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.TransferPage;

public class DataHelper {
  private DataHelper() {}

  @Value
  public static class AuthInfo {
    private String login;
    private String password;
  }

  public static AuthInfo getAuthInfo() {
    return new AuthInfo("vasya", "qwerty123");
  }

  public static AuthInfo getOtherAuthInfo(AuthInfo original) {
    return new AuthInfo("petya", "123qwerty");
  }

  @Value
  public static class VerificationCode {
    private String code;
  }

  public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
    return new VerificationCode("12345");
  }

  @Value
  public static class Cards {
    private String cardNumber;
    private String id;
    private int cardBalance;
  }

  public static Cards firstCard() {
    return new Cards(
            "5559000000000001",
            "[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']",
            10000);
  }

  public static Cards secondCard() {
    return new Cards(
            "5559000000000002",
            "[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']",
            10000);
  }

  public static void resetBalance(int firstCardBalance, int secondCardBalance) {

    if ((firstCardBalance == firstCard().getCardBalance()) && (secondCardBalance == secondCard().getCardBalance())) {
      return;
    }

    if (firstCardBalance < firstCard().getCardBalance()) {
      String differ = Integer.toString(firstCard().getCardBalance() - firstCardBalance);
      Selenide.page(DashboardPage.transfer(1));
      Selenide.page(TransferPage.transferAmount(differ, secondCard().getCardNumber()));

    }
    if (secondCardBalance < secondCard().getCardBalance()) {
      String differ = Integer.toString(secondCard().getCardBalance() - secondCardBalance);
      Selenide.page(DashboardPage.transfer(2));
      Selenide.page(TransferPage.transferAmount(differ, firstCard().getCardNumber()));

    }
  }
}
