package com.codecool.exceptionalatm;

import com.codecool.exceptionalatm.atm.Atm;
import com.codecool.exceptionalatm.card.BankCard;
import com.codecool.exceptionalatm.exception.*;

public class User {
    BankCard card;
    String pin;
    Integer moneyInWallet;
    Atm actualAtm;

    /**
     *
     * @param card not null
     * @param pin for the card, not null
     */
    public User(BankCard card, String pin) {
        this.card = card;
        this.pin = pin;
        this.moneyInWallet = 0;
    }

    /**
     *
     * @return the amount of the money in the wallet
     */
    public Integer getMoneyInWallet() {
        return moneyInWallet;
    }

    /**
     *
     * @param atm not null
     */
    public void visitAtm(Atm atm){
        actualAtm = atm;
    }

    /**
     * Withdraw money from the ATM and adds to the money in the wallet.
     * @param amount positive, not null
     * @throws InappropriateCardException  if the inserted Card is _not_ a BankCard -- should never happen
     * @throws AmountIsHigherThanTheLimitPerWithdrawalException  amount is higher than the the atm's limit of a single withdrowal
     * @throws AtmIsOutOfMoneyException  there is not enough money in the ATM
     * @throws LowOnFundsException  amount on the card is less than the requested amount
     * @throws InvalidPinException  the pin does not match with the pin of the inserted card
     * @throws InvalidInputException either the pin or the amount is not valid
     * @throws NoCardException there is no bank card in the ATM  -- should not happen
     */
    public void withdrawMoney(Integer amount) throws
            InappropriateCardException, AmountIsHigherThanTheLimitPerWithdrawalException, AtmIsOutOfMoneyException,
            LowOnFundsException, InvalidPinException, InvalidInputException, NoCardException {
        actualAtm.insertCard(card);
        actualAtm.insertPin(pin);
        actualAtm.getAmount(amount);
        actualAtm.removeCard();
    }
}
