package ua.epamcourses.natalia_markova.project3.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by natalia_markova on 01.06.2016.
 */
public enum VoucherType {
    LEISURE {
        @Override
        public String toString() {
            return "Leisure";
        }
    }, TREATMENT {
        @Override
        public String toString() {
            return "Treatment";
        }
    }, PILGRIMAGE {
        @Override
        public String toString() {
            return "Pilgrimage";
        }
    }, EXCURSION {
        @Override
        public String toString() {
            return "Excursion";
        }
    }, SHOP_TOUR {
        @Override
        public String toString() {
            return "Shop tour";
        }
    };

    private static Set<VoucherType> valuesSet = new HashSet<VoucherType>() {{
        add(LEISURE);
        add(TREATMENT);
        add(PILGRIMAGE);
        add(EXCURSION);
        add(SHOP_TOUR);
        }
    };

    public static VoucherType getType(String value) {

        for (VoucherType type: valuesSet) {
            if (type.toString().toLowerCase().equals(value.trim().toLowerCase())) {
                return type;
            }
        }
        return null;
    }

}
