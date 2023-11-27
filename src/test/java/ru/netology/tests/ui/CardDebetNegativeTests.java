package ru.netology.tests.ui;


import org.junit.jupiter.api.*;
import ru.netology.helpers.DataHelper;
import ru.netology.pages.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDebetNegativeTests extends BaseUITest {

    private final String subMessageWrongDataFormat = "Неверный формат";
    private final String subMessageWrongDate = "Неверно указан срок действия карты";
    private final String subMessageEmptyData = "Поле обязательно для заполнения";
    private final String subMessageExpiredDate = "Истёк срок действия карты";

    @Test
    @DisplayName("Отправление формы покупки тура с валидными данными, но без номера карты")
    void testFailedBuyingTourByCardWithoutNumberOfCard() {
        MainPage mainPage = new MainPage();
        CardDebetPaymentPage cardDebetPaymentPage = mainPage.buyTourOnDebetCard();
        cardDebetPaymentPage.buyOnCredit(DataHelper.formDataOfCardNumberIsVoid());
        cardDebetPaymentPage.cardNumberFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой и валидными полями, но пустым полем 'месяц'")
    void testFailedBuyingTourByCardWithoutFormMonth() {
        MainPage mainPage = new MainPage();
        CardDebetPaymentPage cardDebetPaymentPage = mainPage.buyTourOnDebetCard();
        cardDebetPaymentPage.buyOnCredit(DataHelper.formDataOfCardMonthIsValidVoid());
        cardDebetPaymentPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой и валидными полями, но пустым полем 'год'")
    void testFailedBuyingTourByCardWithoutFormYear() {
        MainPage mainPage = new MainPage();
        CardDebetPaymentPage cardDebetPaymentPage = mainPage.buyTourOnDebetCard();
        cardDebetPaymentPage.buyOnCredit(DataHelper.formDataOfCardYearIsValidVoid());
        cardDebetPaymentPage.cardYearFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой и валидными полями, но пустым полем 'Владелец'")
    void testFailedBuyingTourByCardWithoutFormOwner() {
        MainPage mainPage = new MainPage();
        CardDebetPaymentPage cardDebetPaymentPage = mainPage.buyTourOnDebetCard();
        cardDebetPaymentPage.buyOnCredit(DataHelper.formDataOfCardOwnerIsVoid());
        cardDebetPaymentPage.cardOwnerFieldMessageIsSubMessage(subMessageEmptyData);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой и валидными полями, но пустым полем 'CVV/CVS'")
    void testFailedBuyingTourByCardWithoutFormCVV() {
        MainPage mainPage = new MainPage();
        CardDebetPaymentPage cardDebetPaymentPage = mainPage.buyTourOnDebetCard();
        cardDebetPaymentPage.buyOnCredit(DataHelper.formDataOfCardCodeIsVoid());
        cardDebetPaymentPage.cardCodeFieldMessageIsSubMessage(subMessageWrongDataFormat); //todo: баг. Поле кардхолдера отображается, как пустое
    }

    @Test
    @DisplayName("Отправление незаполненной формы покупки тура с рабочей карты")
    void testFailedBuyingTourByCardWithUnfilledForm() {
        MainPage mainPage = new MainPage();
        CardDebetPaymentPage cardDebetPaymentPage = mainPage.buyTourOnDebetCard();
        cardDebetPaymentPage.sendVoidDebetForm();
        cardDebetPaymentPage.cardNumberFieldMessageIsSubMessage(subMessageWrongDataFormat);
        cardDebetPaymentPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDataFormat);
        cardDebetPaymentPage.cardYearFieldMessageIsSubMessage(subMessageWrongDataFormat);
        cardDebetPaymentPage.cardOwnerFieldMessageIsSubMessage(subMessageEmptyData);
        cardDebetPaymentPage.cardCodeFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой и валидными полями, но пустым полем 'Номер карты'")
    void testFailedBuyingTourByCardWithoutFormNumberOfCard() {
        MainPage mainPage = new MainPage();
        CardDebetPaymentPage cardDebetPaymentPage = mainPage.buyTourOnDebetCard();
        cardDebetPaymentPage.buyOnCredit(DataHelper.formDataOfCardOwnerIsFalse());
        cardDebetPaymentPage.cardOwnerFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой, где поле 'год' невалидное, а остальные - валидные")
    void testFailedBuyingTourByCardWithInvalidFormYear() {
        MainPage mainPage = new MainPage();
        CardDebetPaymentPage cardDebetPaymentPage = mainPage.buyTourOnDebetCard();
        cardDebetPaymentPage.buyOnCredit(DataHelper.formDataOfCardYearsIsOverdue());
        cardDebetPaymentPage.cardYearFieldMessageIsSubMessage(subMessageExpiredDate);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой, где поле 'месяц' невалидное (из одной цифры), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormMonthOneFigure() {
        MainPage mainPage = new MainPage();
        CardDebetPaymentPage cardDebetPaymentPage = mainPage.buyTourOnDebetCard();
        cardDebetPaymentPage.buyOnCredit(DataHelper.oneSymbolFormDataNumberOfMonth());
        cardDebetPaymentPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой, где поле 'месяц' невалидное (из двух нулей), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormMonthZeroZero() {
        MainPage mainPage = new MainPage();
        CardDebetPaymentPage cardDebetPaymentPage = mainPage.buyTourOnDebetCard();
        cardDebetPaymentPage.buyOnCredit(DataHelper.approvedFormOfCardDataAndZeroZeroMonth ());
        cardDebetPaymentPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDataFormat); //todo: баг с двумя нулями. Можно купить
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой, где поле 'месяц' невалидное (больше 12), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormMonthThirteen() {
        MainPage mainPage = new MainPage();
        CardDebetPaymentPage cardDebetPaymentPage = mainPage.buyTourOnDebetCard();
        cardDebetPaymentPage.buyOnCredit(DataHelper.formDataOfCardMonthIsWrong ());
        cardDebetPaymentPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDate);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой, где поле 'месяц' невалидное (прошедший  месяц), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormMonthPrevious() {
        MainPage mainPage = new MainPage();
        CardDebetPaymentPage cardDebetPaymentPage = mainPage.buyTourOnDebetCard();
        cardDebetPaymentPage.buyOnCredit(DataHelper.approvedFormOfCardDataAndEarlyMonth());
        cardDebetPaymentPage.completeNotice();
        assertEquals("APPROVED", DataHelper.getStatusOfBuingTransaction());
    }

    @Test
    @DisplayName("Отправление формы покупки тура с рабочей картой, где поле 'год' невалидное (прошедший год), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormYearPrevious() {
        MainPage mainPage = new MainPage();
        CardDebetPaymentPage cardDebetPaymentPage = mainPage.buyTourOnDebetCard();
        cardDebetPaymentPage.buyOnCredit(DataHelper.approvedFormOfCardDataAndEarlyYear());
        cardDebetPaymentPage.cardYearFieldMessageIsSubMessage(subMessageExpiredDate);
    }

    @Test
    @DisplayName("Отправление формы покупки тура с невалидным полем номера карты, но остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormNumberOfCard() {
        MainPage mainPage = new MainPage();
        CardDebetPaymentPage cardDebetPaymentPage = mainPage.buyTourOnDebetCard();
        cardDebetPaymentPage.buyOnCredit(DataHelper.declinedCardFormData());
        cardDebetPaymentPage.failedNotice(); //todo: тест верный. Это баг
        assertEquals("DECLINED", DataHelper.getStatusOfBuingTransaction());
    }
}