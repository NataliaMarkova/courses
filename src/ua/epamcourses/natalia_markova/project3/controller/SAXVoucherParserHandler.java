package ua.epamcourses.natalia_markova.project3.controller;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ua.epamcourses.natalia_markova.project3.model.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by natalia_markova on 01.06.2016.
 */
public class SAXVoucherParserHandler extends DefaultHandler {

    private Set<TouristVoucher> vouchers;

    private TouristVoucher voucher;
    private Hotel hotel;
    private Room room;
    private Country country;
    private String value;


    public SAXVoucherParserHandler() {
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
        if (qName.equalsIgnoreCase("vouchers")) {
            vouchers = new HashSet<>();
        } else if (qName.equalsIgnoreCase("voucher")) {
            int id = Integer.parseInt(attributes.getValue("id"));
            voucher = new TouristVoucher(id);
            hotel = null;
            room = null;
            country = null;
            value = null;
        } else if (qName.equalsIgnoreCase("duration")) {
            int days = Integer.parseInt(attributes.getValue("days"));
            int nights = Integer.parseInt(attributes.getValue("nights"));
            voucher.setDays(days);
            voucher.setNights(nights);
        } else if (qName.equalsIgnoreCase("hotel")) {
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");
            hotel = new Hotel(name, country);
            if (type != null) {
                hotel.setType(type);
            }
        } else if (qName.equalsIgnoreCase("vch:room")) {
            String type = attributes.getValue("type");
            String nutrition = attributes.getValue("nutrition");
            NutritionType nutritionType =  NutritionType.valueOf(nutrition);
            room = new Room(hotel, type);
            if (nutritionType != null) {
                room.setNutrition(nutritionType);
            }
            voucher.setRoom(room);
        } else if (qName.equalsIgnoreCase("cost")) {
            double price = Double.parseDouble(attributes.getValue("total"));
            voucher.setPrice(price);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equalsIgnoreCase("voucher")) {
            vouchers.add(voucher);
        } else if (qName.equalsIgnoreCase("type")) {
            VoucherType type = VoucherType.getType(value);
            if (type != null) {
                voucher.setType(type);
            }
        } else if (qName.equalsIgnoreCase("country")) {
            country = new Country(value);
        } else if (qName.equalsIgnoreCase("transport")) {
            TransportType transportType = TransportType.getType(value);
            if (transportType != null) {
                voucher.setTransportType(transportType);
            }
        } else if (qName.equalsIgnoreCase("vch:facility")) {
            room.addFacility(new Facility(value));
        } else if (qName.equalsIgnoreCase("includes")) {
            voucher.addIncludes(value);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        value = new String(ch, start, length);
    }
}
