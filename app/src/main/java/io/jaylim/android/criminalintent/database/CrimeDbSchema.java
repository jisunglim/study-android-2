package io.jaylim.android.criminalintent.database;

/**
 * Created by jaylim on 10/18/2016.
 *
 */

public class CrimeDbSchema {
    public static final class CrimeTable {
        public static final String NAME = "crime";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }


}
