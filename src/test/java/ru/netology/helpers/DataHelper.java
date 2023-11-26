package ru.netology.helpers;

import com.github.javafaker.Faker;
import lombok.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;

import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private static final Faker faker = new Faker(new Locale("en"));
    private static String dbUrl = System.getProperty("db.url");

    @Value
    public static class FormData {
        String number;
        String month;
        String year;
        String holder;
        String cvc;
    }

    public static String getValidNumberOfCard() {
        return "4444 4444 4444 4441";
    }

    public static String getAntiValidNumberOfCard() {
        return "4444 4444 4444 4442";
    }

    public static String getUnknownNumberOfCard() {
        return faker.number().digits(16);
    }

    public static String getCardMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getPickCardMonth(int amountOfMonthsAdded) {
        return LocalDate.now().plusMonths(amountOfMonthsAdded).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getPickCardYear(int amountOfYearsAdded) {
        return LocalDate.now().plusYears(amountOfYearsAdded).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getCardHolder() {
        return faker.name().firstName().toUpperCase() + " " + faker.name().lastName().toUpperCase();
    }

    public static String getCardCode() {
        return faker.number().digits(3);
    }

    public static String getOnlyOneNumber(){
        return faker.number().digits(1);
    }

    public static String getFalseMonthOfCard() {
        return String.valueOf(faker.number().numberBetween(13,99));
    }

    public static String getZeroZeroData() {
        return "00";
    }

    public static String getFalseYearsOfCard() {
        return String.valueOf(faker.number().numberBetween(0,999));
    }

    public static String getOverdueYearsOfCard() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getFalseOwnerOfCard() {
        return faker.name().firstName().toUpperCase();
    }

    public static String getRusLanguageOwnerOfCard() {
        final Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName().toUpperCase() + ' ' + faker.name().lastName().toUpperCase() ;
    }

    public static String getNumbersCardsOfOwner() {
        return String.valueOf(faker.number().numberBetween(100,99999));
    }

    public static String getOneSymbol() {
        return faker.letterify("?").toUpperCase();
    }

    public static FormData approvedFormOfCardData() {
        return new FormData(getValidNumberOfCard(), getCardMonth(),
                getPickCardYear(1), getCardHolder(), getCardCode());
    }

    public static FormData approvedFarmOfCardDataAndNextMonth() {
        return new FormData(getValidNumberOfCard(), getPickCardMonth(1),
                getPickCardYear(1), getCardHolder(), getCardCode());
    }

    public static FormData approvedFarmOfCardDataAndNextYear() {
        return new FormData(getValidNumberOfCard(), getCardMonth(),
                getPickCardYear(1), getCardHolder(), getCardCode());
    }

    public static FormData approvedFormOfCardDataAndEarlyYear() {
        return new FormData(getValidNumberOfCard(), getCardMonth(),
                getPickCardYear(-1), getCardHolder(), getCardCode());
    }

    public static FormData approvedFormOfCardDataAndZeroZeroMonth() {
        return new FormData(getValidNumberOfCard(), getZeroZeroData(),
                getPickCardYear(1), getCardHolder(), getCardCode());
    }


    public static FormData approvedFormOfCardDataAndEarlyMonth() {
        return new FormData(getValidNumberOfCard(), getPickCardMonth(-1),
                getPickCardYear(1), getCardHolder(), getCardCode());
    }

    public static FormData declinedCardFormData() {
        return new FormData(getAntiValidNumberOfCard(), getCardMonth(),
                getPickCardYear(1), getCardHolder(), getCardCode());
    }

    public static FormData oneSymbolFormDataNumberOfCard() {
        return new FormData(getOnlyOneNumber(), getCardMonth(),
                getPickCardYear(1), getCardHolder(), getCardCode());
    }

    public static FormData oneSymbolFormDataNumberOfMonth() {
        return new FormData(getValidNumberOfCard(), getOnlyOneNumber(),
                getPickCardYear(1), getCardHolder(), getCardCode());
    }

    public static FormData formDataOfCardMonthIsWrong() {
        return new FormData(getUnknownNumberOfCard(), getFalseMonthOfCard(),
                getPickCardYear(1), getCardHolder(), getCardCode());
    }

    public static FormData formDataOfCardYearsIsWrong() {
        return new FormData(getUnknownNumberOfCard(), getCardMonth(),
                getFalseYearsOfCard(), getCardHolder(), getCardCode());
    }

    public static FormData formDataOfCardYearsIsOverdue() {
        return new FormData(getUnknownNumberOfCard(), getCardMonth(),
                getOverdueYearsOfCard(), getCardHolder(), getCardCode());
    }

    public static FormData formDataOfCardOwnerIsFalse() {
        return new FormData(getUnknownNumberOfCard(), getCardMonth(),
                getPickCardYear(1), getFalseOwnerOfCard(), getCardCode());
    }

    public static FormData formDataOfCardOwnerIsRusLanguage() {
        return new FormData(getUnknownNumberOfCard(), getCardMonth(),
                getPickCardYear(1), getRusLanguageOwnerOfCard(), getCardCode());
    }
    public static FormData formDataOfCardOwnerIsNumbers() {
        return new FormData(getUnknownNumberOfCard(), getCardMonth(),
                getPickCardYear(1), getNumbersCardsOfOwner(), getCardCode());
    }

    public static FormData formDataOfCardOwnerIsOnlyOneSymbol() {
        return new FormData(getUnknownNumberOfCard(), getCardMonth(),
                getPickCardYear(1),getOneSymbol(), getCardCode());
    }

    public static FormData formDataOfCardOwnerContainSpecialSymbol() {
        return new FormData(getUnknownNumberOfCard(), getCardMonth(),
                getPickCardYear(1),getCardHolder() + "!@#$%^&*№;%:?", getCardCode());
    }

    public static FormData formDataOfCardCodeIsOnlyOneSymbol() {
        return new FormData(getUnknownNumberOfCard(), getCardMonth(),
                getPickCardYear(1),getCardHolder(), getOnlyOneNumber());
    }

    public static FormData formDataOfCardNumberIsVoid() {
        return new FormData("", getCardMonth(),
                getPickCardYear(1),getCardHolder(), getCardCode());
    }

    public static FormData formDataOfCardMonthIsVoid() {
        return new FormData(getUnknownNumberOfCard(), "",
                getPickCardYear(1),getCardHolder(), getCardCode());
    }

    public static FormData formDataOfCardMonthIsValidVoid() {
        return new FormData(getValidNumberOfCard(), "",
                getPickCardYear(1),getCardHolder(), getCardCode());
    }

    public static FormData formDataOfCardYearIsVoid() {
        return new FormData(getUnknownNumberOfCard(), getCardMonth(),
                "",getCardHolder(), getCardCode());
    }

    public static FormData formDataOfCardYearIsValidVoid() {
        return new FormData(getValidNumberOfCard(), getCardMonth(),
                "",getCardHolder(), getCardCode());
    }

    public static FormData formDataOfCardOwnerIsVoid() {
        return new FormData(getValidNumberOfCard(), getCardMonth(),
                getPickCardYear(1),"", getCardCode());
    }

    public static FormData formDataOfCardCodeIsVoid() {
        return new FormData(getValidNumberOfCard(), getCardMonth(),
                getPickCardYear(1),getCardHolder(), "");
    }

    public static FormData formDataOfCardMonthISZeroZero() {
        return new FormData(getUnknownNumberOfCard(), getZeroZeroData(),
                getPickCardYear(1),getCardHolder(), getCardCode());
    }

    public static FormData formDataOfCardYearISZeroZero() {
        return new FormData(getUnknownNumberOfCard(), getCardMonth(),
                getZeroZeroData(),getCardHolder(), getCardCode());
    }

    @SneakyThrows
    public static String getStatusOfBuingTransaction() {
        var runner = new QueryRunner();
        var getStatus = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        var conn = DriverManager.getConnection(dbUrl, "app", "pass");

        return runner.query(conn, getStatus, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getStatusOfBuingOnCreditTransaction() {
        var runner = new QueryRunner();
        var getStatus = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        var conn = DriverManager.getConnection(dbUrl, "app", "pass");

        return runner.query(conn, getStatus, new ScalarHandler<>());
    }

    @SneakyThrows
    public static void cleanData() {
        var runner = new QueryRunner();
        var deletePaymentEntitySQL = "DELETE FROM payment_entity";
        var deleteOrderEntitySQL = "DELETE FROM order_entity";
        var deleteRequestEntitySQL = "DELETE FROM credit_request_entity";

        var conn = DriverManager.getConnection(dbUrl, "app", "pass");

        runner.update(conn, deletePaymentEntitySQL);
        runner.update(conn, deleteOrderEntitySQL);
        runner.update(conn, deleteRequestEntitySQL);
    }
}