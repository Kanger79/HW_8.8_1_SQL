package ru.netology.sql.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private static Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

    public static AuthInfo getAuthInfoInBase() {
        return new AuthInfo("petya", "123qwerty");
    }

    private static String generateRandomLogin() {
        return faker.name().username();
    }

    private static String generateRandomPassword() {
        return faker.internet().password();
    }

    public static AuthInfo getRandomLogin() {
        return new AuthInfo(generateRandomLogin(), "qwerty123");
    }

    public static AuthInfo getRandomPassword() {
        return new AuthInfo("vasya", generateRandomPassword());
    }
    public static AuthInfo getNoLoginValidPassword() {
        return new AuthInfo ("", "qwerty123");
    }

    public static AuthInfo getValidLoginNoPassword() {
        return new AuthInfo ("petya", "");
    }

    public static VerificationCode generateRandomVerificationCode() {
        return new VerificationCode(faker.numerify("######"));
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    @Value
    public static class VerificationCode {
        String code;
    }

}
