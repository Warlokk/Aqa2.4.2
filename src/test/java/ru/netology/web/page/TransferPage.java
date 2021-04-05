package ru.netology.web.page;


import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.alfabank.alfatest.cucumber.annotations.Name;
import ru.alfabank.alfatest.cucumber.api.AkitaPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.Keys.*;

@Name("Параметры перевода")
public class TransferPage extends AkitaPage {

    @FindBy(css = "[data-test-id=amount] .input__control")
    private static SelenideElement amount;
    @FindBy(css = "[data-test-id='from'] .input__control")
    private static SelenideElement fromCard;
    @FindBy(css = "[data-test-id=action-transfer]")
    private static SelenideElement submitTransfer;
    @FindBy(css = "[data-test-id=action-cancel]")
    private static SelenideElement cancelTransfer;

    public static DashboardPage transferAmount(String sum, String from) {
        amount.sendKeys(CONTROL + "a", DELETE);
        amount.setValue(sum);
        fromCard.sendKeys(CONTROL + "a", DELETE);
        fromCard.setValue(from);
        submitTransfer.click();
        return Selenide.page(DashboardPage.class);
    }


}
