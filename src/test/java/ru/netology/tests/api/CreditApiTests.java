package ru.netology.tests.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import ru.netology.helpers.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditApiTests extends BaseApiTest {

    @Test
    @DisplayName("Успешная оплата тура в кредит принимаемой кредитной картой с валидными данными формы")
    void testCardTourPurchaseApprovedCard() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.approvedCardFormData(), creditOperationPath,  Constants.HTTP_OK);

        assertEquals(Constants.APPROVED_STATUS, response.jsonPath().get("status"));
        assertEquals(Constants.APPROVED_STATUS, DataHelper.getCreditOperationStatus());

    }

    @Test
    @DisplayName("Отказ при попытке оплатить тур в кредит непринимаемой картой")
    void testCardTourPurchaseDeclinedCard() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.declinedCardFormData(), creditOperationPath, Constants.HTTP_OK);

        assertEquals(Constants.DECLINED_STATUS, response.jsonPath().get("status"));
        assertEquals(Constants.DECLINED_STATUS, DataHelper.getCreditOperationStatus());
    }

    @Test
    @DisplayName("Отказ при попытке оплатить тур в кредит с номером карты в 1 символ")
    void testCardTourPurchaseSendErrorForSingleCharacterCardNumber() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.oneCharacterCardNumberFormData(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
        assertEquals(Constants.DECLINED_STATUS, DataHelper.getCreditOperationStatus());
    }

    @Test
    @DisplayName("Отказ при попытке оплатить тур в кредит случайной картой с неверным двузначным форматом 'месяц'")
    void testCardTourPurchaseSendErrorForWrongFormatCardMonth() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.wrongCardMonthFormData(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить тур в кредит случайной картой с трехзначным форматом параметра 'год'")
    void testCardTourPurchaseSendErrorForWrongFormatCardYear() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.wrongCardYearFormData(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить тур в кредит  валидной картой с истекшим параметром 'год'")
    void testCardTourPurchaseSendErrorForExpiredCardYear() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.approvedCardFormDataWithApprovedPreviousYear(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить картой тур в кредит  с именем владельца из одного слова")
    void testCardTourPurchaseSendErrorForWrongFormatCardHolder() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.wrongCardHolderFormData(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить картой тур в кредит именем владельца карты на кириллице")
    void testCardTourPurchaseSendErrorForCyrillicCardHolder() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.cyrillicCardHolderFormData(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить картой тур в кредит  с именем владельца карты цифрами")
    void testCardTourPurchaseErrorForNumbersCardHolder() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.numbersCardHolderFormData(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить картой тур в кредит  с именем владельца карты в один символ")
    void testCardTourPurchaseErrorForOneCharacterCardHolder() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.oneCharacterCardHolderFormData(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить картой тур в кредит  с именем владельца карты с спец символом")
    void testCardTourPurchaseSpecSymbolsCardHolder() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.specSymbolsCardHolderFormData(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить картой тур в кредит с CVV карты в один символ")
    void testCardTourPurchaseErrorForOneCharacterCardCode() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.oneCharacterCardCodeFormData(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке тур в кредит картой с пустым номером карты")
    void testCardTourPurchaseErrorForEmptyCardNumber() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.emptyCardNumberFormData(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить тур в кредит случайной картой с пустым полем 'месяц'")
    void testCardTourPurchaseErrorForEmptyCardMonth() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.emptyUnknownCardMonthFormData(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить тур в кредит случайной картой с пустым полем 'год'")
    void testCardTourPurchaseErrorForEmptyCardYear() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.emptyUnknownCardYearFormData(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить тур в кредит случайной картой с пустым полем 'владелец'")
    void testCardTourPurchaseErrorForEmptyCardHolder() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.emptyCardHolderFormData(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить тур в кредит случайной картой с пустым полем 'CVV'")
    void testCardTourPurchaseErrorForEmptyCardCode() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.emptyCardCodeFormData(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить тур в кредит случайной картой c 00 в поле 'месяц'")
    void testCardTourPurchaseErrorForDoubleZeroMonth() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.doubleZeroMonthFormData(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить тур в кредит случайной картой c 00 в поле 'год'")
    void testCardTourPurchaseErrorForDoubleZeroYear() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.doubleZeroYearFormData(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }
}
