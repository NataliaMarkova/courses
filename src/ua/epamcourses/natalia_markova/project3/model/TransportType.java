package ua.epamcourses.natalia_markova.project3.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by natalia_markova on 01.06.2016.
 */
public enum TransportType {
    AVIA {
        @Override
        public String toString() {
            return "Avia";
        }
    }, TRAIN {
        @Override
        public String toString() {
            return "Train";
        }
    }, BUS {
        @Override
        public String toString() {
            return "Bus";
        }
    }, SHIP {
        @Override
        public String toString() {
            return "Ship";
        }
    };

    private static Set<TransportType> valuesSet = new HashSet<TransportType>() {{
        add(AVIA);
        add(TRAIN);
        add(BUS);
        add(SHIP);
    }
    };

    public static TransportType getType(String value) {

        for (TransportType type: valuesSet) {
            if (type.toString().toLowerCase().equals(value.trim().toLowerCase())) {
                return type;
            }
        }
        return null;
    }
}
