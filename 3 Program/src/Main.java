import java.util.Scanner;

/**
 * This program simulates a board game
 * @author Arash
 * @version 1.0.0
 */
public class Main {

    /**
     * Controls whether input string is non negative or not
     * @param str input string by user
     * @return true: string is a non negative number | false: input is not non negative or is not a number
     */
    public static boolean isPositiveNumeric(String str) {
        if (str.equals(""))
            return false;
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    /**
     * Controls whether or not the input is a number in the given range
     * @param start start of the range
     * @param end end of the range
     * @param str input by user
     * @return true: is in the given range | false: is not in the given range
     */
    public static boolean checkNumbericInRange(int start, int end, String str) {
        if (str.equals(""))
            return false;
        else if (!isPositiveNumeric(str))
            return false;
        else {
            long number = Long.parseLong(str);
            if (number > end || number < start)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        BoardGame boardGame = new BoardGame();
        boardGame.generateRandomStartingCards();

        System.out.println("Welcome to ForestAnimals Board Game!\nSelect a game mode:\n[1] PvP\n[2] PvE");
        Scanner scanner = new Scanner(System.in);
        String gameMode = scanner.nextLine();
        while (!gameMode.equals("1") && !gameMode.equals("2")) {
            System.out.print("Wrong input - Please select a correct game mode\n> ");
            gameMode = scanner.nextLine();
        }
        if (gameMode.equals("1")) {
            System.out.println("PvP battle is going to start!");
            System.out.println("Player 1: Please select 10 cards from the deck by entering card number:");
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < boardGame.getPlayer1().getCompleteList().size(); j++)
                    System.out.println("[" + j + "]" + " " + boardGame.getPlayer1().getCompleteList().get(j).toString());
                Scanner cardScanner = new Scanner(System.in);
                String card;
                System.out.print("> ");
                card = cardScanner.nextLine();
                while (!isPositiveNumeric(card)) {
                    System.out.print("\nYou must enter card's number:\n> ");
                    card = scanner.nextLine();
                }
                int cardNumber = Integer.parseInt(card);
                while (cardNumber < 0 || cardNumber > boardGame.getPlayer1().getCompleteList().size() - 1) {
                    System.out.print("Card number is not valid. Enter card's number again:\n> ");
                    card = cardScanner.nextLine();
                    while (!isPositiveNumeric(card)) {
                        System.out.print("\nYou must enter card's number:\n> ");
                        card = scanner.nextLine();
                    }
                    cardNumber = Integer.parseInt(card);
                }
                boardGame.getPlayer1().addToSelectedList(boardGame.getPlayer1().getCompleteList().get(cardNumber));
                boardGame.getPlayer1().getCompleteList().remove(cardNumber);
                if (i < 9)
                    System.out.println("Card selected, select next card:");
            }
            System.out.println("Card selection for player 1 completed!\n\n");

            String resume;

            System.out.println("Player 2, Enter r to continue\n> ");
            resume = scanner.nextLine();
            while (!resume.equals("r"))
                resume = scanner.nextLine();


            System.out.println("Player 2: Please select 10 cards from the deck by entering card number:");
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < boardGame.getPlayer2().getCompleteList().size(); j++)
                    System.out.println("[" + j + "]" + " " + boardGame.getPlayer2().getCompleteList().get(j).toString());
                Scanner cardScanner = new Scanner(System.in);
                String card;
                System.out.print("> ");
                card = cardScanner.nextLine();
                while (!isPositiveNumeric(card)) {
                    System.out.print("\nYou must enter card's number:\n> ");
                    card = scanner.nextLine();
                }
                int cardNumber = Integer.parseInt(card);
                while (cardNumber < 0 || cardNumber > boardGame.getPlayer2().getCompleteList().size() - 1) {
                    System.out.print("Card number is not valid. Enter card's number again:\n> ");
                    card = cardScanner.nextLine();
                    while (!isPositiveNumeric(card)) {
                        System.out.print("\nYou must enter card's number:\n> ");
                        card = scanner.nextLine();
                    }
                    cardNumber = Integer.parseInt(card);
                }
                boardGame.getPlayer2().addToSelectedList(boardGame.getPlayer2().getCompleteList().get(cardNumber));
                boardGame.getPlayer2().getCompleteList().remove(cardNumber);
                if (i < 9)
                    System.out.println("Card selected, select next card:");
            }
            System.out.println("Card selection for player 2 completed!\n\n");
            while (!boardGame.checkFinish()) {
                System.out.println("It is Player 1's turn, Enter r to continue\n> ");
                resume = scanner.nextLine();
                while (!resume.equals("r"))
                    resume = scanner.nextLine();
                System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Your deck:" + ConsoleColors.RESET);
                boardGame.getPlayer1().printSelectedCards();
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Opponet's deck:" + ConsoleColors.RESET);
                boardGame.getPlayer2().printSelectedCards();
                System.out.print("\n\n[1] Attack\n[2] Restore SP\n> ");
                String option = scanner.nextLine();
                while (!checkNumbericInRange(1, 2, option)) {
                    System.out.print("\nWrong input, select one of the above options!\n> ");
                    option = scanner.nextLine();
                }
                int failedTry = 0; //If player failed attack tries go above 3, he loses the round
                if (option.equals("1")) {
                    while (true) {
                        if (failedTry >= 3) {
                            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "You lost your turn. Too many failed attempts" + ConsoleColors.RESET);
                            break;

                        }
                        String str;
                        System.out.print("\nSelect your war party leader:\n> ");
                        str = scanner.nextLine();
                        while (!checkNumbericInRange(0, boardGame.getPlayer1().getSelectedAnimals().size() - 1, str)) {
                            System.out.print("Wrong input. Try again\n> ");
                            str = scanner.nextLine();
                        }
                        int leader = Integer.parseInt(str);
                        int attackType = 1; //Attack type is primary by default. If leader has 2 attack types, he can choose which one
                        if (!boardGame.getPlayer1().getSelectedAnimals().get(leader).getAttack2().equals("")) {
                            System.out.println("\nSelect attack type:\n[1] " + boardGame.getPlayer1().getSelectedAnimals().get(leader).getAttack1() + " - " + boardGame.getPlayer1().getSelectedAnimals().get(leader).getAttackPrimary());
                            System.out.print("[2] " + boardGame.getPlayer1().getSelectedAnimals().get(leader).getAttack2() + " - " + boardGame.getPlayer1().getSelectedAnimals().get(leader).getAttackSecondary() + "\n> ");
                            str = scanner.nextLine();
                            while (!checkNumbericInRange(1, 2, str)) {
                                System.out.print("Wrong input. Try again:\n> ");
                                str = scanner.nextLine();
                            }
                            attackType = Integer.parseInt(str);
                        }
                        String leaderAttack;
                        if (attackType == 1) {
                            boardGame.getPlayer1().addToWarParty(leader, boardGame.getPlayer1().getSelectedAnimals().get(leader).getAttack1());
                            leaderAttack = boardGame.getPlayer1().getWarParty().get(0).getAttack1();
                        } else { //attackType==2
                            boardGame.getPlayer1().addToWarParty(leader, boardGame.getPlayer1().getSelectedAnimals().get(leader).getAttack2());
                            leaderAttack = boardGame.getPlayer1().getWarParty().get(0).getAttack2();
                        }
                        int finish = -1;
                        while (true) {
                            if (finish == 0)
                                break;
                            boardGame.getPlayer1().printSelectedCards();
                            System.out.println("WarParty:");
                            boardGame.getPlayer1().showWarParty();
                            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Select party members. Enter \"F\" (Capital) to finish" + ConsoleColors.RESET);
                            System.out.print("> ");
                            String tmp = scanner.nextLine();
                            if (tmp.equals("F"))
                                break;
                            while (!checkNumbericInRange(0, boardGame.getPlayer1().getSelectedAnimals().size() - 1, tmp)) {
                                System.out.print("Wrong input! Try again.\n> ");
                                tmp = scanner.nextLine();
                                if (tmp.equals("F")) {
                                    finish = 0;
                                    break;
                                }
                            }
                            if (tmp.equals("F"))
                                break;
                            int member = Integer.parseInt(tmp);
                            if (!boardGame.getPlayer1().addToWarParty(member, leaderAttack)) {
                                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Can't be added to WarParty - No compatible attack found!" + ConsoleColors.RESET);
                                continue;
                            }
                        }
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Opponet's deck:" + ConsoleColors.RESET);
                        boardGame.getPlayer2().printSelectedCards();
                        System.out.print("Select opponet's card to attack\n> ");
                        str = scanner.nextLine();
                        while (!checkNumbericInRange(0, boardGame.getPlayer2().getSelectedAnimals().size() - 1, str)) {
                            System.out.print("Wrong input. Try again\n> ");
                            str = scanner.nextLine();
                        }
                        int attacked = Integer.parseInt(str);
                        int finishAttack = 0;
                        if (boardGame.partyAttack(attacked, 1, leaderAttack)) {
                            System.out.println("Successfully attacked!");
                            finishAttack = 1;
                        } else {
                            System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "Cannot attack, not enough SP for party members. You must select the party again" + ConsoleColors.RESET);
                            finishAttack = 0;
                            failedTry++;
                            boardGame.sleep();
                        }

                        boardGame.getPlayer1().disbandParty();
                        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Your deck:" + ConsoleColors.RESET);
                        boardGame.getPlayer1().printSelectedCards();
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Opponet's deck:" + ConsoleColors.RESET);
                        boardGame.getPlayer2().printSelectedCards();
                        if (finishAttack == 1) {
                            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Player 1's turn finished\n\n" + ConsoleColors.RESET);
                            break;
                        }
                    }
                } else if (option.equals("2")) {
                    System.out.println("Select the card index you want to restore its SP: ");
                    String selectedCard = scanner.nextLine();
                    while (!checkNumbericInRange(0, boardGame.getPlayer1().getSelectedAnimals().size() - 1, selectedCard)) {
                        System.out.print("\nOops! Select a valid card:\n> ");
                        selectedCard = scanner.nextLine();
                    }
                    int selectedcard = Integer.parseInt(selectedCard);
                    boardGame.getPlayer1().getSelectedAnimals().get(selectedcard).restoreSP();
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Player 1's turn finished\n\n" + ConsoleColors.RESET);
                }
                if (boardGame.checkFinish())
                    System.exit(0);

                System.out.println("It is Player 2's turn, Enter r to continue\n> "); //Exact same coding as player 1
                resume = scanner.nextLine();
                while (!resume.equals("r"))
                    resume = scanner.nextLine();
                System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Your deck:" + ConsoleColors.RESET);
                boardGame.getPlayer2().printSelectedCards();
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Opponet's deck:" + ConsoleColors.RESET);
                boardGame.getPlayer1().printSelectedCards();
                System.out.print("\n\n[1] Attack\n[2] Restore SP\n> ");
                option = scanner.nextLine();
                while (!checkNumbericInRange(1, 2, option)) {
                    System.out.print("\nWrong input, select one of the above options!\n> ");
                    option = scanner.nextLine();
                }
                failedTry = 0;
                if (option.equals("1")) {
                    while (true) {
                        if(failedTry>=3){
                            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "You lost your turn. Too many failed attempts" + ConsoleColors.RESET);
                            break;
                        }
                        String str;
                        System.out.print("\nSelect your war party leader:\n> ");
                        str = scanner.nextLine();
                        while (!checkNumbericInRange(0, boardGame.getPlayer2().getSelectedAnimals().size() - 1, str)) {
                            System.out.print("Wrong input. Try again\n> ");
                            str = scanner.nextLine();
                        }
                        int leader = Integer.parseInt(str);
                        int attackType = 1;
                        if (!boardGame.getPlayer2().getSelectedAnimals().get(leader).getAttack2().equals("")) {
                            System.out.println("\nSelect attack type:\n[1] " + boardGame.getPlayer2().getSelectedAnimals().get(leader).getAttack1() + " - " + boardGame.getPlayer2().getSelectedAnimals().get(leader).getAttackPrimary());
                            System.out.print("[2] " + boardGame.getPlayer2().getSelectedAnimals().get(leader).getAttack2() + " - " + boardGame.getPlayer2().getSelectedAnimals().get(leader).getAttackSecondary() + "\n> ");
                            str = scanner.nextLine();
                            while (!checkNumbericInRange(1, 2, str)) {
                                System.out.print("Wrong input. Try again:\n> ");
                                str = scanner.nextLine();
                            }
                            attackType = Integer.parseInt(str);
                        }
                        String leaderAttack;
                        if (attackType == 1) {
                            boardGame.getPlayer2().addToWarParty(leader, boardGame.getPlayer2().getSelectedAnimals().get(leader).getAttack1());
                            leaderAttack = boardGame.getPlayer2().getWarParty().get(0).getAttack1();
                        } else { //attackType==2
                            boardGame.getPlayer2().addToWarParty(leader, boardGame.getPlayer2().getSelectedAnimals().get(leader).getAttack2());
                            leaderAttack = boardGame.getPlayer2().getWarParty().get(0).getAttack2();
                        }
                        int finish = -1;
                        while (true) {
                            if (finish == 0)
                                break;
                            boardGame.getPlayer2().printSelectedCards();
                            System.out.println("WarParty:");
                            boardGame.getPlayer2().showWarParty();
                            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Select party members. Enter \"F\" (Capital) to finish" + ConsoleColors.RESET);
                            System.out.print("> ");
                            String tmp = scanner.nextLine();
                            if (tmp.equals("F"))
                                break;
                            while (!checkNumbericInRange(0, boardGame.getPlayer2().getSelectedAnimals().size() - 1, tmp)) {
                                System.out.print("Wrong input! Try again.\n> ");
                                tmp = scanner.nextLine();
                                if (tmp.equals("F")) {
                                    finish = 0;
                                    break;
                                }
                            }
                            if (tmp.equals("F"))
                                break;
                            int member = Integer.parseInt(tmp);
                            if (!boardGame.getPlayer2().addToWarParty(member, leaderAttack)) {
                                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Can't be added to WarParty - No compatible attack found!" + ConsoleColors.RESET);
                                continue;
                            }
                        }
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Opponet's deck:" + ConsoleColors.RESET);
                        boardGame.getPlayer1().printSelectedCards();
                        System.out.print("Select opponet's card to attack\n> ");
                        str = scanner.nextLine();
                        while (!checkNumbericInRange(0, boardGame.getPlayer1().getSelectedAnimals().size() - 1, str)) {
                            System.out.print("Wrong input. Try again\n> ");
                            str = scanner.nextLine();
                        }
                        int attacked = Integer.parseInt(str);
                        int finishAttack = 0;
                        if (boardGame.partyAttack(attacked, 2, leaderAttack)) {
                            System.out.println("Successfully attacked!");
                            finishAttack = 1;
                        } else {
                            System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "Cannot attack, not enough SP for party members. You must select the party again" + ConsoleColors.RESET);
                            finishAttack = 0;
                            failedTry++;
                            boardGame.sleep();
                        }
                        boardGame.getPlayer2().disbandParty();
                        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Your deck:" + ConsoleColors.RESET);
                        boardGame.getPlayer2().printSelectedCards();
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Opponet's deck:" + ConsoleColors.RESET);
                        boardGame.getPlayer1().printSelectedCards();
                        if (finishAttack == 1) {
                            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Player 2's turn finished\n\n" + ConsoleColors.RESET);
                            break;
                        }
                    }
                } else if (option.equals("2")) {
                    System.out.println("Select the card index you want to restore its SP: ");
                    String selectedCard = scanner.nextLine();
                    while (!checkNumbericInRange(0, boardGame.getPlayer2().getSelectedAnimals().size() - 1, selectedCard)) {
                        System.out.print("\nOops! Select a valid card:\n> ");
                        selectedCard = scanner.nextLine();
                    }
                    int selectedcard = Integer.parseInt(selectedCard);
                    boardGame.getPlayer2().getSelectedAnimals().get(selectedcard).restoreSP();
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Player 2's turn finished\n\n" + ConsoleColors.RESET);
                }
                if (boardGame.checkFinish())
                    System.exit(0);
            }


        } else if (gameMode.equals("2")) { //Player vs Computer
            System.out.println("PvE battle is going to start!");
            System.out.println("Please select 10 cards from the deck by entering card number:");
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < boardGame.getPlayer1().getCompleteList().size(); j++)
                    System.out.println("[" + j + "]" + " " + boardGame.getPlayer1().getCompleteList().get(j).toString());
                Scanner cardScanner = new Scanner(System.in);
                String card;
                System.out.print("> ");
                card = cardScanner.nextLine();
                while (!isPositiveNumeric(card)) {
                    System.out.print("\nYou must enter card's number:\n> ");
                    card = scanner.nextLine();
                }
                int cardNumber = Integer.parseInt(card);
                while (cardNumber < 0 || cardNumber > boardGame.getPlayer1().getCompleteList().size() - 1) {
                    System.out.print("Card number is not valid. Enter card's number again:\n> ");
                    card = cardScanner.nextLine();
                    while (!isPositiveNumeric(card)) {
                        System.out.print("\nYou must enter card's number:\n> ");
                        card = scanner.nextLine();
                    }
                    cardNumber = Integer.parseInt(card);
                }
                boardGame.getPlayer1().addToSelectedList(boardGame.getPlayer1().getCompleteList().get(cardNumber));
                boardGame.getPlayer1().getCompleteList().remove(cardNumber);
                if (i < 9)
                    System.out.println("Card selected, select next card:");
            }
            System.out.println("Card selection for player 1 completed!\n\n");
            String resume;

            System.out.println("Computer is chosing its cards...");
            boardGame.getPlayer2().selectRandomCardsForSelection();
            System.out.println("Card selection for computer completed!\n\n");
            while (!boardGame.checkFinish()) {
                System.out.println("It is your turn, Enter r to continue\n> ");
                resume = scanner.nextLine();
                while (!resume.equals("r"))
                    resume = scanner.nextLine();
                System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Your deck:" + ConsoleColors.RESET);
                boardGame.getPlayer1().printSelectedCards();
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Opponet's deck:" + ConsoleColors.RESET);
                boardGame.getPlayer2().printSelectedCards();
                System.out.print("\n\n[1] Attack\n[2] Restore SP\n> ");
                String option = scanner.nextLine();
                while (!checkNumbericInRange(1, 2, option)) {
                    System.out.print("\nWrong input, select one of the above options!\n> ");
                    option = scanner.nextLine();
                }
                int failedTry = 0;
                if (option.equals("1")) {
                    while (true) {
                        if (failedTry >= 3) {
                            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "You lost your turn. Too many failed attempts" + ConsoleColors.RESET);
                            break;

                        }
                        String str;
                        System.out.print("\nSelect your war party leader:\n> ");
                        str = scanner.nextLine();
                        while (!checkNumbericInRange(0, boardGame.getPlayer1().getSelectedAnimals().size() - 1, str)) {
                            System.out.print("Wrong input. Try again\n> ");
                            str = scanner.nextLine();
                        }
                        int leader = Integer.parseInt(str);
                        int attackType = 1;
                        if (!boardGame.getPlayer1().getSelectedAnimals().get(leader).getAttack2().equals("")) {
                            System.out.println("\nSelect attack type:\n[1] " + boardGame.getPlayer1().getSelectedAnimals().get(leader).getAttack1() + " - " + boardGame.getPlayer1().getSelectedAnimals().get(leader).getAttackPrimary());
                            System.out.print("[2] " + boardGame.getPlayer1().getSelectedAnimals().get(leader).getAttack2() + " - " + boardGame.getPlayer1().getSelectedAnimals().get(leader).getAttackSecondary() + "\n> ");
                            str = scanner.nextLine();
                            while (!checkNumbericInRange(1, 2, str)) {
                                System.out.print("Wrong input. Try again:\n> ");
                                str = scanner.nextLine();
                            }
                            attackType = Integer.parseInt(str);
                        }
                        String leaderAttack;
                        if (attackType == 1) {
                            boardGame.getPlayer1().addToWarParty(leader, boardGame.getPlayer1().getSelectedAnimals().get(leader).getAttack1());
                            leaderAttack = boardGame.getPlayer1().getWarParty().get(0).getAttack1();
                        } else { //attackType==2
                            boardGame.getPlayer1().addToWarParty(leader, boardGame.getPlayer1().getSelectedAnimals().get(leader).getAttack2());
                            leaderAttack = boardGame.getPlayer1().getWarParty().get(0).getAttack2();
                        }
                        int finish = -1;
                        while (true) {
                            if (finish == 0)
                                break;
                            boardGame.getPlayer1().printSelectedCards();
                            System.out.println("WarParty:");
                            boardGame.getPlayer1().showWarParty();
                            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Select party members. Enter \"F\" (Capital) to finish" + ConsoleColors.RESET);
                            System.out.print("> ");
                            String tmp = scanner.nextLine();
                            if (tmp.equals("F"))
                                break;
                            while (!checkNumbericInRange(0, boardGame.getPlayer1().getSelectedAnimals().size() - 1, tmp)) {
                                System.out.print("Wrong input! Try again.\n> ");
                                tmp = scanner.nextLine();
                                if (tmp.equals("F")) {
                                    finish = 0;
                                    break;
                                }
                            }
                            if (tmp.equals("F"))
                                break;
                            int member = Integer.parseInt(tmp);
                            if (!boardGame.getPlayer1().addToWarParty(member, leaderAttack)) {
                                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Can't be added to WarParty - No compatible attack found!" + ConsoleColors.RESET);
                                continue;
                            }
                        }
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Opponet's deck:" + ConsoleColors.RESET);
                        boardGame.getPlayer2().printSelectedCards();
                        System.out.print("Select opponet's card to attack\n> ");
                        str = scanner.nextLine();
                        while (!checkNumbericInRange(0, boardGame.getPlayer2().getSelectedAnimals().size() - 1, str)) {
                            System.out.print("Wrong input. Try again\n> ");
                            str = scanner.nextLine();
                        }
                        int attacked = Integer.parseInt(str);
                        int finishAttack = 0;
                        if (boardGame.partyAttack(attacked, 1, leaderAttack)) {
                            System.out.println("Successfully attacked!");
                            finishAttack = 1;
                        } else {
                            System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "Cannot attack, not enough SP for party members. You must select the party again" + ConsoleColors.RESET);
                            finishAttack = 0;
                            failedTry++;
                            boardGame.sleep();
                        }

                        boardGame.getPlayer1().disbandParty();
                        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Your deck:" + ConsoleColors.RESET);
                        boardGame.getPlayer1().printSelectedCards();
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Computer's deck:" + ConsoleColors.RESET);
                        boardGame.getPlayer2().printSelectedCards();
                        if (finishAttack == 1) {
                            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Player 1's turn finished\n\n" + ConsoleColors.RESET);
                            break;
                        }
                    }
                } else if (option.equals("2")) { //Player vs Computer
                    System.out.println("Select the card index you want to restore its SP: ");
                    String selectedCard = scanner.nextLine();
                    while (!checkNumbericInRange(0, boardGame.getPlayer1().getSelectedAnimals().size() - 1, selectedCard)) {
                        System.out.print("\nOops! Select a valid card:\n> ");
                        selectedCard = scanner.nextLine();
                    }
                    int selectedcard = Integer.parseInt(selectedCard);
                    boardGame.getPlayer1().getSelectedAnimals().get(selectedcard).restoreSP();
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Player 1's turn finished\n\n" + ConsoleColors.RESET);
                }
                if (boardGame.checkFinish())
                    System.exit(0);

                System.out.println("It is computers turn");
                System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Computer's deck:" + ConsoleColors.RESET);
                boardGame.getPlayer2().printSelectedCards();
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Your deck:" + ConsoleColors.RESET);
                boardGame.getPlayer1().printSelectedCards();
                option = "1";
                failedTry = 0; //If failed tries go above 3, computer will restore a card's SP and cancels attacking
                if (option.equals("1")) {
                    while (true) {
                        if(failedTry>=3){
                            option = "2";
                            break;
                        }
                        int leader = boardGame.getPlayer2().randomLeaderIndex();
                        int attackType = 1;
                        if (!boardGame.getPlayer2().getSelectedAnimals().get(leader).getAttack2().equals("")) {
                            attackType = boardGame.random2();
                        }
                        String leaderAttack;
                        if (attackType == 1) {
                            boardGame.getPlayer2().addToWarParty(leader, boardGame.getPlayer2().getSelectedAnimals().get(leader).getAttack1());
                            leaderAttack = boardGame.getPlayer2().getWarParty().get(0).getAttack1();
                        } else { //attackType==2
                            boardGame.getPlayer2().addToWarParty(leader, boardGame.getPlayer2().getSelectedAnimals().get(leader).getAttack2());
                            leaderAttack = boardGame.getPlayer2().getWarParty().get(0).getAttack2();
                        }
                        int attacked = boardGame.getPlayer1().randomLeaderIndex();
                        int finishAttack = 0;
                        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT+"Computer is going to attack.\nYour under attack card: "+boardGame.getPlayer1().getSelectedAnimals().get(attacked)+"\nAttacker: "+boardGame.getPlayer2().getWarParty().get(0));
                        boardGame.sleep();
                        if (boardGame.partyAttack(attacked, 2, leaderAttack)) {
                            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+"Attack successfully completed"+ConsoleColors.RESET);
                            finishAttack = 1;
                            boardGame.sleep();
                        } else {
                            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT+"Attack failed"+ConsoleColors.RESET);
                            finishAttack = 0;
                            failedTry++;
                            boardGame.sleep();
                        }
                        boardGame.getPlayer2().disbandParty();
                        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Your deck:" + ConsoleColors.RESET);
                        boardGame.getPlayer1().printSelectedCards();
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Computer's deck:" + ConsoleColors.RESET);
                        boardGame.getPlayer2().printSelectedCards();
                        if (finishAttack == 1) {
                            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Computer's turn finished\n\n" + ConsoleColors.RESET);
                            break;
                        }
                    }
                } if (option.equals("2")) {
                    System.out.println("Computer has chosen to restore SP: ");
                    int selectedCard = boardGame.getPlayer2().restoreSPforAI();
                    boardGame.getPlayer2().getSelectedAnimals().get(selectedCard).restoreSP();
                    System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT+"Computer restored SP for the following card:\n["+selectedCard+"] "+boardGame.getPlayer2().getSelectedAnimals().get(selectedCard).toString()+ConsoleColors.RESET+"\n");
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Computer's turn finished\n\n" + ConsoleColors.RESET);
                }
                if (boardGame.checkFinish())
                    System.exit(0);
            }
        }
    }
}


