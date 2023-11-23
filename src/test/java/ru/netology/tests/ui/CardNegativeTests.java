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
    @DisplayName("Отправка формы покупки тура с пустым полем номера карты и валидными данными")
    void testFailedCardPurchaseTourEmptyCardNumber() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourViaCard();
        cardCheckoutPage.buyThroughCard(DataHelper.emptyCardNumberFormData());
        cardCheckoutPage.cardNumberFieldSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправка формы покупки тура с принимаемой картой и пустым полем “месяц” " +
            " и оставшимися валидными данными для полей")
    void testFailedValidCardPurchaseTourValidCardEmptyMonth() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourViaCard();
        cardCheckoutPage.buyThroughCard(DataHelper.emptyCardMonthFormData());
        cardCheckoutPage.cardMonthFieldSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправка формы покупки тура с принимаемой картой и пустым полем “год”" +
            " и оставшимися валидными данными для полей")
    void testFailedValidCardPurchaseTourValidCardEmptyYear() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourViaCard();
        cardCheckoutPage.buyThroughCard(DataHelper.emptyCardYearFormData());
        cardCheckoutPage.cardYearFieldSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправка формы покупки тура с принимаемой картой и пустым полем “Владелец”" +
            " и оставшимися валидными данными для полей")
    void testFailedValidCardPurchaseTourValidCardEmptyHolder() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourViaCard();
        cardCheckoutPage.buyThroughCard(DataHelper.emptyCardHolderFormData());
        cardCheckoutPage.cardHolderFieldSubMessage(subMessageEmptyData);
    }

    @Test
    @DisplayName("Отправка формы покупки тура с принимаемой картой и пустым полем “CVV/CVS”" +
            " и оставшимися валидными данными для полей")
    void testFailedValidCardPurchaseTourValidCardEmptyCVV() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourViaCard();
        cardCheckoutPage.buyThroughCard(DataHelper.emptyCardCodeFormData());
        cardCheckoutPage.cardCodeFieldSubMessage(subMessageWrongDataFormat); //todo: тут баг, подсвечивается поле с кардхолдером, как пустое
    }

    @Test
    @DisplayName("Отправка пустой формы покупки тура с принимаемой картой")
    void testFailedSendEmptyCardDataFields() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourViaCard();
        cardCheckoutPage.sendEmptyCardForm();
        cardCheckoutPage.cardNumberFieldSubMessage(subMessageWrongDataFormat);
        cardCheckoutPage.cardMonthFieldSubMessage(subMessageWrongDataFormat);
        cardCheckoutPage.cardYearFieldSubMessage(subMessageWrongDataFormat);
        cardCheckoutPage.cardHolderFieldSubMessage(subMessageEmptyData);
        cardCheckoutPage.cardCodeFieldSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправка формы покупки тура со случайными числами в поле “номер карты”" +
            " и оставшимися валидными данными для полей")
    void testFailedCardPurchaseViaUnknownCard() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourViaCard();
        cardCheckoutPage.buyThroughCard(DataHelper.wrongCardHolderFormData());
        cardCheckoutPage.cardHolderFieldSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправка формы покупки тура с вводом в поле “год” невалидного значения " +
            "равного предшествующему году от текущего года и оставшимися валидными данными для полей")
    void testFailedCardPurchaseInvalidExpireDate() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourViaCard();
        cardCheckoutPage.buyThroughCard(DataHelper.expiredCardYearFormData());
        cardCheckoutPage.cardYearFieldSubMessage(subMessageExpiredDate);
    }

    @Test
    @DisplayName("Отправка формы покупки тура с вводом в поле “месяц”" +
            " невалидных значений месяцев в один символ и оставшимися валидными данными для полей")
    void testFailedCardPurchaseOneCharMonthDataFormat() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourViaCard();
        cardCheckoutPage.buyThroughCard(DataHelper.oneCharacterMonthNumberFormData());
        cardCheckoutPage.cardMonthFieldSubMessage(subMessageWrongDataFormat);
    }

    @Test
    @DisplayName("Отправка формы покупки тура с вводом в поле “месяц” невалидного" +
            "значения из двух нулей и оставшимися валидными данными для полей")
    void testFailedCardPurchaseInvalidMonthDoubleZeroDataFormat() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourViaCard();
        cardCheckoutPage.buyThroughCard(DataHelper.approvedCardFormDataWithDoubleZeroMonth());
        cardCheckoutPage.cardMonthFieldSubMessage(subMessageWrongDataFormat); //todo: тут баг, с двумя 00 удается купить
    }

    @Test
    @DisplayName("Отправка формы покупки тура с вводом в поле “месяц”" +
            "несуществующего значения месяца и оставшимися валидными данными для полей")
    void testFailedCardPurchaseMonthDataFormat() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourViaCard();
        cardCheckoutPage.buyThroughCard(DataHelper.wrongCardMonthFormData());
        cardCheckoutPage.cardMonthFieldSubMessage(subMessageWrongDate);
    }

    @Test
    @DisplayName("Отправка формы покупки тура с принимаемой картой" +
            "на текущий месяц (currentMonth - 1) и текущий год (currentYear) и валидными данными для оставшихся полей")
    void testSuccessfulCardPurchaseTourPreviousMonthCurrentYear() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourViaCard();
        cardCheckoutPage.buyThroughCard(DataHelper.approvedCardFormDataWithPreviousMonth());
        cardCheckoutPage.successNotification();
        assertEquals("APPROVED", DataHelper.getBuyingOperationStatus());
    }

    @Test
    @DisplayName("Отправка формы покупки тура с принимаемой картой" +
            "на текущий месяц (currentMonth) и текущий год (currentYear - 1) и валидными данными для оставшихся полей")
    void testFailedCardPurchaseTourCurrentMonthPreviousYear() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourViaCard();
        cardCheckoutPage.buyThroughCard(DataHelper.approvedCardFormDataWithApprovedPreviousYear());
        cardCheckoutPage.cardYearFieldSubMessage(subMessageExpiredDate);
    }

    @Test
    @DisplayName("Отправка формы покупки тура с отклоненным номером карты и валидными данными формы")
    void testFailedCardPurchaseTourDeclinedCardValidFormData() {
        MainPage mainPage = new MainPage();
        CardCheckoutPage cardCheckoutPage = mainPage.buyTourViaCard();
        cardCheckoutPage.buyThroughCard(DataHelper.declinedCardFormData());
        cardCheckoutPage.errorNotification(); //todo: тест логически правильный, тут баг
        assertEquals("DECLINED", DataHelper.getBuyingOperationStatus());
    }
}