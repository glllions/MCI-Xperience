package records;

import java.util.HashMap;

public class Database {

    /**
     * Anlegen von Dummy Daten für Räume.
     *
     * @param amountOfDummys Anzahl der Raum Dummys die erzeugt werden sollen
     * @param myDummyDataGenerator Eine Klasse mit der Verantwortlichkeit DummyDaten zu generieren
     * @param myDummyUserData HashMap aller Dummy User im System
     * @return HashMap aller Dummy Räume Key= Raumnummer, Value = Room Instanz
     */
    public HashMap<Integer, Room> createMyDummyRoomData(int amountOfDummys, DataGenerator myDummyDataGenerator, User myDummyUserData){
        //Dummy Database
        HashMap<Integer, Room> myRoomDummyDataBase = new HashMap<>();
        //Anzahl der Dummys die erzeugt werden sollen
        int amountOfRoomDummys = amountOfDummys;

        //N mal Räume anlegen
        for(int i = 0; i < amountOfRoomDummys; i++){

            //Dummy Daten generieren
            int randomRoomNumber = myDummyDataGenerator.createRandomRoomNumber();
            String randomRoomName = myDummyDataGenerator.createRandomRoomName();
            User randomOwner = myDummyDataGenerator.getMeSomeRandomDozent();

           // Random Room der Dummy-Datenbank hinzufügen.
            Room randomRoom = new Room(randomRoomNumber, randomRoomName, randomOwner);
            myRoomDummyDataBase.put(randomRoomNumber, randomRoom);
        }
        return myRoomDummyDataBase;
    }
}
