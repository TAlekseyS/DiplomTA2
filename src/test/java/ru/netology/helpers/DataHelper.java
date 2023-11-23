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

    public static String getApprovedCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getUnknownCardNumber() {
        return faker.number().digits(16);
    }

    public static String getCardMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getCustomAddedChangedCardMonth(int amountOfMonthsAdded) {
        return LocalDate.now().plusMonths(amountOfMonthsAdded).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getCustomAddedCardYear(int amountOfYearsAdded) {
        return LocalDate.now().plusYears(amountOfYearsAdded).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getCardHolder() {
        return faker.name().firstName().toUpperCase() + " " + faker.name().lastName().toUpperCase();
    }

    public static String getCardCode() {
        return faker.number().digits(3);
    }

    public static String getOneNumber(){
        return faker.number().digits(1);
    }

    public static String getWrongTwoDigitCardMonth() {
        return String.valueOf(faker.number().numberBetween(13,99));
    }

    public static String getDoubleZeroData() {
        return "00";
    }

    public static String getWrongCardYear() {
        return String.valueOf(faker.number().numberBetween(0,999));
    }

    public static String getCardYearExpired() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getWrongCardHolder() {
        return faker.name().firstName().toUpperCase();
    }

    public static String getCyrillicCardHolder() {
        final Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName().toUpperCase() + ' ' + faker.name().lastName().toUpperCase() ;
    }

    public static String getNumbersCardHolder() {
        return String.valueOf(faker.number().numberBetween(100,99999));
    }

    public static String getOneCharacter() {
        return faker.letterify("?").toUpperCase();
    }

    public static FormData approvedCardFormData() {
        return new FormData(getApprovedCardNumber(), getCardMonth(),
                getCustomAddedCardYear(1), getCardHolder(), getCardCode());
    }

    public static FormData approvedCardFormDataWithApprovedNextMonth() {
        return new FormData(getApprovedCardNumber(), getCustomAddedChangedCardMonth(1),
                getCustomAddedCardYear(1), getCardHolder(), getCardCode());
    }

    public static FormData approvedCardFormDataWithApprovedNextYear() {
        return new FormData(getApprovedCardNumber(), getCardMonth(),
                getCustomAddedCardYear(1), getCardHolder(), getCardCode());
    }

    public static FormData approvedCardFormDataWithApprovedPreviousYear() {
        return new FormData(getApprovedCardNumber(), getCardMonth(),
                getCustomAddedCardYear(-1), getCardHolder(), getCardCode());
    }

    public static FormData approvedCardFormDataWithDoubleZeroMonth() {
        return new FormData(getApprovedCardNumber(), getDoubleZeroData(),
                getCustomAddedCardYear(1), getCardHolder(), getCardCode());
    }


    public static FormData approvedCardFormDataWithPreviousMonth() {
        return new FormData(getApprovedCardNumber(), getCustomAddedChangedCardMonth(-1),
                getCustomAddedCardYear(1), getCardHolder(), getCardCode());
    }

    public static FormData declinedCardFormData() {
        return new FormData(getDeclinedCardNumber(), getCardMonth(),
                getCustomAddedCardYear(1), getCardHolder(), getCardCode());
    }

    public static FormData oneCharacterCardNumberFormData() {
        return new FormData(getOneNumber(), getCardMonth(),
                getCustomAddedCardYear(1), getCardHolder(), getCardCode());
    }

    public static FormData oneCharacterMonthNumberFormData() {
        return new FormData(getApprovedCardNumber(), getOneNumber(),
                getCustomAddedCardYear(1), getCardHolder(), getCardCode());
    }

    public static FormData wrongCardMonthFormData() {
        return new FormData(getUnknownCardNumber(), getWrongTwoDigitCardMonth(),
                getCustomAddedCardYear(1), getCardHolder(), getCardCode());
    }

    public static FormData wrongCardYearFormData() {
        return new FormData(getUnknownCardNumber(), getCardMonth(),
                getWrongCardYear(), getCardHolder(), getCardCode());
    }

    public static FormData expiredCardYearFormData() {
        return new FormData(getUnknownCardNumber(), getCardMonth(),
                getCardYearExpired(), getCardHolder(), getCardCode());
    }

    public static FormData wrongCardHolderFormData() {
        return new FormData(getUnknownCardNumber(), getCardMonth(),
                getCustomAddedCardYear(1), getWrongCardHolder(), getCardCode());
    }

    public static FormData cyrillicCardHolderFormData() {
        return new FormData(getUnknownCardNumber(), getCardMonth(),
                getCustomAddedCardYear(1), getCyrillicCardHolder(), getCardCode());
    }
    public static FormData numbersCardHolderFormData() {
        return new FormData(getUnknownCardNumber(), getCardMonth(),
                getCustomAddedCardYear(1), getNumbersCardHolder(), getCardCode());
    }

    public static FormData oneCharacterCardHolderFormData() {
        return new FormData(getUnknownCardNumber(), getCardMonth(),
                getCustomAddedCardYear(1),getOneCharacter(), getCardCode());
    }

    public static FormData specSymbolsCardHolderFormData() {
        return new FormData(getUnknownCardNumber(), getCardMonth(),
                getCustomAddedCardYear(1),getCardHolder() + "!@#$%^&*№;%:?", getCardCode());
    }

    public static FormData oneCharacterCardCodeFormData() {
        return new FormData(getUnknownCardNumber(), getCardMonth(),
                getCustomAddedCardYear(1),getCardHolder(), getOneNumber());
    }

    public static FormData emptyCardNumberFormData() {
        return new FormData("", getCardMonth(),
                getCustomAddedCardYear(1),getCardHolder(), getCardCode());
    }

    public static FormData emptyUnknownCardMonthFormData() {
        return new FormData(getUnknownCardNumber(), "",
                getCustomAddedCardYear(1),getCardHolder(), getCardCode());
    }

    public static FormData emptyCardMonthFormData() {
        return new FormData(getApprovedCardNumber(), "",
                getCustomAddedCardYear(1),getCardHolder(), getCardCode());
    }

    public static FormData emptyUnknownCardYearFormData() {
        return new FormData(getUnknownCardNumber(), getCardMonth(),
                "",getCardHolder(), getCardCode());
    }

    public static FormData emptyCardYearFormData() {
        return new FormData(getApprovedCardNumber(), getCardMonth(),
                "",getCardHolder(), getCardCode());
    }

    public static FormData emptyCardHolderFormData() {
        return new FormData(getApprovedCardNumber(), getCardMonth(),
                getCustomAddedCardYear(1),"", getCardCode());
    }

    public static FormData emptyCardCodeFormData() {
        return new FormData(getApprovedCardNumber(), getCardMonth(),
                getCustomAddedCardYear(1),getCardHolder(), "");
    }

    public static FormData doubleZeroMonthFormData() {
        return new FormData(getUnknownCardNumber(), getDoubleZeroData(),
                getCustomAddedCardYear(1),getCardHolder(), getCardCode());
    }

    public static FormData doubleZeroYearFormData() {
        return new FormData(getUnknownCardNumber(), getCardMonth(),
                getDoubleZeroData(),getCardHolder(), getCardCode());
    }

    @SneakyThrows
    public static String getBuyingOperationStatus() {
        var runner = new QueryRunner();
        var getStatus = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        var conn = DriverManager.getConnection(dbUrl, "app", "pass");

        return runner.query(conn, getStatus, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getCreditOperationStatus() {
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