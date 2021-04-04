package ru.netology.web.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.openqa.selenium.support.FindBy;
import ru.alfabank.alfatest.cucumber.annotations.Name;
import ru.alfabank.alfatest.cucumber.api.AkitaPage;

import static com.codeborne.selenide.Selenide.*;


@Name("Дашбоард")
public class DashboardPage extends AkitaPage {
    @FindBy(css = "[data-test-id=dashboard]")
    private SelenideElement heading;

    private static final String transferButton = " [data-test-id=action-deposit]";

    public static int getCardBalance(int toCardCount) {
        String text = $$(".list__item").get(toCardCount - 1).getText();
        return extractBalance(text);
    }

    private static int extractBalance(String text) {
        String tmp = text.split(":")[1];
        String value = tmp.substring(0, tmp.indexOf("р.")).trim();
        return Integer.parseInt(value);
    }

    public static TransferPage transfer(int toCardCount) {
        $$("[data-test-id=action-deposit]").get(toCardCount - 1).click();
        return Selenide.page(TransferPage.class);
    }
}
