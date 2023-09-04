package ru.netology.sql.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.sql.data.DataHelper;
import ru.netology.sql.data.SQLHelper;
import ru.netology.sql.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.sql.data.SQLHelper.cleanDatabase;

public class SqlTest {

    @AfterAll
    static void teardown() {
        cleanDatabase();
    }

    @Test
    @DisplayName("Should successfully login with exist login and password from sut")
    void shouldSuccessfulLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoInBase();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    @DisplayName("Should get error notification if random verification code")
    void shouldGetErrorIfRandomVerificationCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoInBase();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotificationVisibility();
    }

    @Test
    @DisplayName("Should get error if random login")
    void shouldGetErrorIfRandomLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getRandomLogin();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisibility();
    }

    @Test
    @DisplayName("Should get error if random password")
    void shouldGetErrorIfRandomPassword() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfoLogin = DataHelper.getRandomPassword();
        loginPage.validLogin(authInfoLogin);
        loginPage.verifyErrorNotificationVisibility();
    }

    @Test
    @DisplayName("Should get error if no login")
    void shouldGetErrorIfNoLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfoLogin = DataHelper.getNoLoginValidPassword();
        loginPage.validLogin(authInfoLogin);
        loginPage.findErrorIfEmptyFieldLogin("Логин\n" +
                "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Should get error if no password")
    void shouldGetErrorIfNoPassword() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfoLogin = DataHelper.getValidLoginNoPassword();
        loginPage.validLogin(authInfoLogin);
        loginPage.findErrorIfEmptyFieldPassword("Пароль\n" +
                "Поле обязательно для заполнения");
    }


    @Test
    @DisplayName("Should get error if input wrong password three times")
    public void shouldGetErrorIfInputWrongPasswordThreeTimes() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoInBase();
        var authInfoLogin = DataHelper.getRandomPassword();
        loginPage.validLogin(authInfoLogin);
        loginPage.verifyErrorNotificationVisibility();
        loginPage.clearLoginFieldAndPassword();
        loginPage.validLogin(authInfoLogin);
        loginPage.verifyErrorNotificationVisibility();
        loginPage.clearLoginFieldAndPassword();
        loginPage.validLogin(authInfoLogin);
        loginPage.verifyErrorNotificationVisibility();
        loginPage.clearLoginFieldAndPassword();
        loginPage.validLogin(authInfoLogin);
        loginPage.verifyErrorNotificationVisibility();
        loginPage.clearLoginFieldAndPassword();

        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisibility();

    }
}
