package records.data;

import records.Transponder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransponderData {

    private static List<Transponder> transponders = new ArrayList();

    public static List<Transponder> getTransponders() {
        return transponders;
    }
}