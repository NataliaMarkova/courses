package ua.epamcourses.natalia_markova.classwork20.serialization;

import java.io.Serializable;

/**
 * Created by natalia_markova on 17.06.2016.
 */

class ToSerialize implements Serializable {
        public int i = 10;
        static transient int k;
}

public class SerializationExample {
}
