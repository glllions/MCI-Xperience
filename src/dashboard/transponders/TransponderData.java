package dashboard.transponders;

import records.Transponder;
import records.User;

import java.util.Arrays;
import java.util.List;

public class TransponderData {

    //private static Transponder transponder1 = new Transponder();


    private static List<Transponder> transponders = Arrays.asList(

            new Transponder("TXXXA", true),
            new Transponder("TXXXB", false),
            new Transponder("TXXXC", false),
            new Transponder("TXXXD", true),
            new Transponder("TXXXE", true),
            new Transponder("TXXXX", true),
            new Transponder("TXXXY", false),
            new Transponder("TXXXZ", true)

    );


}