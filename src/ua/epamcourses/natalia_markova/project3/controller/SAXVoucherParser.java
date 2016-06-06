package ua.epamcourses.natalia_markova.project3.controller;

import org.xml.sax.SAXException;
import ua.epamcourses.natalia_markova.project3.model.TouristVoucher;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Created by natalia_markova on 02.06.2016.
 */
public class SAXVoucherParser implements VoucherXMLParser {
    @Override
    public Set<TouristVoucher> parse(String xmlFileName) throws ParserConfigurationException, SAXException, IOException {

        File inputFile = new File(xmlFileName);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        SAXVoucherParserHandler parserHandler = new SAXVoucherParserHandler();
        saxParser.parse(inputFile, parserHandler);

        return parserHandler.getVouchers();
    }
}
