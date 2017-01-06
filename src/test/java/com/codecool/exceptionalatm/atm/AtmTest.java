package com.codecool.exceptionalatm.atm;

import com.codecool.exceptionalatm.bank.Bank;
import com.codecool.exceptionalatm.card.BankCard;
import com.codecool.exceptionalatm.card.Card;
import com.codecool.exceptionalatm.exception.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by ani on 2017.01.05..
 */
@RunWith(MockitoJUnitRunner.class)
public class AtmTest {
    public Atm atm;
    public Bank bank;
    public Integer amountOnCard;
    public Integer moneyInTheAtm;
    public String correctPin;
    public BankCard bankCard;

    @Before
    public void setUp() throws Exception {
        correctPin = "1234";
        bankCard = new BankCard(correctPin);
        amountOnCard = 42;

        Map<String, Integer> amountsOnCards = new HashMap<>();
        amountsOnCards.put(bankCard.getNumber(), amountOnCard);
        bank = new Bank(amountsOnCards);

        moneyInTheAtm = 1000;
        atm = new Atm(bank, moneyInTheAtm);
    }

    @org.junit.Test (expected=InappropriateCardException.class)
    public void insertCard_giftCard_shouldThrowException() throws Exception {
        Card giftCard = new Card();
        atm.insertCard(giftCard);
    }

    @org.junit.Test
    public void insertCard_bankCard_shouldStoreCard() throws Exception {
        atm.insertCard(bankCard);
        assertEquals(bankCard, atm.getCurrentCard());
    }

    @org.junit.Test (expected=InvalidInputException.class)
    public void insertPin_null_throwException() throws Exception {
        atm.insertPin(null);
    }

    @org.junit.Test (expected=InvalidInputException.class)
    public void insertPin_lengthDiffersFrom4_throwsException() throws Exception {
        atm.insertPin("123");
    }

    @org.junit.Test
    public void insertPin_validPin_storesThePin() throws Exception {
        atm.insertPin("1234");
        assertEquals("1234", atm.getCurrentPin());
    }

    @org.junit.Test (expected=InvalidInputException.class)
    public void getAmount_negativeAmount_throwsException() throws Exception {
        atm.getAmount(-1);
    }

    @org.junit.Test (expected=InvalidInputException.class)
    public void getAmount_zeroAmount_throwsException() throws Exception {
        atm.getAmount(0);
    }

    @org.junit.Test (expected=AmountIsHigherThanTheLimitPerWithdrawalException.class)
    public void getAmount_moreThanTheMoneyInTheAtm_throwsException() throws Exception {
        atm.getAmount(Atm.LIMIT_OF_WITHDRAWAL+1);
    }

    @org.junit.Test (expected=AtmIsOutOfMoneyException.class)
    public void getAmount_higherThanTheAtmsLimitPerWithdrawal_throwsException() throws Exception {
        Integer moneyInTheAtm = 5;
        atm = new Atm(bank, moneyInTheAtm);
        atm.getAmount(10);
    }

    @org.junit.Test (expected=NoCardException.class)
    public void getAmount_noCard_throwsException() throws Exception {
        atm.getAmount(amountOnCard);
    }

    @org.junit.Test (expected=InvalidPinException.class)
    public void getAmount_noPin_throwsException() throws Exception {
        atm.insertCard(bankCard);
        atm.getAmount(amountOnCard);
    }

    @org.junit.Test (expected=InvalidPinException.class)
    public void getAmount_incorrectPin_throwsException() throws Exception {
        atm.insertCard(bankCard);
        atm.insertPin("5678");
        atm.getAmount(amountOnCard);
    }

    @org.junit.Test
    public void getAmount_happyDay() throws Exception {
        atm.insertCard(bankCard);
        atm.insertPin(correctPin);
        assertEquals(amountOnCard, atm.getAmount(amountOnCard));
    }

    @org.junit.Test (expected=LowOnFundsException.class)
    public void getAmount_notEnoughMoneyOnTheCard_throwsException() throws Exception {
        atm.insertCard(bankCard);
        atm.insertPin(correctPin);
        atm.getAmount(amountOnCard+1);
    }

    @org.junit.Test
    public void removeCard_shouldClearCard() throws Exception {
        atm.insertCard(bankCard);
        atm.removeCard();
        assertNull(atm.getCurrentCard());
     }

    @org.junit.Test
    public void removeCard_shouldClearPin() throws Exception {
        atm.insertPin(correctPin);
        atm.removeCard();
        assertNull(atm.getCurrentPin());
    }

}