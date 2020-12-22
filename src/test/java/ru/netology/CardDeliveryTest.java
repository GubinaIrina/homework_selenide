package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.KeyDownAction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
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
        $("[data-test-id='city'] input").setValue("Мо")
                .sendKeys(Keys.chord(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ENTER));
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
        String dateTest = date.plusDays(7).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.ARROW_DOWN, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT,
                        Keys.ARROW_RIGHT, Keys.ENTER));
        $("[data-test-id='name'] input").setValue("Губина Ирина");
        $("[data-test-id='phone'] input").setValue("+79650001122");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='notification']").waitUntil(visible, 15000)
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + dateTest));
    }
}
