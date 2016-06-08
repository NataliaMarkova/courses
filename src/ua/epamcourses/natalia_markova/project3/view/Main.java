package ua.epamcourses.natalia_markova.project3.view;

import ua.epamcourses.natalia_markova.project3.controller.*;
import ua.epamcourses.natalia_markova.project3.model.TouristVoucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by natalia_markova on 02.06.2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        String dir = System.getProperty("user.dir");
        String xmlFileName = dir + "\\tourist_voucher.xml";
        String xsdFileName = dir + "\\tourist_voucher.xsd";
//        String xslFileName = dir + "\\tourist_voucher.xsl";
        String xslFileName = dir + "\\tourist_voucher_table.xsl";
        String resultFileName = dir + "\\tourist_voucher.html";

        XMLValidator validator = new XMLValidator();
        validator.validate(xmlFileName, xsdFileName);
        System.out.println("Validation: OK");
        System.out.println();

        List<VoucherXMLParser> parsers = new ArrayList<>();
        parsers.add(new SAXVoucherParser());
        parsers.add(new DOMVoucherParser());
        parsers.add(new StAXVoucherParser());

        for (VoucherXMLParser parser : parsers) {
            System.out.println(parser.getClass().getSimpleName());
            Set<TouristVoucher> vouchers = parser.parse(xmlFileName);
            for (TouristVoucher voucher : vouchers) {
                System.out.println(voucher);
            }
            System.out.println();
        }

        XMLTransformer transformer = new XMLTransformer();
        transformer.transform(xmlFileName, xslFileName, resultFileName);
        System.out.println("Transformation: OK");
    }
}
