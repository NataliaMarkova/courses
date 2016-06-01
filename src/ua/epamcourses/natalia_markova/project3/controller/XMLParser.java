package ua.epamcourses.natalia_markova.project3.controller;

import ua.epamcourses.natalia_markova.project3.model.TouristVoucher;

import java.util.List;

/**
 * Created by natalia_markova on 31.05.2016.
 */
public interface XMLParser {
    List<TouristVoucher> parse(String fileName);
    boolean validate(String fileName);
}
