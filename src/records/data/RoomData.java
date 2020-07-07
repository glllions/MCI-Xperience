package records.data;


import records.Building;
import records.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomData {

    private static List<Room> rooms = new ArrayList();

    public static List<Room> getRooms() {
        return rooms;
    }
}