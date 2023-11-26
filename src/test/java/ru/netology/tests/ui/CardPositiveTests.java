package ru.netology.tests.ui;

import org.junit.jupiter.api.*;
import ru.netology.helpers.DataHelper;
import ru.netology.pages.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardPositiveTests extends BaseUITest {

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей карты, на текущий год и месяц и валидными данными")
    void testSuccessfulBuyingTourOnCurrentMY() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourOnDebetCard();
        cardCheckoutPage.buyOnCredit(DataHelper.approvedFormOfCardData());
        cardCheckoutPage.completeNotice();
        assertEquals("APPROVED", DataHelper.getStatusOfBuingTransaction());
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей карты, на текущий год и следующий месяц и валидными данными")
    void testSuccessfulBuyingTourOnCurrentYAndNextMont() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourOnDebetCard();
        cardCheckoutPage.buyOnCredit(DataHelper.approvedFarmOfCardDataAndNextMonth());
        cardCheckoutPage.completeNotice();
        assertEquals("APPROVED", DataHelper.getStatusOfBuingTransaction());
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей карты, на следующий год и следующий месяц и валидными данными")
    void testSuccessfulBuyingTourOnNextMY() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourOnDebetCard();
        cardCheckoutPage.buyOnCredit(DataHelper.approvedFarmOfCardDataAndNextYear());
        cardCheckoutPage.completeNotice();
        assertEquals("APPROVED", DataHelper.getStatusOfBuingTransaction());
    }
}