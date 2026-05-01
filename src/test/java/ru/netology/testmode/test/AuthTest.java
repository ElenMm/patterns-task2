package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
        //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
        //  пользователя registeredUser
        // Сокращаем круг поиска - поиск по форме
        SelenideElement form = $(".form");

        // Ищем и заполняем поле Логин
        form.$("[name = 'login']").setValue(registeredUser.getLogin());

        // Ищем и заполняем поле Пароль
        form.$("[name = 'password']").setValue(registeredUser.getPassword());

        // Ищем и кликаем на кнопку Продолжить
        form.$("[data-test-id = 'action-login']").click();

        // Проверяем вход в личный кабинет
        $("h2").should(Condition.and("Все условия", Condition.text("Личный кабинет"), Condition.visible));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
        // Сокращаем круг поиска - поиск по форме
        SelenideElement form = $(".form");

        // Ищем и заполняем поле Логин
        form.$("[name = 'login']").setValue(notRegisteredUser.getLogin());

        // Ищем и заполняем поле Пароль
        form.$("[name = 'password']").setValue(notRegisteredUser.getPassword());

        // Ищем и кликаем на кнопку Продолжить
        form.$("[data-test-id = 'action-login']").click();

        // Проверяем ошибку - незарегистрированный пользователь
        $("[data-test-id = 'error-notification']").should(Condition.visible).
                should(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
        // Сокращаем круг поиска - поиск по форме
        SelenideElement form = $(".form");

        // Ищем и заполняем поле Логин
        form.$("[name = 'login']").setValue(blockedUser.getLogin());

        // Ищем и заполняем поле Пароль
        form.$("[name = 'password']").setValue(blockedUser.getPassword());

        // Ищем и кликаем на кнопку Продолжить
        form.$("[data-test-id = 'action-login']").click();

        // Проверяем ошибку - заблокированный пользователь
        $("[data-test-id = 'error-notification']").should(Condition.visible).
                should(Condition.text("Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser
        // Сокращаем круг поиска - поиск по форме
        SelenideElement form = $(".form");

        // Ищем и заполняем поле Логин
        form.$("[name = 'login']").setValue(wrongLogin);

        // Ищем и заполняем поле Пароль
        form.$("[name = 'password']").setValue(registeredUser.getPassword());

        // Ищем и кликаем на кнопку Продолжить
        form.$("[data-test-id = 'action-login']").click();

        // Проверяем ошибку - незарегистрированный пользователь
        $("[data-test-id = 'error-notification']").should(Condition.visible).
                should(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
        //  "Пароль" - переменную wrongPassword
        // Сокращаем круг поиска - поиск по форме
        SelenideElement form = $(".form");

        // Ищем и заполняем поле Логин
        form.$("[name = 'login']").setValue(registeredUser.getLogin());

        // Ищем и заполняем поле Пароль
        form.$("[name = 'password']").setValue(wrongPassword);

        // Ищем и кликаем на кнопку Продолжить
        form.$("[data-test-id = 'action-login']").click();

        // Проверяем ошибку - незарегистрированный пользователь
        $("[data-test-id = 'error-notification']").should(Condition.visible).
                should(Condition.text("Неверно указан логин или пароль"));
    }
}
