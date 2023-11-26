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
    @DisplayName("Оплата тура рабочей картой с валидными вводными данными - Выполняется")
    void testBuyingTourOnCardApproved() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.approvedFormOfCardData(), buyOperationPath, Constants.HTTP_OK);

        assertEquals(Constants.APPROVED_STATUS, response.jsonPath().get("status"));
        assertEquals(Constants.APPROVED_STATUS, DataHelper.getStatusOfBuingTransaction());
    }

    @Test
    @DisplayName("Оплата тура нерабочей картой - Не выполняется")
    void testBuyingTourOnDeclinedCard() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.declinedCardFormData(), buyOperationPath, Constants.HTTP_OK);

        assertEquals(Constants.DECLINED_STATUS, response.jsonPath().get("status"));
        assertEquals(Constants.DECLINED_STATUS, DataHelper.getStatusOfBuingTransaction());
    }

    @Test
    @DisplayName("Оплата тура картой с номером в один символ - Не выполняется")
    void testBuyingTourByCardWithOnlyOneSymbolInNumber() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.oneSymbolFormDataNumberOfCard(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
        assertEquals(Constants.DECLINED_STATUS, DataHelper.getStatusOfBuingTransaction());
    }

    @Test
    @DisplayName("Оплата тура картой с форматом 'месяц' больше 12 - Не выполняется")
    void testBuyingTourByCardWithUnExistantMonth() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardMonthIsWrong (), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с форматом 'год' из  трех цифр - Не выполняется")
    void testBuyingTourByCardWithOnlyThreeFigureOnYear() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardYearsIsWrong (), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура рабочей картой с форматом просроченным полем 'год' - Не выполняется")
    void testBuyingTourByCardWithOverdueFormYear() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.approvedFormOfCardDataAndEarlyYear(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с полем именем владельца из одного слова - Не выполняется")
    void testBuyingTourByCardWithOnlyOneWordOnNameOwner() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardOwnerIsFalse(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с полем именем владельца на русском языке - Не выполняется")
    void testBuyingTourByCardWithRussianSymbolsInNameOwner() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardOwnerIsRusLanguage(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с полем именем владельца из цифр - Не выполняется")
    void testBuyingTourByCardWithFigureSymbolsInNameOwner() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardOwnerIsNumbers(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с полем именем владельца из одного символа - Не выполняется")
    void testBuyingTourByCardWithOnlyOneSymbolsInNameOwner() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardOwnerIsOnlyOneSymbol(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с полем имени владельца из специального символа - Не выполняется")
    void testBuyingTourByCardWithSpecialSymbolsInNameOwner() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardOwnerContainSpecialSymbol(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с полем СVV в олин символ - Не выполняется")
    void testBuyingTourByCardWithCVVOnlyOneSymbol() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardCodeIsOnlyOneSymbol(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой без номера карты (без цифр) - Не выполняется")
    void testBuyingTourByCardWithoutNumberOfCard() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardNumberIsVoid(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с пустым полем месяц - Не выполняется")
    void testBuyingTourByCardWithoutMonthForm() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardMonthIsVoid(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с пустым полем год - Не выполняется")
    void testBuyingTourByCardWithoutYearForm() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardYearIsVoid(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с пустым полем владельца - Не выполняется")
    void testBuyingTourByCardWithoutOwnerForm() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardOwnerIsVoid(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с пустым полем СVV - Не выполняется")
    void testBuyingTourByCardWithoutCVVForm() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardCodeIsVoid(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с полем месяц из двух нулей - Не выполняется")
    void testBuyingTourByCardWithZeroZeroInMonth() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardMonthISZeroZero(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с полем год из двух нулей - Не выполняется")
    void testBuyingTourByCardWithZeroZeroInYear() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardYearISZeroZero(), buyOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }
}
