package ru.netology.tests.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import ru.netology.helpers.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardCreditApiTests extends BaseApiTest {

    @Test
    @DisplayName("Оплата тура рабочей картой в Кредит с валидными вводными данными - Выполняется")
    void testBuyingTourOnCardApproved() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.approvedFormOfCardData(), creditOperationPath,  initialData.HTTP_OK);

        assertEquals(initialData.APPROVED_STATUS, response.jsonPath().get("status"));
        assertEquals(initialData.APPROVED_STATUS, DataHelper.getStatusOfBuingOnCreditTransaction());

    }

    @Test
    @DisplayName("Оплата тура в  Кредит нерабочей картой - Не выполняется")
    void testBuyingTourOnDeclinedCard() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.declinedCardFormData(), creditOperationPath, initialData.HTTP_OK);

        assertEquals(initialData.DECLINED_STATUS, response.jsonPath().get("status"));
        assertEquals(initialData.DECLINED_STATUS, DataHelper.getStatusOfBuingOnCreditTransaction());
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с номером в один символ - Не выполняется")
    void testBuyingTourByCardWithOnlyOneSymbolInNumber() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.oneSymbolFormDataNumberOfCard(), creditOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
        assertEquals(initialData.DECLINED_STATUS, DataHelper.getStatusOfBuingOnCreditTransaction());
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с форматом 'месяц' больше 12 - Не выполняется")
    void testBuyingTourByCardWithUnExistantMonth() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardMonthIsWrong (), creditOperationPath, initialData.INTERNAL_ERROR);
        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с форматом 'год' из  трех цифр - Не выполняется")
    void testBuyingTourByCardWithOnlyThreeFigureOnYear() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardYearsIsWrong (), creditOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит рабочей картой с форматом просроченным полем 'год' - Не выполняется")
    void testBuyingTourByCardWithOverdueFormYear() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.approvedFormOfCardDataAndEarlyYear(), creditOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с полем именем владельца из одного слова - Не выполняется")
    void testBuyingTourByCardWithOnlyOneWordOnNameOwner() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardOwnerIsFalse(), creditOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с полем именем владельца на русском языке - Не выполняется")
    void testBuyingTourByCardWithRussianSymbolsInNameOwner() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardOwnerIsRusLanguage(), creditOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с полем именем владельца из цифр - Не выполняется")
    void testBuyingTourByCardWithFigureSymbolsInNameOwner() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardOwnerIsNumbers(), creditOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с полем именем владельца из одного символа - Не выполняется")
    void testBuyingTourByCardWithOnlyOneSymbolsInNameOwner() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardOwnerIsOnlyOneSymbol(), creditOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с полем именем владельца из специального символа - Не выполняется")
    void testBuyingTourByCardWithSpecialSymbolsInNameOwner() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardOwnerContainSpecialSymbol(), creditOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с полем СVV в олин символ - Не выполняется")
    void testBuyingTourByCardWithCVVOnlyOneSymbol() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardCodeIsOnlyOneSymbol(), creditOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой без номера карты (без цифр) - Не выполняется")
    void testBuyingTourByCardWithoutNumberOfCard() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardNumberIsVoid(), creditOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с пустым полем месяц - Не выполняется")
    void testBuyingTourByCardWithoutMonthForm() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardMonthIsVoid(), creditOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с пустым полем год - Не выполняется")
    void testBuyingTourByCardWithoutYearForm() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardYearIsVoid(), creditOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с пустым полем владельца - Не выполняется")
    void testBuyingTourByCardWithoutOwnerForm() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardOwnerIsVoid(), creditOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с пустым полем СVV - Не выполняется")
    void testBuyingTourByCardWithoutCVVForm() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardCodeIsVoid(), creditOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с полем месяц из двух нулей - Не выполняется")
    void testBuyingTourByCardWithZeroZeroInMonth() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardMonthISZeroZero(), creditOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура в Кредит картой с полем год из двух нулей - Не выполняется")
    void testBuyingTourByCardWithZeroZeroInYear() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardYearISZeroZero(), creditOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }
}
