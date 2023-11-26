package ru.netology.tests.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import ru.netology.helpers.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditApiTests extends BaseApiTest {

    @Test
    @DisplayName("Оплата тура рабочей картой в Кредит с валидными вводными данными - Выполняется")
    void testBuyingTourOnCardApproved() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.approvedFormOfCardData(), creditOperationPath,  Constants.HTTP_OK);

        assertEquals(Constants.APPROVED_STATUS, response.jsonPath().get("status"));
        assertEquals(Constants.APPROVED_STATUS, DataHelper.getStatusOfBuingOnCreditTransaction());

    }

    @Test
    @DisplayName("Оплата тура в  Кредит нерабочей картой - Не выполняется")
    void testBuyingTourOnDeclinedCard() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.declinedCardFormData(), creditOperationPath, Constants.HTTP_OK);

        assertEquals(Constants.DECLINED_STATUS, response.jsonPath().get("status"));
        assertEquals(Constants.DECLINED_STATUS, DataHelper.getStatusOfBuingOnCreditTransaction());
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с номером в один символ - Не выполняется")
    void testBuyingTourByCardWithOnlyOneSymbolInNumber() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.oneSymbolFormDataNumberOfCard(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
        assertEquals(Constants.DECLINED_STATUS, DataHelper.getStatusOfBuingOnCreditTransaction());
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с форматом 'месяц' больше 12 - Не выполняется")
    void testBuyingTourByCardWithUnExistantMonth() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardMonthIsWrong (), creditOperationPath, Constants.INTERNAL_ERROR);
        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с форматом 'год' из  трех цифр - Не выполняется")
    void testBuyingTourByCardWithOnlyThreeFigureOnYear() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardYearsIsWrong (), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит рабочей картой с форматом просроченным полем 'год' - Не выполняется")
    void testBuyingTourByCardWithOverdueFormYear() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.approvedFormOfCardDataAndEarlyYear(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с полем именем владельца из одного слова - Не выполняется")
    void testBuyingTourByCardWithOnlyOneWordOnNameOwner() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardOwnerIsFalse(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с полем именем владельца на русском языке - Не выполняется")
    void testBuyingTourByCardWithRussianSymbolsInNameOwner() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardOwnerIsRusLanguage(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с полем именем владельца из цифр - Не выполняется")
    void testBuyingTourByCardWithFigureSymbolsInNameOwner() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardOwnerIsNumbers(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с полем именем владельца из одного символа - Не выполняется")
    void testBuyingTourByCardWithOnlyOneSymbolsInNameOwner() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardOwnerIsOnlyOneSymbol(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с полем именем владельца из специального символа - Не выполняется")
    void testBuyingTourByCardWithSpecialSymbolsInNameOwner() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardOwnerContainSpecialSymbol(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с полем СVV в олин символ - Не выполняется")
    void testBuyingTourByCardWithCVVOnlyOneSymbol() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardCodeIsOnlyOneSymbol(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой без номера карты (без цифр) - Не выполняется")
    void testBuyingTourByCardWithoutNumberOfCard() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardNumberIsVoid(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с пустым полем месяц - Не выполняется")
    void testBuyingTourByCardWithoutMonthForm() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardMonthIsVoid(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с пустым полем год - Не выполняется")
    void testBuyingTourByCardWithoutYearForm() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardYearIsVoid(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с пустым полем владельца - Не выполняется")
    void testBuyingTourByCardWithoutOwnerForm() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardOwnerIsVoid(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с пустым полем СVV - Не выполняется")
    void testBuyingTourByCardWithoutCVVForm() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardCodeIsVoid(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с полем месяц из двух нулей - Не выполняется")
    void testBuyingTourByCardWithZeroZeroInMonth() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardMonthISZeroZero(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с полем год из двух нулей - Не выполняется")
    void testBuyingTourByCardWithZeroZeroInYear() {
        Response response = RestRequestHelper.sendRequest(
                DataHelper.formDataOfCardYearISZeroZero(), creditOperationPath, Constants.INTERNAL_ERROR);

        assertEquals(Constants.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }
}
