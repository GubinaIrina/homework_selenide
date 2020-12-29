package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.KeyDownAction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.Month;
import java.util.Date;
import java.util.Calendar;
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
        $("body > div.popup.popup_direction_bottom-left.popup_target_anchor.popup_size_m.popup_visible" +
                ".popup_height_adaptive.popup_theme_alfa-on-white.input__popup")
                .findElement(withText("Москва")).click();
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
    void dateSelection() throws ParseException {
        $("[data-test-id='city'] input").setValue("Москва");
        LocalDate date = LocalDate.now();
        String currentDate = date.plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String dateTest = date.plusDays(7).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String dayTest = date.plusDays(7).format(DateTimeFormatter.ofPattern("d"));
        $("[data-test-id='date'] input").click();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date date1 = format.parse(currentDate);
        Date date2 = format.parse(dateTest);
        int month1 = date1.getMonth();
        int month2 = date2.getMonth();
        if (month2 > month1) {
            $("body > div.popup.popup_direction_bottom-left.popup_target_anchor.popup_size_s.popup_visible" +
                    ".popup_padded.popup_theme_alfa-on-white > div > div > div > div > div > div > div:nth-child(4)")
                    .click();
        }
        $("table").findElement(withText(dayTest)).click();
        $("[data-test-id='name'] input").setValue("Губина Ирина");
        $("[data-test-id='phone'] input").setValue("+79650001122");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='notification']").waitUntil(visible, 15000)
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + dateTest));
    }
}
