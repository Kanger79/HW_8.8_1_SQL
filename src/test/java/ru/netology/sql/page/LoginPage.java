package ru.netology.sql.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.sql.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement errorNotification =$("[data-test-id='error-notification']");

    private final SelenideElement emptyFieldLogin = $("[data-test-id='login'].input_invalid");
    private final SelenideElement emptyFieldPassword = $("[data-test-id='password'].input_invalid");

    public void verifyErrorNotificationVisibility() {
        errorNotification.shouldBe(visible);
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }
    public void findErrorNotificationVisibility(String expectedText) {
        errorNotification.shouldBe(Condition.exactText(expectedText), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    public void findErrorIfEmptyFieldLogin (String expectedText) {
        emptyFieldLogin.shouldHave(Condition.exactText(expectedText), Duration.ofSeconds(3)).shouldBe(Condition.visible);
    }

    public void findErrorIfEmptyFieldPassword (String expectedText) {
        emptyFieldPassword.shouldHave(Condition.exactText(expectedText), Duration.ofSeconds(3)).shouldBe(Condition.visible);
    }

    public void clearLoginFieldAndPassword() {
        loginField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        passwordField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
    }

}
