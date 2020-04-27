import java.util.Random;

/**
 * This class simulates the board game with two players
 * Player 1 is always human
 * Player 2 can either be human or A.I
 */

public class BoardGame {
    Player player1;
    Player player2;

    public BoardGame() {
        player1 = new Player();
        player2 = new Player();
    }

    /**
     * Generates 30 cards for each players to select their starting 10 cards
     */
    public void generateRandomStartingCards() {
        for (int i = 0; i < 30; i++) {
            player1.addToCompleteList();
            player2.addToCompleteList();
        }
    }

    /**
     * Calculates and returns average party attack dealed for each party member ( = total damage / number of members)
     * @param playerNumber Which player is attacking? (Player 1 or Player 2)
     * @param attackType Attack name (Wond? Kill? Bite? ...)
     * @return verage party attack dealed for each party member  = total damage / number of members
     */
    public int averagePartyAttack(int playerNumber, String attackType) {
        int totalAtt = 0;
        int averageAtt = 0;
        Animal leader;
        if (playerNumber == 1) {
            for (Animal animal : getPlayer1().getWarParty()) {
                totalAtt += animal.getAttacks().get(attackType);
                averageAtt = totalAtt / getPlayer1().getWarParty().size();
            }

        } else if (playerNumber == 2) {
            for (Animal animal : getPlayer2().getWarParty()) {
                totalAtt += animal.getAttacks().get(attackType);
                averageAtt = totalAtt / getPlayer2().getWarParty().size();
            }
        }
        return averageAtt;
    }

    /**
     * Calculates and returns total damage dealt from war party
     * @param playerNumber Which player is attacking? (Player 1 or Player 2)
     * @param attackType Attack name (Wond? Kill? Bite? ...)
     * @return total damage dealt from war party
     */
    public int totalPartyAttack(int playerNumber, String attackType) {
        int totalAtt = 0;
        Animal leader;
        if (playerNumber == 1) {
            for (Animal animal : getPlayer1().getWarParty()) {
                totalAtt += animal.getAttacks().get(attackType);
            }

        } else if (playerNumber == 2) {
            for (Animal animal : getPlayer2().getWarParty()) {
                totalAtt += animal.getAttacks().get(attackType);
            }
        }
        return totalAtt;
    }

    /**
     * Attacks other player's selected card with war party. War party can have 1 or more members
     * @param indexAttacked Opponet's card which is under attack
     * @param playerNum Number of the player who is attacking
     * @param attackType War party attack type (Wond? Kill? Bite? ...)
     * @return true: Every party member has enough SP for attack and party attacked successfully | false: not enough SP for attack
     */
    public boolean partyAttack(int indexAttacked, int playerNum, String attackType) {
        if (playerNum == 1) {
            int attackDamage = averagePartyAttack(1, attackType);
            int totalDamage = totalPartyAttack(1, attackType);
            for (Animal animal : getPlayer1().getWarParty()) {
                if (!animal.checkSPforAttack(attackDamage))
                    return false;
            }
            for (Animal animal : getPlayer1().getWarParty()) {
                animal.loseSP(attackDamage);
            }
            player2.getSelectedAnimals().get(indexAttacked).loseHP(totalDamage);
            if (player2.getSelectedAnimals().get(indexAttacked).getHP() <= 0) { //When opponet's card has below 0 HP, then it is dead!
                player2.getSelectedAnimals().get(indexAttacked).setHP(0);
                player2.getSelectedAnimals().get(indexAttacked).setAlive(false);
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "R.I.P " + player2.getSelectedAnimals().get(indexAttacked).toString() + " :(\nAnimal removed from Player 2's deck" + ConsoleColors.RESET);
                player2.getSelectedAnimals().remove(indexAttacked);
                sleep();
            }
        } else if (playerNum == 2) {
            int attackDamage = averagePartyAttack(2, attackType);
            int totalDamage = totalPartyAttack(2, attackType);
            for (Animal animal : getPlayer2().getWarParty()) {
                if (!animal.checkSPforAttack(attackDamage))
                    return false;
            }
            for (Animal animal : getPlayer2().getWarParty()) {
                animal.loseSP(attackDamage);
            }
            player1.getSelectedAnimals().get(indexAttacked).loseHP(totalDamage);
            if (player1.getSelectedAnimals().get(indexAttacked).getHP() <= 0) { //When opponet's card has below 0 HP, then it is dead!
                player1.getSelectedAnimals().get(indexAttacked).setHP(0);
                player1.getSelectedAnimals().get(indexAttacked).setAlive(false);
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "R.I.P " + player1.getSelectedAnimals().get(indexAttacked).toString() + " :(\nAnimal removed from Player 1's deck" + ConsoleColors.RESET);
                player1.getSelectedAnimals().remove(indexAttacked);
                sleep();
            }
        }
        return true;
    }

    /**
     * Generates a random number (1 or 2) for A.I to decide to use which attack type (if selected animal has 2 attack types)
     * @return 1 or 2
     */
    public int random2() {
        Random random = new Random();
        int a = random.nextInt(2) + 1;
        return a;
    }

    /**
     * Controls if one player has won the game
     * @return true: match is finished and one player has won the game! | false: match is not finished yet
     */
    public boolean checkFinish() {
        if (player1.getSelectedAnimals().size() == 0) {
            System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "Player 2 won the match!\n\n ###End of the game###" + ConsoleColors.RESET);
            return true;
        } else if (player2.getSelectedAnimals().size() == 0) {
            System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "Player 1 won the match!\n\n ###End of the game###" + ConsoleColors.RESET);
            return true;
        } else
            return false;
    }

    /**
     * Stops porcessing for 2 second. Player can observe his cards
     */
    public void sleep(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    /* *********** Getter and Setter Methods *********** Start */

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
    /* *********** Getter and Setter Methods *********** End */
}
