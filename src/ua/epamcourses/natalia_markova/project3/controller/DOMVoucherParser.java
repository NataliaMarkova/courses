package ua.epamcourses.natalia_markova.project3.controller;

import org.w3c.dom.*;
import ua.epamcourses.natalia_markova.project3.controller.VoucherXMLParser;
import ua.epamcourses.natalia_markova.project3.model.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yack on 02.06.2016.
 */
public class DOMVoucherParser implements VoucherXMLParser {

    public DOMVoucherParser() {
        super();
    }

    @Override
    public Set<TouristVoucher> parse(String xmlFileName) throws Exception {

        Set<TouristVoucher> vouchers =  new HashSet<>();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new File(xmlFileName));
        doc.getDocumentElement().normalize();
        NodeList nodes = doc.getElementsByTagName("voucher");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            Element element = (Element) node;

            int id = Integer.parseInt(element.getAttribute("id"));
            TouristVoucher voucher = new TouristVoucher(id);
            vouchers.add(voucher);

            VoucherType voucherType = VoucherType.getType(element.getElementsByTagName("Type").item(0).getTextContent());
            if (voucherType != null) {
                voucher.setType(voucherType);
            }

            Country country = new Country(element.getElementsByTagName("Country").item(0).getTextContent());

            Element el = (Element) element.getElementsByTagName("Duration").item(0);
            int days = Integer.parseInt(el.getAttribute("days"));
            int nights = Integer.parseInt(el.getAttribute("nights"));
            voucher.setDays(days);
            voucher.setNights(nights);

            TransportType transportType = TransportType.getType(element.getElementsByTagName("Transport").item(0).getTextContent());
            if (transportType != null) {
                voucher.setTransportType(transportType);
            }

            el = (Element) element.getElementsByTagName("Hotel").item(0);
            String name = el.getAttribute("name");
            String type = el.getAttribute("type");
            Hotel hotel = new Hotel(name, country);
            if (type != null) {
                hotel.setType(type);
            }

            Node nd = element.getElementsByTagName("Room").item(0);
            el = (Element) nd;
            NutritionType nutrition = NutritionType.valueOf(el.getAttribute("nutrition"));
            type = el.getAttribute("type");
            Room room = new Room(hotel, type);
            voucher.setRoom(room);
            if (nutrition != null) {
                room.setNutrition(nutrition);
            }

            NodeList childNodes = ((Element) nd).getElementsByTagName("Facility");
            for (int j = 0; j < childNodes.getLength(); j++) {
                Node fn = childNodes.item(j);
                room.addFacility(new Facility(fn.getTextContent()));
            }

            el = (Element) element.getElementsByTagName("Cost").item(0);
            double price = Double.parseDouble(el.getAttribute("total"));
            voucher.setPrice(price);

            childNodes = el.getElementsByTagName("Includes");
            for (int j = 0; j < childNodes.getLength(); j++) {
                Node fn = childNodes.item(j);
                voucher.addIncludes(fn.getTextContent());
            }
        }

        return vouchers;

    }
}
