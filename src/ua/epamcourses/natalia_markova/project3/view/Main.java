package ua.epamcourses.natalia_markova.project3.view;

import org.xml.sax.SAXException;
import ua.epamcourses.natalia_markova.project3.controller.sax.SAXVoucherParser;
import ua.epamcourses.natalia_markova.project3.controller.VoucherXMLOperator;
import ua.epamcourses.natalia_markova.project3.model.TouristVoucher;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by natalia_markova on 02.06.2016.
 */
public class Main {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        String dir = System.getProperty("user.dir");
        String xmlFileName = dir + "\\tourist_voucher.xml";
        String xsdFileName = dir + "\\tourist_voucher.xsd";

        List<VoucherXMLOperator> operators = new ArrayList<>();
        operators.add(new SAXVoucherParser());

        for (VoucherXMLOperator operator : operators) {

            System.out.println(operator.getClass().getSimpleName());
            Set<TouristVoucher> vouchers = new HashSet<>();

            operator.validate(xmlFileName, xsdFileName);
            System.out.println("Validation: OK");
            vouchers = operator.parse(xmlFileName);

            for (TouristVoucher voucher : vouchers) {
                System.out.println(voucher);
            }

            System.out.println();

        }

    }
}
