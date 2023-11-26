package ru.netology.tests.ui;

import org.junit.jupiter.api.*;
import ru.netology.helpers.DataHelper;
import ru.netology.pages.CreditCheckoutPage;
import ru.netology.pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditNegativeTests  extends BaseUITest {

    private final String subMessageWrongDataFormat = "Неверный формат";
    private final String subMessageWrongDate = "Неверно указан срок действия карты";
    private final String subMessageEmptyData = "Поле обязательно для заполнения";
    private final String subMessageExpiredDate = "Истёк срок действия карты";

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с валидными данными, но без номера карты")
    void testFailedBuyingTourByCardWithoutNumberOfCard() {
        MainPage mainPage = new MainPage();
        CreditCheckoutPage creditCheckoutPage = mainPage.buyTourOnCreditCard();
        creditCheckoutPage.buyThroughCredit(DataHelper.formDataOfCardNumberIsVoid());
        creditCheckoutPage.cardNumberFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой и валидными полями, но пустым полем 'месяц'")
    void testFailedBuyingTourByCardWithoutFormMonth() {
        MainPage mainPage = new MainPage();
        CreditCheckoutPage creditCheckoutPage = mainPage.buyTourOnCreditCard();
        creditCheckoutPage.buyThroughCredit(DataHelper.formDataOfCardMonthIsValidVoid());
        creditCheckoutPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой и валидными полями, но пустым полем 'год'")
    void testFailedBuyingTourByCardWithoutFormYear() {
        MainPage mainPage = new MainPage();
        CreditCheckoutPage creditCheckoutPage = mainPage.buyTourOnCreditCard();
        creditCheckoutPage.buyThroughCredit(DataHelper.formDataOfCardYearIsValidVoid());
        creditCheckoutPage.cardYearFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой и валидными полями, но пустым полем 'Владелец'")
    void testFailedBuyingTourByCardWithoutFormOwner() {
        MainPage mainPage = new MainPage();
        CreditCheckoutPage creditCheckoutPage = mainPage.buyTourOnCreditCard();
        creditCheckoutPage.buyThroughCredit(DataHelper.formDataOfCardOwnerIsVoid());
        creditCheckoutPage.cardOwnerFieldMessageIsSubMessage(subMessageEmptyData);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой и валидными полями, но пустым полем 'CVV/CVS'")
    void testFailedBuyingTourByCardWithoutFormCVV() {
        MainPage mainPage = new MainPage();
        CreditCheckoutPage creditCheckoutPage = mainPage.buyTourOnCreditCard();
        creditCheckoutPage.buyThroughCredit(DataHelper.formDataOfCardCodeIsVoid());
        creditCheckoutPage.cardCodeFieldMessageIsSubMessage(subMessageWrongDataFormat); //todo: баг. Поле кардхолдера отображается, как пустое
    }

    @Test
    @DisplayName("Отправление незаполненной формы покупки тура в Кредит с рабочей карты")
    void testFailedBuyingTourByCardWithUnfilledForm() {
        MainPage mainPage = new MainPage();
        CreditCheckoutPage creditCheckoutPage = mainPage.buyTourOnCreditCard();
        creditCheckoutPage.sendVoidCreditForm();
        creditCheckoutPage.cardNumberFieldMessageIsSubMessage(subMessageWrongDataFormat);
        creditCheckoutPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDataFormat);
        creditCheckoutPage.cardYearFieldMessageIsSubMessage(subMessageWrongDataFormat);
        creditCheckoutPage.cardOwnerFieldMessageIsSubMessage(subMessageEmptyData);
        creditCheckoutPage.cardCodeFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой и валидными полями, но пустым полем 'Номер карты'")
    void testFailedBuyingTourByCardWithoutFormNumberOfCard() {
        MainPage mainPage = new MainPage();
        CreditCheckoutPage creditCheckoutPage = mainPage.buyTourOnCreditCard();
        creditCheckoutPage.buyThroughCredit(DataHelper.formDataOfCardOwnerIsFalse());
        creditCheckoutPage.cardOwnerFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой, где поле 'год' невалидное, а остальные - валидные")
    void testFailedBuyingTourByCardWithInvalidFormYear() {
        MainPage mainPage = new MainPage();
        CreditCheckoutPage creditCheckoutPage = mainPage.buyTourOnCreditCard();
        creditCheckoutPage.buyThroughCredit(DataHelper.formDataOfCardYearsIsOverdue());
        creditCheckoutPage.cardYearFieldMessageIsSubMessage(subMessageExpiredDate);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой, где поле 'месяц' невалидное (из одной цифры), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormMonthOneFigure() {
        MainPage mainPage = new MainPage();
        CreditCheckoutPage creditCheckoutPage = mainPage.buyTourOnCreditCard();
        creditCheckoutPage.buyThroughCredit(DataHelper.oneSymbolFormDataNumberOfMonth());
        creditCheckoutPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой, где поле 'месяц' невалидное (из двух нулей), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormMonthZeroZero() {
        MainPage mainPage = new MainPage();
        CreditCheckoutPage creditCheckoutPage = mainPage.buyTourOnCreditCard();
        creditCheckoutPage.buyThroughCredit(DataHelper.approvedFormOfCardDataAndZeroZeroMonth ());
        creditCheckoutPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDataFormat); //todo: баг с двумя нулями. Можно купить
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой, где поле 'месяц' невалидное (больше 12), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormMonthThirteen() {
        MainPage mainPage = new MainPage();
        CreditCheckoutPage creditCheckoutPage = mainPage.buyTourOnCreditCard();
        creditCheckoutPage.buyThroughCredit(DataHelper.formDataOfCardMonthIsWrong ());
        creditCheckoutPage.cardMonthFieldMessageIsSubMessage(subMessageWrongDate);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой, где поле 'месяц' невалидное (прошедший  месяц), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormMonthPrevious() {
        MainPage mainPage = new MainPage();
        CreditCheckoutPage creditCheckoutPage = mainPage.buyTourOnCreditCard();
        creditCheckoutPage.buyThroughCredit(DataHelper.approvedFormOfCardDataAndEarlyMonth());
        creditCheckoutPage.completeNotice();
        assertEquals("APPROVED", DataHelper.getStatusOfBuingOnCreditTransaction());
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с рабочей картой, где поле 'год' невалидное (прошедший год), а остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormYearPrevious() {
        MainPage mainPage = new MainPage();
        CreditCheckoutPage creditCheckoutPage = mainPage.buyTourOnCreditCard();
        creditCheckoutPage.buyThroughCredit(DataHelper.approvedFormOfCardDataAndEarlyYear());
        creditCheckoutPage.cardYearFieldMessageIsSubMessage(subMessageExpiredDate);
    }

    @Test
    @DisplayName("Отправление формы покупки тура в Кредит с невалидным полем номера карты, но остальные поля - валидные")
    void testFailedBuyingTourByCardWithInvalidFormNumberOfCard() {
        MainPage mainPage = new MainPage();
        CreditCheckoutPage creditCheckoutPage = mainPage.buyTourOnCreditCard();
        creditCheckoutPage.buyThroughCredit(DataHelper.declinedCardFormData());
        creditCheckoutPage.failedNotice(); //todo: тест верный. Это баг
        assertEquals("DECLINED", DataHelper.getStatusOfBuingOnCreditTransaction());
    }
}
