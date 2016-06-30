package ua.epamcourses.natalia_markova.classwork21;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by natalia_markova on 29.06.2016.
 */

@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
    int value(); // поля описываются как методы
}


@MyAnnotation(value =  30)
class A {

}

public class AnnotationExample {
    public static void main(String[] args) {
        Class cl = A.class;
        MyAnnotation an = (MyAnnotation) cl.getAnnotation(MyAnnotation.class);
        System.out.println(an.value());
    }
}
