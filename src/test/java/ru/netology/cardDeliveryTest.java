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

public class cardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void deliveryCardForm() {
        LocalDate date = LocalDate.now().plusDays(4); //локальная дата
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        String text = date.format(formatter);

        SelenideElement form = $(".form");

        form.$("[data-test-id='city'] .input__control").setValue("Махачкала");
        form.$("[data-test-id='date'] .input__control").sendKeys(Keys.CONTROL + "a"); //выделяет текст в поле
        form.$("[data-test-id='date'] .input__control").sendKeys(Keys.DELETE); // удаляет выделеный текст
        form.$("[data-test-id='date'] .input__control").setValue(text);
        form.$("[data-test-id='name'] .input__control").setValue("Мирча Луческу");
        form.$("[data-test-id='phone'] .input__control").setValue("+79880000000");
        form.$("[data-test-id='agreement']").click();
        $(".button").click();
        $(".button").should(Condition.appear, Duration.ofSeconds(15));
        SelenideElement notification = $("[data-test-id='notification']").shouldBe(hidden);
        notification.$(withText("Успешно")).shouldBe(hidden);
    }

    @Test
    void dropdownList() throws InterruptedException {
        LocalDate date = LocalDate.now().plusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        String text = date.format(formatter);

        SelenideElement form = $(".form");

        form.$("[data-test-id='city'] .input__control").setValue("Ки");
        $$("div.popup div.menu-item span.menu-item__control").findBy(Condition.text("Киров")).click();
        form.$("[data-test-id='date'] .input__control").sendKeys(Keys.CONTROL + "a");
        form.$("[data-test-id='date'] .input__control").sendKeys(Keys.DELETE);
        form.$("[data-test-id='date'] .input__icon").click();
        $(byText(String.valueOf(date.getDayOfMonth()))).click(); //выбор даты где отображается календарь
        Thread.sleep(5000);
    }
}
