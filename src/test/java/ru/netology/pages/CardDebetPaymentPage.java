package ru.netology.pages;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.helpers.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardDebetPaymentPage {

    private final SelenideElement heading = $x("//*[contains(text(),'Оплата по карте')]");

    private final ElementsCollection form = $$(".form-field .input");

    private final SelenideElement cardNumberField = form.find(exactText("Номер карты")).$(".input__control");
    private final SelenideElement cardMonthField = form.find(exactText("Месяц")).$(".input__control");
    private final SelenideElement cardYearField = form.find(exactText("Год")).$(".input__control");
    private final SelenideElement cardHolderField = form.find(exactText("Владелец")).$(".input__control");
    private final SelenideElement cardCodeField = form.find(exactText("CVC/CVV")).$(".input__control");
    private final SelenideElement continueButton = $$("[type='button']").find(exactText("Продолжить"));
    private final SelenideElement successNotification = $(".notification_status_ok");
    private final SelenideElement errorNotification = $(".notification_status_error");
    private final ElementsCollection fieldSubMessage = $$(".form-field .input .input__top");
    private final SelenideElement cardNumberFieldSubMessage =
            fieldSubMessage.find(exactText("Номер карты")).parent().$(".input__sub");
    private final SelenideElement cardMonthFieldSubMessage =
            fieldSubMessage.find(exactText("Месяц")).parent().$(".input__sub");
    private final SelenideElement cardYearFieldSubMessage =
            fieldSubMessage.find(exactText("Год")).parent().$(".input__sub");
    private final SelenideElement cardHolderFieldSubMessage =
            fieldSubMessage.find(exactText("Владелец")).parent().$(".input__sub");
    private final SelenideElement cardCodeFieldSubMessage =
            fieldSubMessage.find(exactText("CVC/CVV")).parent().$(".input__sub");

    public CardDebetPaymentPage() {
        heading.shouldBe(visible);
    }

    public void buyOnCredit(DataHelper.FormData formData) {
        cardNumberField.setValue(formData.getNumber());
        cardMonthField.setValue(formData.getMonth());
        cardYearField.setValue(formData.getYear());
        cardHolderField.setValue(formData.getHolder());
        cardCodeField.setValue(formData.getCvc());
        continueButton.click();
    }

    public void sendVoidDebetForm() {
        continueButton.click();
    }

    public void completeNotice() {
        successNotification.shouldHave(Condition.text("Операция одобрена банком"),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void failedNotice() {
        errorNotification.shouldHave(Condition.text("Банк отказал в проведении операции."),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void cardNumberFieldMessageIsSubMessage(String subMessage) {
        cardNumberFieldSubMessage.shouldHave(visible, exactText(subMessage));
    }

    public void cardMonthFieldMessageIsSubMessage(String subMessage) {
        cardMonthFieldSubMessage.shouldHave(visible, exactText(subMessage));
    }

    public void cardYearFieldMessageIsSubMessage(String subMessage) {
        cardYearFieldSubMessage.shouldHave(visible, exactText(subMessage));
    }

    public void cardOwnerFieldMessageIsSubMessage(String subMessage) {
        cardHolderFieldSubMessage.shouldHave(visible, exactText(subMessage));
    }

    public void cardCodeFieldMessageIsSubMessage(String subMessage) {
        cardCodeFieldSubMessage.shouldHave(visible, exactText(subMessage));
    }
}