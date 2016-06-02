package ua.epamcourses.natalia_markova.project3.controller;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ua.epamcourses.natalia_markova.project3.model.Hotel;
import ua.epamcourses.natalia_markova.project3.model.Room;
import ua.epamcourses.natalia_markova.project3.model.TouristVoucher;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by natalia_markova on 01.06.2016.
 */
public class SAXParser extends DefaultHandler {

    private Set<Hotel> hotels = new HashSet<>();
    private Set<Room> rooms = new HashSet<>();
    private Set<TouristVoucher> vouchers = new HashSet<>();

    public SAXParser() {
        super();
    }

    public Set<TouristVoucher> getVouchers() {
        return vouchers;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
    }
}
