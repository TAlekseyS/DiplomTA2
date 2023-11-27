package ru.netology.tests.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.helpers.initialData;
import ru.netology.helpers.DataHelper;
import ru.netology.helpers.RepeatRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDebetApiTests extends BaseApiTest {

    @Test
    @DisplayName("Оплата тура рабочей картой с валидными вводными данными - Выполняется")
    void testBuyingTourOnCardApproved() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.approvedFormOfCardData(), buyOperationPath, initialData.HTTP_OK);

        assertEquals(initialData.APPROVED_STATUS, response.jsonPath().get("status"));
        assertEquals(initialData.APPROVED_STATUS, DataHelper.getStatusOfBuingTransaction());
    }

    @Test
    @DisplayName("Оплата тура нерабочей картой - Не выполняется")
    void testBuyingTourOnDeclinedCard() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.declinedCardFormData(), buyOperationPath, initialData.HTTP_OK);

        assertEquals(initialData.DECLINED_STATUS, response.jsonPath().get("status"));
        assertEquals(initialData.DECLINED_STATUS, DataHelper.getStatusOfBuingTransaction());
    }

    @Test
    @DisplayName("Оплата тура картой с номером в один символ - Не выполняется")
    void testBuyingTourByCardWithOnlyOneSymbolInNumber() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.oneSymbolFormDataNumberOfCard(), buyOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
        assertEquals(initialData.DECLINED_STATUS, DataHelper.getStatusOfBuingTransaction());
    }

    @Test
    @DisplayName("Оплата тура картой с форматом 'месяц' больше 12 - Не выполняется")
    void testBuyingTourByCardWithUnExistantMonth() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardMonthIsWrong (), buyOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с форматом 'год' из  трех цифр - Не выполняется")
    void testBuyingTourByCardWithOnlyThreeFigureOnYear() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardYearsIsWrong (), buyOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура рабочей картой с форматом просроченным полем 'год' - Не выполняется")
    void testBuyingTourByCardWithOverdueFormYear() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.approvedFormOfCardDataAndEarlyYear(), buyOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с полем именем владельца из одного слова - Не выполняется")
    void testBuyingTourByCardWithOnlyOneWordOnNameOwner() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardOwnerIsFalse(), buyOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с полем именем владельца на русском языке - Не выполняется")
    void testBuyingTourByCardWithRussianSymbolsInNameOwner() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardOwnerIsRusLanguage(), buyOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с полем именем владельца из цифр - Не выполняется")
    void testBuyingTourByCardWithFigureSymbolsInNameOwner() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardOwnerIsNumbers(), buyOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с полем именем владельца из одного символа - Не выполняется")
    void testBuyingTourByCardWithOnlyOneSymbolsInNameOwner() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardOwnerIsOnlyOneSymbol(), buyOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с полем имени владельца из специального символа - Не выполняется")
    void testBuyingTourByCardWithSpecialSymbolsInNameOwner() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardOwnerContainSpecialSymbol(), buyOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с полем СVV в олин символ - Не выполняется")
    void testBuyingTourByCardWithCVVOnlyOneSymbol() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardCodeIsOnlyOneSymbol(), buyOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой без номера карты (без цифр) - Не выполняется")
    void testBuyingTourByCardWithoutNumberOfCard() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardNumberIsVoid(), buyOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с пустым полем месяц - Не выполняется")
    void testBuyingTourByCardWithoutMonthForm() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardMonthIsVoid(), buyOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с пустым полем год - Не выполняется")
    void testBuyingTourByCardWithoutYearForm() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardYearIsVoid(), buyOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с пустым полем владельца - Не выполняется")
    void testBuyingTourByCardWithoutOwnerForm() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardOwnerIsVoid(), buyOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с пустым полем СVV - Не выполняется")
    void testBuyingTourByCardWithoutCVVForm() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardCodeIsVoid(), buyOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с полем месяц из двух нулей - Не выполняется")
    void testBuyingTourByCardWithZeroZeroInMonth() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardMonthISZeroZero(), buyOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }

    @Test
    @DisplayName("Оплата тура картой с полем год из двух нулей - Не выполняется")
    void testBuyingTourByCardWithZeroZeroInYear() {
        Response response = RepeatRequest.sendRequest(
                DataHelper.formDataOfCardYearISZeroZero(), buyOperationPath, initialData.INTERNAL_ERROR);

        assertEquals(initialData.BAD_REQUEST_MESSAGE, response.jsonPath().get("message"));
    }
}
