package ru.netology.tests.ui;

import org.junit.jupiter.api.*;
import ru.netology.helpers.DataHelper;
import ru.netology.pages.CardCreditPaymentPage;
import ru.netology.pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardCreditNegativeTests extends BaseUITest {

    private final String subMessageWrongDataFormat = "Неверный формат";
    private final String subMessageWrongDate = "Неверно указан срок действия карты";
    private final String subMessageEmptyData = "Поле обязательно для заполнения";
    private final String subMessageExpiredDate = "Истёк срок действия карты";

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с валидными данными, но без номера карты")
    void testFailedBuyingTourByCardWithoutNumberOfCard() {
        MainPage mainPage = new MainPage();
        CardCreditPaymentPage cardCreditPaymentPage = mainPage.buyTourOnCreditCard();
        cardCreditPaymentPage.buyThroughCredit(DataHelper.formDataOfCardNumberIsVoid());
        cardCreditPaymentPage.cardNumberFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой и валидными полями, но пустым полем 'месяц'")
    void testFailedBuyingTourByCardWithoutFormMonth() {
        MainPage mainPage = new MainPage();
        CardCreditPaymentPage cardCreditPaymentPage = mainPage.buyTourOnCreditCard();
        cardCreditPaymentPage.buyThroughCredit(DataHelper.formDataOfCardMonthIsValidVoid());
        cardCreditPaymentPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой и валидными полями, но пустым полем 'год'")
    void testFailedBuyingTourByCardWithoutFormYear() {
        MainPage mainPage = new MainPage();
        CardCreditPaymentPage cardCreditPaymentPage = mainPage.buyTourOnCreditCard();
        cardCreditPaymentPage.buyThroughCredit(DataHelper.formDataOfCardYearIsValidVoid());
        cardCreditPaymentPage.cardYearFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой и валидными полями, но пустым полем 'Владелец'")
    void testFailedBuyingTourByCardWithoutFormOwner() {
        MainPage mainPage = new MainPage();
        CardCreditPaymentPage cardCreditPaymentPage = mainPage.buyTourOnCreditCard();
        cardCreditPaymentPage.buyThroughCredit(DataHelper.formDataOfCardOwnerIsVoid());
        cardCreditPaymentPage.cardOwnerFieldMessageIsSubMessage(subMessageEmptyData);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой и валидными полями, но пустым полем 'CVV/CVS'")
    void testFailedBuyingTourByCardWithoutFormCVV() {
        MainPage mainPage = new MainPage();
        CardCreditPaymentPage cardCreditPaymentPage = mainPage.buyTourOnCreditCard();
        cardCreditPaymentPage.buyThroughCredit(DataHelper.formDataOfCardCodeIsVoid());
        cardCreditPaymentPage.cardCodeFieldMessageIsSubMessage(subMessageWrongDataFormat); //todo: баг. Поле кардхолдера отображается, как пустое
    }

    @Test
    @DisplayName("Отправление незаполненной формы покупки тура в Кредит с рабочей карты")
    void testFailedBuyingTourByCardWithUnfilledForm() {
        MainPage mainPage = new MainPage();
        CardCreditPaymentPage cardCreditPaymentPage = mainPage.buyTourOnCreditCard();
        cardCreditPaymentPage.sendVoidCreditForm();
        cardCreditPaymentPage.cardNumberFieldMessageIsSubMessage(subMessageWrongDataFormat);
        cardCreditPaymentPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDataFormat);
        cardCreditPaymentPage.cardYearFieldMessageIsSubMessage(subMessageWrongDataFormat);
        cardCreditPaymentPage.cardOwnerFieldMessageIsSubMessage(subMessageEmptyData);
        cardCreditPaymentPage.cardCodeFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой и валидными полями, но пустым полем 'Номер карты'")
    void testFailedBuyingTourByCardWithoutFormNumberOfCard() {
        MainPage mainPage = new MainPage();
        CardCreditPaymentPage cardCreditPaymentPage = mainPage.buyTourOnCreditCard();
        cardCreditPaymentPage.buyThroughCredit(DataHelper.formDataOfCardOwnerIsFalse());
        cardCreditPaymentPage.cardOwnerFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой, где поле 'год' невалидное, а остальные - валидные")
    void testFailedBuyingTourByCardWithInvalidFormYear() {
        MainPage mainPage = new MainPage();
        CardCreditPaymentPage cardCreditPaymentPage = mainPage.buyTourOnCreditCard();
        cardCreditPaymentPage.buyThroughCredit(DataHelper.formDataOfCardYearsIsOverdue());
        cardCreditPaymentPage.cardYearFieldMessageIsSubMessage(subMessageExpiredDate);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой, где поле 'месяц' невалидное (из одной цифры), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormMonthOneFigure() {
        MainPage mainPage = new MainPage();
        CardCreditPaymentPage cardCreditPaymentPage = mainPage.buyTourOnCreditCard();
        cardCreditPaymentPage.buyThroughCredit(DataHelper.oneSymbolFormDataNumberOfMonth());
        cardCreditPaymentPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой, где поле 'месяц' невалидное (из двух нулей), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormMonthZeroZero() {
        MainPage mainPage = new MainPage();
        CardCreditPaymentPage cardCreditPaymentPage = mainPage.buyTourOnCreditCard();
        cardCreditPaymentPage.buyThroughCredit(DataHelper.approvedFormOfCardDataAndZeroZeroMonth ());
        cardCreditPaymentPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDataFormat); //todo: баг с двумя нулями. Можно купить
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой, где поле 'месяц' невалидное (больше 12), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormMonthThirteen() {
        MainPage mainPage = new MainPage();
        CardCreditPaymentPage cardCreditPaymentPage = mainPage.buyTourOnCreditCard();
        cardCreditPaymentPage.buyThroughCredit(DataHelper.formDataOfCardMonthIsWrong ());
        cardCreditPaymentPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDate);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой, где поле 'месяц' невалидное (прошедший  месяц), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormMonthPrevious() {
        MainPage mainPage = new MainPage();
        CardCreditPaymentPage cardCreditPaymentPage = mainPage.buyTourOnCreditCard();
        cardCreditPaymentPage.buyThroughCredit(DataHelper.approvedFormOfCardDataAndEarlyMonth());
        cardCreditPaymentPage.completeNotice();
        assertEquals("APPROVED", DataHelper.getStatusOfBuingOnCreditTransaction());
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой, где поле 'год' невалидное (прошедший год), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormYearPrevious() {
        MainPage mainPage = new MainPage();
        CardCreditPaymentPage cardCreditPaymentPage = mainPage.buyTourOnCreditCard();
        cardCreditPaymentPage.buyThroughCredit(DataHelper.approvedFormOfCardDataAndEarlyYear());
        cardCreditPaymentPage.cardYearFieldMessageIsSubMessage(subMessageExpiredDate);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с невалидным полем номера карты, но остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormNumberOfCard() {
        MainPage mainPage = new MainPage();
        CardCreditPaymentPage cardCreditPaymentPage = mainPage.buyTourOnCreditCard();
        cardCreditPaymentPage.buyThroughCredit(DataHelper.declinedCardFormData());
        cardCreditPaymentPage.failedNotice(); //todo: тест верный. Это баг
        assertEquals("DECLINED", DataHelper.getStatusOfBuingOnCreditTransaction());
    }
}
