import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This class simulates a player
 * @author Arash
 */
public class Player {
    private ArrayList<Animal> completeList; //Complete 30 cards list
    private ArrayList<Animal> selectedAnimals; // Selected 10 cards
    private ArrayList<Animal> warParty; // War party cards

    public Player() {
        completeList = new ArrayList<Animal>();
        selectedAnimals = new ArrayList<Animal>();
        warParty = new ArrayList<Animal>();
    }

    /**
     *
     * @return a random animal for player to select his cards
     */
    public Animal randomAnimal() {
        Random r = new Random();
        int a = r.nextInt(12);
        switch (a) {
            case 0:
                return new Bear();
            case 1:
                return new Boar();
            case 2:
                return new Cow();
            case 3:
                return new Elephant();
            case 4:
                return new Fox();
            case 5:
                return new Hippopotamus();
            case 6:
                return new Lion();
            case 7:
                return new Rabbit();
            case 8:
                return new Tiger();
            case 9:
                return new Turtle();
            case 10:
                return new Vulture();
            case 11:
                return new Wolf();
        }
        return new Boar(); //Not gonna happen anyway...
    }

    /**
     * Adds a card to fill complete list with 30 cards
     * It guarantees no animal is generated more than 5 cards with heckNumberOfAnimals method
     */
    public void addToCompleteList() {
        Animal animal = randomAnimal();
        while (!checkNumberOfAnimals(animal))
            animal = randomAnimal();
        completeList.add(animal);
    }

    /**
     *
     * @param animal animal is selected to fill player's 10 cards
     */
    public void addToSelectedList(Animal animal) {
        selectedAnimals.add(animal);
    }

    /**
     *
     * @param animal animal that is generated randomly and we want to make sure is not generated more than 5 times for complete 30 cards
     * @return true: Isn't generated 5 or more than 5 cards | false: is generated 5 or more cards of this same animal
     */
    public boolean checkNumberOfAnimals(Animal animal) {
        Map<String, Integer> animalCount = new HashMap<>();
        for (Animal a : completeList) {
            String className = a.getClass().getName();
            Integer count = animalCount.get(className);
            if (count == null) {
                // First time we've seen this type of Animal
                animalCount.put(className, 1);
            } else {
                // We've seen this type of Animal at least once
                animalCount.put(className, count + 1);
            }
        }

        if (animalCount.containsKey(animal.getClass().getName())) {

            if (animalCount.get(animal.getClass().getName()) >= 5)
                return false;
        }
        return true;
    }

    /**
     *
     * @param damage The damage that attacker wants to deal
     * @param attackerIndex attacker's index in the selected cards of player
     * @return true: Has enough SP for attack | false: doesn't have enough SP for attack
     */
    public boolean checkSPforAttack(int damage, int attackerIndex) {
        if (selectedAnimals.get(attackerIndex).getSP() < damage)
            return false;
        return true;
    }

    /**
     * Prints selected cards of the player
     */
    public void printSelectedCards() {
        int i = 0;
        for (Animal animal : selectedAnimals) {
            System.out.println("[" + i + "] " + animal.toString());
            i++;
        }
    }

    /**
     * adds a card from selection to war party
     * @param index animal's index in the selection
     * @param attackType Attack type (Primary or secondary)
     * @return true: added successfully | false: cannot be added
     */
    public boolean addToWarParty(int index, String attackType) {//true: added successfully | false: cannot be added
        if (warParty.size() == 0) {
            warParty.add(selectedAnimals.get(index));
            selectedAnimals.remove(index);
            return true;
        } else {
            if (canBeAddedtoWarParty(selectedAnimals.get(index), attackType)) {
                warParty.add(selectedAnimals.get(index));
                selectedAnimals.remove(index);
                return true;
            }
            return false;
        }
    }

    /**
     * Disbands party and returns party members to selection collection
     * @return true: party disbanded successfully | false: party cannot be disbanded (normally not gonna happen...)
     */
    public boolean disbandParty() {
        if (warParty.size() == 0)
            return false;
        else {
            for (int i = 0; i < warParty.size(); i++) {
                selectedAnimals.add(warParty.get(i));
            }
            warParty.clear();
        }
        return true;
    }

    /**
     * Selects 10 random cards for the A.I player
     */
    public void selectRandomCardsForSelection() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int a = random.nextInt(completeList.size());
            selectedAnimals.add(completeList.get(a));
            completeList.remove(a);
        }
    }

    /**
     * Tells that whether selected animal can be added to war party or not
     * @param animal animal we want to add to the war party
     * @param partyLeaderAttackType party leader's attack type (i.e. bite? wound? kill? ...)
     * @return ture: selected animal can be added to war party | false: selected animal can't be added to war party because it doesn't have an attack type same as party leader
     */
    public boolean canBeAddedtoWarParty(Animal animal, String partyLeaderAttackType) {
        if (!animal.getAttack1().equals(partyLeaderAttackType) && !animal.getAttack2().equals(partyLeaderAttackType))
            return false;
        else {
            return true;
        }
    }

    /**
     * Generates a random index from player's selection
     * @return a random index from player's selection
     */
    public int randomLeaderIndex() {
        Random random = new Random();
        int a = random.nextInt(selectedAnimals.size());
        return a;
    }

    /**
     * Returns the index of animal in A.I's selection for restoring SP
     * @return random index of animal in A.I's selection for restoring SP
     */
    public int restoreSPforAI() {
        for (int i = 0 ; i<selectedAnimals.size()-1 ; i++) {
            Animal animal = selectedAnimals.get(i);
            if(animal.getSP()<animal.getFullSP())
                return i;
        }
        return randomLeaderIndex();
    }

    /**
     * prints player's war party
     */
    public void showWarParty() {
        for (int i = 0; i < warParty.size(); i++)
            System.out.println("[" + i + "] " + warParty.get(i).toString());
    }


    /* ****** Getter and Setter Methods ****** Start */
    public ArrayList<Animal> getCompleteList() {
        return completeList;
    }

    public ArrayList<Animal> getSelectedAnimals() {
        return selectedAnimals;
    }

    public ArrayList<Animal> getWarParty() {
        return warParty;
    }
    /* ****** Getter and Setter Methods ****** End */
}
