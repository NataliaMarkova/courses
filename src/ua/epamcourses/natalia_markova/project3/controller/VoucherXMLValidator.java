package ua.epamcourses.natalia_markova.project3.controller;

import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by natalia_markova on 02.06.2016.
 */
public interface VoucherXMLValidator {
    void validate(String xmlFileName, String xsdFileName) throws SAXException, IOException;
}
