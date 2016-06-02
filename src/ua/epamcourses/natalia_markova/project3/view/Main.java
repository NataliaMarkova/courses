package ua.epamcourses.natalia_markova.project3.view;

import ua.epamcourses.natalia_markova.project3.controller.XMLOperator;

/**
 * Created by natalia_markova on 02.06.2016.
 */
public class Main {
    public static void main(String[] args) {

        String dir = System.getProperty("user.dir");
        String xmlFileName = "\\tourist_voucher.xml";
        String xsdFileName = "\\tourist_voucher.xsd";

        XMLOperator operator = new XMLOperator();
        System.out.println(operator.validate(dir + xmlFileName, dir + xsdFileName));

    }
}
