package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    @BeforeEach
    void openWebPage() {
        open("http://localhost:9999");
    }

    @Test
    void shouldArrangeDelivery() {
        $("[data-test-id='city'] input").setValue("Москва");
        LocalDate date = LocalDate.now();
        String dateTest = date.plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("input[placeholder=\"Дата встречи\"]").setValue(dateTest);
        $("[data-test-id='name'] input").setValue("Губина Ирина");
        $("[data-test-id='phone'] input").setValue("+79650001122");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='notification']").waitUntil(visible, 15000)
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + dateTest));
    }

    @Test
    void selectingACityFromTheList() {
        $("[data-test-id='city'] input").setValue("Мо");
        $(".menu_mode_radio-check").findElement(withText("Москва")).click();
        LocalDate date = LocalDate.now();
        String dateTest = date.plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("input[placeholder=\"Дата встречи\"]").setValue(dateTest);
        $("[data-test-id='name'] input").setValue("Губина Ирина");
        $("[data-test-id='phone'] input").setValue("+79650001122");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='notification']").waitUntil(visible, 15000)
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + dateTest));
    }

    @Test
    void dateSelection() {
        $("[data-test-id='city'] input").setValue("Москва");
        LocalDate date = LocalDate.now();
        LocalDate currentDate = date.plusDays(3);
        LocalDate dateTest = date.plusDays(7);
        $("[data-test-id='date'] input").click();
        if (dateTest.getYear() > currentDate.getYear()) {
            $("[data-step=\"12\"]").click();
        }
        if (!dateTest.getMonth().equals(currentDate.getMonth())) {
            $("[data-step=\"1\"]").click();
        }
        $("table").findElement(withText(String.valueOf(dateTest.getDayOfMonth()))).click();
        $("[data-test-id='name'] input").setValue("Губина Ирина");
        $("[data-test-id='phone'] input").setValue("+79650001122");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='notification']").waitUntil(visible, 15000)
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " +
                        dateTest.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
    }
}
