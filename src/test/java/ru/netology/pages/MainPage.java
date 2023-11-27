package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {

    private SelenideElement buyButton = $x("//*[contains(text(),'Купить')]");
    private SelenideElement creditButton = $x("//*[contains(text(),'Купить в кредит')]");

    public CardDebetPaymentPage buyTourOnDebetCard() {
        buyButton.click();
        return new CardDebetPaymentPage();
    }

    public CardCreditPaymentPage buyTourOnCreditCard() {
        creditButton.click();
        return new CardCreditPaymentPage();
    }
}