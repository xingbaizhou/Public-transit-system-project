import java.util.ArrayList;

public class CardAccountManager {

    /**
     * ArrayList of CardUserAccount
     */
    private ArrayList<CardUserAccount> allUserList;

    /**
     * constructor of class
     */
    CardAccountManager() {
        allUserList = new ArrayList<>();
    }

    void addUser(CardUserAccount user) {
        if (!allUserList.contains(user)) {
            allUserList.add(user);
        }
    }

    /**
     * return the CardUserAccount by name
     *
     * @param name the Username of the CardUserAccount
     * @return a CardUserAccount instance
     */
    CardUserAccount findUserByName(String name) {
        int i = 0;
        while (i < allUserList.size()) {
            if (allUserList.get(i).getName().equals(name)) {
                return allUserList.get(i);
            }
            i++;
        }
        return null;
    }

    /**
     * return the CardUserAccount by email
     *
     * @param email hte Email of the CardUserAccount
     * @return a CardUserAccount
     */
    CardUserAccount findUserByEmail(String email) {
        int i = 0;
        while (i < allUserList.size()) {
            if (allUserList.get(i).getEmail().equals(email)) {
                return allUserList.get(i);
            }
            i++;
        }
        return null;
    }

    /**
     * a getter of the local variable getAllUserList
     *
     * @return the local variable getAllUserList
     */
    public ArrayList<CardUserAccount> getAllUserList() {
        return allUserList;
    }


}
