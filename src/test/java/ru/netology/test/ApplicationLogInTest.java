package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.*;

public class ApplicationLogInTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldLogIn() {
        val RequestData = generateValidUser();
        $("[data-test-id='login'] [name='login']").setValue(RequestData.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(RequestData.getPassword());
        $("[data-test-id='action-login'] [class='button__text']").click();
        $("h2").shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldShowUserAsBlocked() {
        val RequestData = generateBlockedUser();
        $("[data-test-id=login] [name=login]").setValue(RequestData.getLogin());
        $("[data-test-id=password] [name=password]").setValue(RequestData.getPassword());
        $("[data-test-id=action-login] [class=button__text]").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(exactText("Ошибка! Пользователь заблокирован"));

    }

    @Test
    void shouldShowInvalidPassword() {
        val RequestData = generateUserWithInvalidPassword();
        $("[data-test-id=login] [name=login]").setValue(RequestData.getLogin());
        $("[data-test-id=password] [name=password]").setValue(RequestData.getPassword());
        $("[data-test-id=action-login] [class=button__text]").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    void shouldShowInvalidLogin() {
        val RequestData = generateUserWithInvalidLogin();
        $("[data-test-id=login] [name=login]").setValue(RequestData.getLogin());
        $("[data-test-id=password] [name=password]").setValue(RequestData.getPassword());
        $("[data-test-id=action-login] [class=button__text]").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    void shouldShowNonRegisteredUser() {
        val RequestData = generateNonRegisteredUser();
        $("[data-test-id=login] [name=login]").setValue(RequestData.getLogin());
        $("[data-test-id=password] [name=password]").setValue(RequestData.getPassword());
        $("[data-test-id=action-login] [class=button__text]").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }

}