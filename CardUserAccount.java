import java.util.ArrayList;

public class CardUserAccount {
    /**
     * user name of the account
     */
    private String name;
    /**
     * email of the account
     */
    private String email;
    /**
     * a ArrayList of Card, all the card of a account is stored in this ArrayList
     */
    private ArrayList<Card> CardCollection;

    /**
     * Constructor of this class
     *
     * @param name  name of the account
     * @param email email of the account
     */
    CardUserAccount(String name, String email) {
        this.name = name;
        this.email = email;
        this.CardCollection = new ArrayList<>();
    }

    /**
     * getter of local variable name
     *
     * @return the local variable name
     */
    public String getName() {
        return name;
    }

    /**
     * change the name of the account
     *
     * @param newName the new name you want
     */
    void changeName(String newName) {
        this.name = newName;
    }

    /**
     * getter of local variable email
     *
     * @return the local variable email
     */
    String getEmail() {
        return email;
    }

    /**
     * getter of local variable CardCollection
     *
     * @return the local variable CardCollection
     */
    ArrayList<Card> getCardCollection() {
        return CardCollection;
    }

    /**
     * add a Card to the ArrayList CardCollection
     *
     * @param aCard the Card you want to add to the ArrayList
     */
    void addCard(Card aCard) {

        if (!CardCollection.contains(aCard)) {
            CardCollection.add(aCard);
        }
    }

    /**
     * add the amount of money to a Card with given cardId
     *
     * @param cardId the card ID of the card you want to add the money
     * @param amount the amount you want to add
     */
    void addMoney(int cardId, int amount) {
        int i = 0;
        while (i < CardCollection.size()) {
            if (CardCollection.get(i).getCardId() == (cardId)) {
                CardCollection.get(i).addMoney(amount);
            }
            i++;
        }
    }

    /**
     * Suspend a card with given card ID
     *
     * @param cardId the Card ID of the card you want to suspend
     */
    void suspendCard(int cardId) {
        int i = 0;
        while (i < CardCollection.size()) {
            if (CardCollection.get(i).getCardId() == (cardId)) {
                CardCollection.get(i).suspendCard();
            }
            i++;
        }
    }


    /**
     * remove a card from the card List in the account
     *
     * @param cardId the cardId of the card you want to remove
     */
    void removeCard(int cardId) {
        int i = 0;
        while (i < CardCollection.size()) {
            if (CardCollection.get(i).getCardId() == (cardId)) {
                CardCollection.remove(i);
            }
            i++;
        }
    }

    /**
     * equal method of CardUserAccount
     *
     * @param other the other CardUserAccount instance to compare
     * @return true of both email and name is equal, vice versa
     */
    public boolean equals(CardUserAccount other) {
        return this.email.equals(other.getEmail()) && this.name.equals(other.getName());
    }

    /**
     * the toString method CardUserAccount
     *
     * @return the string representation of CardUserAccount
     */
    public String toString() {
        int cardNumber = CardCollection.size();
        StringBuilder cardDetail = new StringBuilder();
        if (CardCollection.size() != 0) {
            for (Card card1 : CardCollection) {
                cardDetail.append(card1.toString());
                cardDetail.append('\n');
            }
        }

        return "You account name is "
                + name
                + ". Your account email is "
                + email
                + ". You have total "
                + cardNumber
                + " Cards, here is your Card detail:"
                + "\n"
                + cardDetail;
    }

    /**
     * get the most recent three trips from the CardCollection list
     *
     * @return an ArrayList of ArrayList of trips (most recent three)
     */
    ArrayList<ArrayList<Trip>> getRecentThreeTrips() {
        ArrayList<ArrayList<Trip>> temp = new ArrayList<>();
        ArrayList<ArrayList<Trip>> result = new ArrayList<>();
        ArrayList<ArrayList<Trip>> allFinishedTrips = new ArrayList<>();
        if (CardCollection.size() == 0) {
            return temp;
        }
        for (Card card : CardCollection) {
            allFinishedTrips.addAll(card.getFinishedTrip());
        }
        if (allFinishedTrips.size() > 0) {
            temp.add(allFinishedTrips.get(0));
        }
        for (ArrayList<Trip> tripList : allFinishedTrips) {
            int i = 0; // compare using the last trip inside a tripList
            int date = tripList.get(tripList.size() - 1).getDate();
            int time = tripList.get(tripList.size() - 1).getTime();
            int cardID = tripList.get(tripList.size() - 1).getCardID();

            while ((i < temp.size())
                    && date
                    < temp.get(i).get(temp.get(i).size() - 1).getDate()) { // stop when tripList's date is
                i++;
            }
            while (i < temp.size()
                    && date == temp.get(i).get(temp.get(i).size() - 1).getDate()
                    && time < temp.get(i).get(temp.get(i).size() - 1).getTime()) {
                i++;
            }
            while (i < temp.size()
                    && date == temp.get(i).get(temp.get(i).size() - 1).getDate()
                    && time == temp.get(i).get(temp.get(i).size() - 1).getTime()
                    && cardID > temp.get(i).get(temp.get(i).size() - 1).getCardID()) {
                i++;
            }
            if (!temp.contains(tripList)) {
                temp.add(i, tripList);
            }
        }
        if (temp.size() > 0) {
            result.add(temp.get(0));
            if (temp.size() > 1) {
                result.add(temp.get(1));
                if (temp.size() > 2) {
                    result.add(temp.get(2));
                }
            }
        }
        return result;
    }
}
