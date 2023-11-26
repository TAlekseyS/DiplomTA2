package ru.netology.tests.ui;

import org.junit.jupiter.api.*;
import ru.netology.helpers.DataHelper;
import ru.netology.pages.CreditCheckoutPage;
import ru.netology.pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditPositiveTests extends BaseUITest {

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей карты, на текущий год и месяц и валидными данными")
    void testSuccessfulBuyingTourOnCurrentMY() {
        MainPage mainPage = new MainPage();
        CreditCheckoutPage creditCheckoutPage = mainPage.buyTourOnCreditCard();
        creditCheckoutPage.buyThroughCredit(DataHelper.approvedFormOfCardData());
        creditCheckoutPage.completeNotice();
        assertEquals("APPROVED", DataHelper.getStatusOfBuingOnCreditTransaction());
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей карты, на текущий год и следующий месяц и валидными данными")
    void testSuccessfulBuyingTourOnCurrentYAndNextMont() {
        MainPage mainPage = new MainPage();
        CreditCheckoutPage creditCheckoutPage = mainPage.buyTourOnCreditCard();
        creditCheckoutPage.buyThroughCredit(DataHelper.approvedFarmOfCardDataAndNextMonth());
        creditCheckoutPage.completeNotice();
        assertEquals("APPROVED", DataHelper.getStatusOfBuingOnCreditTransaction());
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей карты, на следующий год и следующий месяц и валидными данными")
    void testSuccessfulBuyingTourOnNextMY() {
        MainPage mainPage = new MainPage();
        CreditCheckoutPage creditCheckoutPage = mainPage.buyTourOnCreditCard();
        creditCheckoutPage.buyThroughCredit(DataHelper.approvedFarmOfCardDataAndNextYear());
        creditCheckoutPage.completeNotice();
        assertEquals("APPROVED", DataHelper.getStatusOfBuingOnCreditTransaction());
    }
}