package ru.netology.web.step;

import cucumber.api.java.ru.*;
import lombok.val;
import ru.alfabank.alfatest.cucumber.api.AkitaScenario;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.*;


import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static org.junit.Assert.assertEquals;
import static ru.alfabank.tests.core.helpers.PropertyLoader.loadProperty;

public class TemplateSteps {
  private final AkitaScenario scenario = AkitaScenario.getInstance();

  @Пусть("^пользователь залогинен с именем \"([^\"]*)\" и паролем \"([^\"]*)\"$")
  public void loginWithNameAndPassword(String login, String password) {
    val loginUrl = loadProperty("loginUrl");
    open(loginUrl);

    scenario.setCurrentPage(page(LoginPage.class));
    val loginPage = (LoginPage) scenario.getCurrentPage().appeared();
    val authInfo = new DataHelper.AuthInfo(login, password);
    scenario.setCurrentPage(loginPage.validLogin(authInfo));

    val verificationPage = (VerificationPage) scenario.getCurrentPage().appeared();
    val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    scenario.setCurrentPage(verificationPage.validVerify(verificationCode));

    scenario.getCurrentPage().appeared();
  }


  @Когда("^он переводит \"([^\"]*)\" рублей с карты с номером \"([^\"]*)\" на свою \"([^\"]*)\" карту с главной страницы")
  public void transfer(String amount, String fromCard, int toCardCount) {
    val dashboardPage = (DashboardPage) scenario.getCurrentPage().appeared();
    scenario.setCurrentPage(dashboardPage.transfer(toCardCount));
    val transferPage = (TransferPage) scenario.getCurrentPage().appeared();
    scenario.setCurrentPage(transferPage.transferAmount(amount.trim(), fromCard.trim()));
    scenario.getCurrentPage().appeared();
  }

  @Тогда("^баланс его \"([^\"]*)\" карты из списка на главной странице должен стать \"([^\"]*)\" рублей")
  public void checkBalance(int toCardCount, String expectedBalance) {

    val dashboardPage = (DashboardPage) scenario.getCurrentPage().appeared();
    int actualBalance = dashboardPage.getCardBalance(toCardCount);
    int expected = Integer.parseInt(expectedBalance.replace(" ",""));
    assertEquals(expected, actualBalance);
  }

}
