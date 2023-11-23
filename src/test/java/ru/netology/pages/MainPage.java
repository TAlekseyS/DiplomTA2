package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {

    private SelenideElement buyButton = $x("//*[contains(text(),'Купить')]");
    private SelenideElement creditButton = $x("//*[contains(text(),'Купить в кредит')]");

    public CardCheckoutPage buyTourViaCard() {
        buyButton.click();
        return new CardCheckoutPage();
    }

    public CreditCheckoutPage buyTourViaCreditCard() {
        creditButton.click();
        return new CreditCheckoutPage();
    }
}