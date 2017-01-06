package com.codecool.exceptionalatm.bank;

import com.codecool.exceptionalatm.exception.LowOnFundsException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Created by ani on 2017.01.05..
 */
public class BankTest {
    private Integer amountOnCard;
    private String cardId;
    private Bank bank;

    @Before
    public void setUp() throws Exception {
        cardId = UUID.randomUUID().toString();
        amountOnCard = 42;
        Map<String, Integer> amountsOnCards = new HashMap<>();
        amountsOnCards.put(cardId, amountOnCard);
        bank = new Bank(amountsOnCards);
    }

    @Test (expected=LowOnFundsException.class)
    public void withdrawMoneyFromCard_notEnoughMoneyOnTheCard_throwsException() throws Exception {
        bank.withdrawMoneyFromCard(cardId, amountOnCard+1);
    }

    @Test
    public void withdrawMoneyFromCard_happyDay_returnsTheWithdrawnMoney() throws Exception {
        assertEquals(amountOnCard, bank.withdrawMoneyFromCard(cardId, amountOnCard));
    }

    @Test (expected=LowOnFundsException.class)
    public void withdrawMoneyFromCard_happyDay_decreasesTheAmountOnTheCard() throws Exception {
        bank.withdrawMoneyFromCard(cardId, amountOnCard);
        bank.withdrawMoneyFromCard(cardId, 1);
    }


}