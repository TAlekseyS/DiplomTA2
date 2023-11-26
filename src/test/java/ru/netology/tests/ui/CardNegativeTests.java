package ru.netology.tests.ui;


import org.junit.jupiter.api.*;
import ru.netology.helpers.DataHelper;
import ru.netology.pages.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardNegativeTests extends BaseUITest {

    private final String subMessageWrongDataFormat = "Неверный формат";
    private final String subMessageWrongDate = "Неверно указан срок действия карты";
    private final String subMessageEmptyData = "Поле обязательно для заполнения";
    private final String subMessageExpiredDate = "Истёк срок действия карты";

    @Test
    @DisplayName("Отправление формы покупки тура с валидными данными, но без номера карты")
    void testFailedBuyingTourByCardWithoutNumberOfCard() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourOnDebetCard();
        cardCheckoutPage.buyOnCredit(DataHelper.formDataOfCardNumberIsVoid());
        cardCheckoutPage.cardNumberFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой и валидными полями, но пустым полем 'месяц'")
    void testFailedBuyingTourByCardWithoutFormMonth() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourOnDebetCard();
        cardCheckoutPage.buyOnCredit(DataHelper.formDataOfCardMonthIsValidVoid());
        cardCheckoutPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой и валидными полями, но пустым полем 'год'")
    void testFailedBuyingTourByCardWithoutFormYear() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourOnDebetCard();
        cardCheckoutPage.buyOnCredit(DataHelper.formDataOfCardYearIsValidVoid());
        cardCheckoutPage.cardYearFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой и валидными полями, но пустым полем 'Владелец'")
    void testFailedBuyingTourByCardWithoutFormOwner() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourOnDebetCard();
        cardCheckoutPage.buyOnCredit(DataHelper.formDataOfCardOwnerIsVoid());
        cardCheckoutPage.cardOwnerFieldMessageIsSubMessage(subMessageEmptyData);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой и валидными полями, но пустым полем 'CVV/CVS'")
    void testFailedBuyingTourByCardWithoutFormCVV() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourOnDebetCard();
        cardCheckoutPage.buyOnCredit(DataHelper.formDataOfCardCodeIsVoid());
        cardCheckoutPage.cardCodeFieldMessageIsSubMessage(subMessageWrongDataFormat); //todo: баг. Поле кардхолдера отображается, как пустое
    }

    @Test
    @DisplayName("Отправление незаполненной формы покупки тура с рабочей карты")
    void testFailedBuyingTourByCardWithUnfilledForm() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourOnDebetCard();
        cardCheckoutPage.sendVoidDebetForm();
        cardCheckoutPage.cardNumberFieldMessageIsSubMessage(subMessageWrongDataFormat);
        cardCheckoutPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDataFormat);
        cardCheckoutPage.cardYearFieldMessageIsSubMessage(subMessageWrongDataFormat);
        cardCheckoutPage.cardOwnerFieldMessageIsSubMessage(subMessageEmptyData);
        cardCheckoutPage.cardCodeFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой и валидными полями, но пустым полем 'Номер карты'")
    void testFailedBuyingTourByCardWithoutFormNumberOfCard() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourOnDebetCard();
        cardCheckoutPage.buyOnCredit(DataHelper.formDataOfCardOwnerIsFalse());
        cardCheckoutPage.cardOwnerFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой, где поле 'год' невалидное, а остальные - валидные")
    void testFailedBuyingTourByCardWithInvalidFormYear() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourOnDebetCard();
        cardCheckoutPage.buyOnCredit(DataHelper.formDataOfCardYearsIsOverdue());
        cardCheckoutPage.cardYearFieldMessageIsSubMessage(subMessageExpiredDate);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой, где поле 'месяц' невалидное (из одной цифры), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormMonthOneFigure() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourOnDebetCard();
        cardCheckoutPage.buyOnCredit(DataHelper.oneSymbolFormDataNumberOfMonth());
        cardCheckoutPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой, где поле 'месяц' невалидное (из двух нулей), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormMonthZeroZero() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourOnDebetCard();
        cardCheckoutPage.buyOnCredit(DataHelper.approvedFormOfCardDataAndZeroZeroMonth ());
        cardCheckoutPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDataFormat); //todo: баг с двумя нулями. Можно купить
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой, где поле 'месяц' невалидное (больше 12), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormMonthThirteen() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourOnDebetCard();
        cardCheckoutPage.buyOnCredit(DataHelper.formDataOfCardMonthIsWrong ());
        cardCheckoutPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDate);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой, где поле 'месяц' невалидное (прошедший  месяц), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormMonthPrevious() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourOnDebetCard();
        cardCheckoutPage.buyOnCredit(DataHelper.approvedFormOfCardDataAndEarlyMonth());
        cardCheckoutPage.completeNotice();
        assertEquals("APPROVED", DataHelper.getStatusOfBuingTransaction());
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой, где поле 'год' невалидное (прошедший год), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormYearPrevious() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourOnDebetCard();
        cardCheckoutPage.buyOnCredit(DataHelper.approvedFormOfCardDataAndEarlyYear());
        cardCheckoutPage.cardYearFieldMessageIsSubMessage(subMessageExpiredDate);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с невалидным полем номера карты, но остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormNumberOfCard() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourOnDebetCard();
        cardCheckoutPage.buyOnCredit(DataHelper.declinedCardFormData());
        cardCheckoutPage.failedNotice(); //todo: тест верный. Это баг
        assertEquals("DECLINED", DataHelper.getStatusOfBuingTransaction());
    }
}