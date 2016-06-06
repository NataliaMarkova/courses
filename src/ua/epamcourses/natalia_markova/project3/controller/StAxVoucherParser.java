package ua.epamcourses.natalia_markova.project3.controller;

import ua.epamcourses.natalia_markova.project3.controller.VoucherXMLParser;
import ua.epamcourses.natalia_markova.project3.model.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by natalia_markova on 02.06.2016.
 */
public class StAxVoucherParser implements VoucherXMLParser {
    @Override
    public Set<TouristVoucher> parse(String xmlFileName) throws Exception {

        Set<TouristVoucher> vouchers =  new HashSet<>();

        TouristVoucher voucher = null;
        Hotel hotel = null;
        Room room = null;
        Country country = null;
        String value = null;

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(xmlFileName));

        while(eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            switch (event.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    StartElement startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();
                    if (qName.equalsIgnoreCase("voucher")) {
                        Iterator<Attribute> attributes = startElement.getAttributes();
                        int id = Integer.parseInt(attributes.next().getValue());
                        voucher = new TouristVoucher(id);
                        vouchers.add(voucher);
                        hotel = null;
                        room = null;
                        country = null;
                        value = null;
                    } else if (qName.equalsIgnoreCase("duration")) {
                        int days = 0;
                        int nights = 0;
                        Iterator<Attribute> attributes = startElement.getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().getLocalPart().equals("day")) {
                                days = Integer.parseInt(attribute.getValue());
                            } else if (attribute.getName().getLocalPart().equals("nights")) {
                                nights = Integer.parseInt(attribute.getValue());
                            }
                        }
                        voucher.setDays(days);
                        voucher.setNights(nights);
                    } else if (qName.equalsIgnoreCase("hotel")) {
                        String name = "";
                        String type = "";
                        Iterator<Attribute> attributes = startElement.getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().getLocalPart().equals("name")) {
                                name = attribute.getValue();
                            } else if (attribute.getName().getLocalPart().equals("type")) {
                                type = attribute.getValue();
                            }
                        }
                        hotel = new Hotel(name, country);
                        if (type != null) {
                            hotel.setType(type);
                        }
                    } else if (qName.equalsIgnoreCase("room")) {
                        String type = "";
                        String nutrition = "";
                        Iterator<Attribute> attributes = startElement.getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().getLocalPart().equals("nutrition")) {
                                nutrition = attribute.getValue();
                            } else if (attribute.getName().getLocalPart().equals("type")) {
                                type = attribute.getValue();
                            }
                        }
                        NutritionType nutritionType =  NutritionType.valueOf(nutrition);
                        room = new Room(hotel, type);
                        if (nutritionType != null) {
                            room.setNutrition(nutritionType);
                        }
                        voucher.setRoom(room);
                    } else if (qName.equalsIgnoreCase("cost")) {
                        Iterator<Attribute> attributes = startElement.getAttributes();
                        double price = Double.parseDouble(attributes.next().getValue());
                        voucher.setPrice(price);
                    }
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    EndElement endElement = event.asEndElement();
                    qName = endElement.getName().getLocalPart();
                    if (qName.equalsIgnoreCase("type")) {
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
                    } else if (qName.equalsIgnoreCase("facility")) {
                        room.addFacility(new Facility(value));
                    } else if (qName.equalsIgnoreCase("includes")) {
                        voucher.addIncludes(value);
                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    value = event.asCharacters().getData();
                    break;

            }
        }
        return vouchers;
    }
}
