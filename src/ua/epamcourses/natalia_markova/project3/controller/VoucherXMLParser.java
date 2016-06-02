package ua.epamcourses.natalia_markova.project3.controller;

import org.xml.sax.SAXException;
import ua.epamcourses.natalia_markova.project3.model.TouristVoucher;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Set;

/**
 * Created by natalia_markova on 31.05.2016.
 */
public interface VoucherXMLParser {
    Set<TouristVoucher> parse(String xmlFileName) throws ParserConfigurationException, SAXException, IOException;
}
