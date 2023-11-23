package ru.netology.tests.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.helpers.Constants;
import ru.netology.helpers.DataHelper;
import ru.netology.helpers.RestRequestHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardApiTests extends BaseApiTest {

    @Test
    @DisplayName("Успешная оплата тура принимаемой картой с валидными данными формы")
    void testCardTourPurchaseApprovedCard() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.approvedCardFormData(), buyOperationPath, Constants.HTTP_OK);

        assertEquals(Constants.APPROVED_STATUS, response.jsonPath().get("status"));
        assertEquals(Constants.APPROVED_STATUS, DataHelper.getBuyingOperationStatus());
    }

    @Test
    @DisplayName("Отказ при попытке оплатить тур непринимаемой картой")
    void testCardTourPurchaseDeclinedCard() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.declinedCardFormData(), buyOperationPath, Constants.HTTP_OK);

        assertEquals(Constants.DECLINED_STATUS, response.jsonPath().get("status"));
        assertEquals(Constants.DECLINED_STATUS, DataHelper.getBuyingOperationStatus());
    }

    @Test
    @DisplayName("Отказ при попытке оплатить тур с номером карты в 1 символ")
    void testCardTourPurchaseSendErrorForSingleCharacterCardNumber() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.oneCharacterCardNumberFormData(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
        assertEquals(Constants.DECLINED_STATUS, DataHelper.getBuyingOperationStatus());
    }

    @Test
    @DisplayName("Отказ при попытке оплатить тур случайной картой с неверным двузначным форматом 'месяц'")
    void testCardTourPurchaseSendErrorForWrongFormatCardMonth() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.wrongCardMonthFormData(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить тур случайной картой с трехзначным форматом параметра 'год'")
    void testCardTourPurchaseSendErrorForWrongFormatCardYear() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.wrongCardYearFormData(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить тур валидной картой с истекшим параметром 'год'")
    void testCardTourPurchaseSendErrorForExpiredCardYear() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.approvedCardFormDataWithApprovedPreviousYear(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить картой тур с именем владельца из одного слова")
    void testCardTourPurchaseSendErrorForWrongFormatCardHolder() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.wrongCardHolderFormData(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить картой тур именем владельца карты на кириллице")
    void testCardTourPurchaseSendErrorForCyrillicCardHolder() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.cyrillicCardHolderFormData(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить картой тур с именем владельца карты цифрами")
    void testCardTourPurchaseErrorForNumbersCardHolder() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.numbersCardHolderFormData(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить картой тур с именем владельца карты в один символ")
    void testCardTourPurchaseErrorForOneCharacterCardHolder() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.oneCharacterCardHolderFormData(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить картой тур с именем владельца карты с спец символом")
    void testCardTourPurchaseSpecSymbolsCardHolder() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.specSymbolsCardHolderFormData(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить картой тур с CVV карты в один символ")
    void testCardTourPurchaseErrorForOneCharacterCardCode() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.oneCharacterCardCodeFormData(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке картой с пустым номером карты")
    void testCardTourPurchaseErrorForEmptyCardNumber() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.emptyCardNumberFormData(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить случайной картой с пустым полем 'месяц'")
    void testCardTourPurchaseErrorForEmptyCardMonth() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.emptyUnknownCardMonthFormData(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить случайной картой с пустым полем 'год'")
    void testCardTourPurchaseErrorForEmptyCardYear() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.emptyUnknownCardYearFormData(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить случайной картой с пустым полем 'владелец'")
    void testCardTourPurchaseErrorForEmptyCardHolder() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.emptyCardHolderFormData(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить случайной картой с пустым полем 'CVV'")
    void testCardTourPurchaseErrorForEmptyCardCode() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.emptyCardCodeFormData(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить случайной картой c 00 в поле 'месяц'")
    void testCardTourPurchaseErrorForDoubleZeroMonth() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.doubleZeroMonthFormData(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Отказ при попытке оплатить случайной картой c 00 в поле 'год'")
    void testCardTourPurchaseErrorForDoubleZeroYear() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.doubleZeroYearFormData(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }
}
