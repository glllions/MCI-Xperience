package records.data;


import records.Building;
import records.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomData {

    private static List<Room> rooms = new ArrayList(Arrays.asList(

            new Room(511, Building.FERCHAU),
            new Room(400, Building.MENSA),
            new Room(401, Building.MENSA),
            new Room(1111, Building.TH),
            new Room(2111, Building.TH),
            new Room(1211, Building.TH),
            new Room(0211, Building.TH)

    ));

    public static List<Room> getRooms() {
        return rooms;
    }
}