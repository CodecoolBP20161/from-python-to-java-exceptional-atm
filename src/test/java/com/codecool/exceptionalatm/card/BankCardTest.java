package com.codecool.exceptionalatm.card;

import com.codecool.exceptionalatm.card.BankCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by ani on 2017.01.05..
 */
public class BankCardTest {
    String correctPin;
    BankCard bankCard;

    @Before
    public void setUp() throws Exception {
        correctPin = "1234";
        bankCard = new BankCard(correctPin);
    }

    @Test
    public void checkPin_correctPin_true() throws Exception {
        assertTrue(bankCard.checkPin(correctPin));
    }

    @Test
    public void checkPin_incorrectPin_false() throws Exception {
        assertFalse(bankCard.checkPin("9999"));
    }

    @Test
    public void checkPin_null_false() throws Exception {
        assertFalse(bankCard.checkPin(null));
    }

}