import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        TransitSystem.stations.add(new SubwayStation("Spadina", 1));
        TransitSystem.stations.add(new SubwayStation("St.George", 2));
        TransitSystem.stations.add(new SubwayStation("Museum", 3));
        TransitSystem.stations.add(new SubwayStation("Queen'sPark", 4));
        TransitSystem.stations.add(new SubwayStation("St.Patrick", 5));
        TransitSystem.stations.add(new SubwayStation("Osgoode", 6));
        TransitSystem.stations.add(new SubwayStation("St.Andrew", 7));
        TransitSystem.stations.add(new SubwayStation("Union", 8));
        TransitSystem.stations.add(new SubwayStation("King", 9));
        TransitSystem.stations.add(new SubwayStation("Queen", 10));
        TransitSystem.stations.add(new SubwayStation("Dundas", 11));
        TransitSystem.stations.add(new SubwayStation("College", 12));
        TransitSystem.stations.add(new SubwayStation("Wellesley", 13));
        TransitSystem.stations.add(new SubwayStation("Bloor-Yonge", 14));
        TransitSystem.stations.add(new SubwayStation("Sheppard-Yonge", 15));
        TransitSystem.stations.add(new SubwayStation("Finch", 16));
        TransitSystem.stops.add(new BusStop("Finch", 100));
        TransitSystem.stops.add(new BusStop("Steeles", 101));
        TransitSystem.stops.add(new BusStop("Bayview", 102));
        TransitSystem.stops.add(new BusStop("DonMills", 103));
        TransitSystem.stops.add(new BusStop("Woodbine", 104));
        TransitSystem.stops.add(new BusStop("VictoriaPark", 105));

        Path ppp = Paths.get("testcase.txt");
        ArrayList<String> command = new ArrayList<>();
        try (BufferedReader fileInput = Files.newBufferedReader(ppp)) {
            String line = fileInput.readLine();
            while (line != null) {
                command.add(line);
                line = fileInput.readLine();
            }
        }

        String oneLine;
        String[] sep;
        CardAccountManager caManager = new CardAccountManager();
        CardsManager cManager = new CardsManager();
        TripManager tManager = new TripManager();
        tManager.syncCardManager(cManager);
        ArrayList<AdminUserAccount> AdminUserAccountList = new ArrayList<>();

        int i = 0;
        while (i != command.size()) {
            oneLine = command.get(i);
            sep = oneLine.split("\\s");

            switch (sep[0]) {

                case "AddMoney":
                    int cardId6 = Integer.parseInt(sep[1]);
                    int amount = Integer.parseInt(sep[3]);
                    if (caManager.findUserByName(sep[2]) != null){
                        CardUserAccount cua = caManager.findUserByName(sep[2]);
                        if (cua.getCardCollection().contains(cManager.findCard(cardId6))){
                            caManager.findUserByName(sep[2]).addMoney(cardId6, amount);
                            System.out.println("Account of " + sep[2] + " has just added $" +
                            amount +" to card " + cardId6);
                        } else { System.out.println("The cardId that you entered: " + sep[1] + " does not exists in " +
                                "your account.");
                        }
                    } else {System.out.println("The user name that you entered: " + sep[2] + " does not match any" +
                            " account in the system.");
                    }
                    break;

                case "RegisterNewAccount": // create new account
                    caManager.addUser(new CardUserAccount(sep[1], sep[2]));
                    System.out.println("Registered new account name: " + sep[1] + " Email: " + sep[2]);
                    break;

                case "ViewAccountDetail":
                    if (caManager.findUserByName(sep[1]) == null){
                        System.out.println("The userName you entered: " + sep[1] +
                                " does not match any account in the system. " +
                                "You cannot view account detail of an account that does not exist");
                    }
                    else{System.out.println(caManager.findUserByName(sep[1]).toString());}
                    break;

                case "PrintDailyReport":
                    int date = Integer.parseInt(sep[1]);
                    AdminUserAccountList.get(0).dailyReport(date);
                    break;

                case "RegisterAdminUserAccount":
                    AdminUserAccount allKnownGod = new AdminUserAccount(sep[1], sep[2]);
                    allKnownGod.syncAllFinishedTrip(tManager.getFinishedTrips());
                    allKnownGod.syncAllUnfinishedTrip(tManager.getUnfinishedTrips());
                    AdminUserAccountList.add(allKnownGod);
                    break;

                case "ViewRecentThreeTrip":
                    String email = sep[2];
                    if (caManager.findUserByEmail(sep[2]) == null){
                        System.out.println("You cannot view recent three trip of an account that does not exist.");
                    }else{
                    ArrayList<ArrayList<Trip>> tripList = caManager.findUserByEmail(email).getRecentThreeTrips();
                    if(tripList.size() == 0){
                        System.out.println("There is no recent trip on this account");
                    }else if(tripList.size() == 1){
                        System.out.println("There is only one recent trip:"+ "\n" + tManager.toString(tripList.get(0)));
                    }else if(tripList.size() == 2){
                        System.out.println("There are two recent trips:" + "\n"
                                + "1. " + tManager.toString(tripList.get(0)) + ";" + "\n"
                                + "2. " + tManager.toString(tripList.get(1)));
                    }else if(tripList.size() == 3){
                        System.out.println("There are three recent trips:" + "\n"
                                + "1. " + tManager.toString(tripList.get(0)) + ";" + "\n"
                                + "2. " + tManager.toString(tripList.get(1)) + ";" + "\n"
                                + "3. " + tManager.toString(tripList.get(2)));
                    }
                    }
                    break;

                case "ChangeAccountName": // change account name
                    String oldName = sep[1];
                    String newName = sep[2];
                    caManager.findUserByName(oldName).changeName(newName);
                    System.out.println("Changed account name from " + oldName + " to " + newName);
                    break;

                case "AddCard": // create a new card with given card ID and add to CardUserAccount and
                    // cardsManager
                    int cardId1 = Integer.parseInt(sep[1]);
                    cManager.addCard(new Card(cardId1));
                    caManager.findUserByName(sep[2]).addCard(cManager.findCard(cardId1));
                    System.out.println("Added Card CardId: " + sep[1] + " to Account of " + sep[2]);
                    break;

                case "RemoveCard": // remove a card with given card ID and user name from CardUserAccount
                    int cardId2 = Integer.parseInt(sep[1]);
                    caManager.findUserByName(sep[2]).removeCard(cardId2);
                    System.out.println("Removed Card " + sep[1] + " from Account of " + sep[2]);
                    break;

                case "SuspendCard": // suspend a card with given card ID and user name from CardUserAccount
                    int cardId3 = Integer.parseInt(sep[1]);
                    caManager.findUserByName(sep[2]).suspendCard(cardId3);
                    System.out.println("Suspended Card " + sep[1] + " in Account of " + sep[2]);
                    break;

                case "In":
                    int cardId4 = Integer.parseInt(sep[1]);
                    int date1 = Integer.parseInt(sep[2]);
                    int time1 = Integer.parseInt(sep[3]);
                    boolean checkBalance = cManager.validBalance(cardId4);
                    boolean checkSuspend = cManager.isSuspend(cardId4);
                    if (!checkBalance) {
                        System.out.println(
                                "Card"
                                        + sep[1]
                                        + "have insufficient balance, "
                                        + "please add balance before tap in");
                    } else if (checkSuspend) {
                        System.out.println("Card" + sep[1] + "is Suspended, you cannot tap in");
                    } else {
                        if (sep[4].equals("Subway")) {
                            Trip newTrip = new Trip(cardId4, time1, date1, TransitSystem.findSubLocation(sep[5]));
                            newTrip.changeTapInStatus();
                            tManager.addTrip(newTrip);
                        } else if(sep[4].equals("Bus")){
                            Trip newTrip = new Trip(cardId4, time1, date1, TransitSystem.findBusLocation(sep[5]));
                            newTrip.changeTapInStatus();
                            tManager.addTrip(newTrip);
                        }
                    }
                    break;

                case "Out":
                    int cardId5 = Integer.parseInt(sep[1]);
                    int date2 = Integer.parseInt(sep[2]);
                    int time2 = Integer.parseInt(sep[3]);
                    if (sep[4].equals("Subway")) {
                        Trip newTrip = new Trip(cardId5, time2, date2, TransitSystem.findSubLocation(sep[5]));
                        newTrip.changeTapOutStatus();
                        tManager.addTrip(newTrip);
                    } else if(sep[4].equals("Bus")){
                        Trip newTrip = new Trip(cardId5, time2, date2, TransitSystem.findBusLocation(sep[5]));
                        newTrip.changeTapOutStatus();
                        tManager.addTrip(newTrip);
                    }
                    break;
            }

            i++;
        }
    }
}