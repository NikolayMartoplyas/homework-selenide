package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class СardDeliveryTest {

    private String formatDate(int days, String pattern) {
        LocalDate date = LocalDate.now().plusDays(days);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void deliveryCardForm() throws InterruptedException {
        String planing = formatDate(4, "dd.MM.yyyy");
        SelenideElement form = $(".form");

        form.$("[data-test-id='city'] .input__control").setValue("Махачкала");
        form.$("[data-test-id='date'] .input__control").sendKeys(Keys.CONTROL + "a"); //выделяет текст в поле
        form.$("[data-test-id='date'] .input__control").sendKeys(Keys.DELETE); // удаляет выделеный текст
        form.$("[data-test-id='date'] .input__control").setValue(planing);
        form.$("[data-test-id='name'] .input__control").setValue("Мирча Луческу");
        form.$("[data-test-id='phone'] .input__control").setValue("+79880000000");
        form.$("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(text("Встреча успешно забронирована на " + planing));
    }

}
