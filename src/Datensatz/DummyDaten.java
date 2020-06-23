package Datensatz;

import java.util.ArrayList;
import java.util.Random;


/**
     * Klasse mit der Verantwortlichkeit DummyDaten durch Zufallsgenerierung bereitzustellen.
     */
    public class DummyDaten {
        ArrayList<String> forenames = new ArrayList<>();
        ArrayList<String> surenames = new ArrayList<>();


        public DummyDaten(){
            initRandomNameData();
        }

        /**
         * Initalisieren der Dummy Daten für eine Zufallsgenerierung von Namen
         */
        private void initRandomNameData() {
            forenames.add("Mohammed");
            forenames.add("Peter");
            forenames.add("Alan");
            forenames.add("Edsger");

            surenames.add("Lee");
            surenames.add("Pan");
            surenames.add("Turing");
            surenames.add("Dijkstra");
        }


        /**
         * Anlegen eines Zufallsnamens
         *
         * @return ein Zufallsname als {@link Name}
         */
        public Name getMeSomeRandomNames(){
            Random random = new Random();
            String randomForeName = forenames.get(random.nextInt(forenames.size()));
            String randomSurename = surenames.get(random.nextInt(surenames.size()));

            return new Name(randomForeName, randomSurename);
        }


        //TODO: Put here some more Random Generation Functions
    }





    /**
     * Wrapper Klasse für Namen bestehend aus Vor- und Nachname.
     */
    class Name{
        private String forename = "Ive got no forename";
        private String surename = "Ive got no surename";

        public Name(String forename, String surename){
            this.forename = forename;
            this.surename = surename;
        }

        public String getForename() {
            return forename;
        }

        public String getSurename() {
            return surename;
        }
    }



