package ua.epamcourses.natalia_markova.project3.controller;

import ua.epamcourses.natalia_markova.project3.model.TouristVoucher;

import java.util.Set;

/**
 * Created by natalia_markova on 02.06.2016.
 */
public interface XMLGenerator {
    boolean generate(Set<TouristVoucher> vouchers, String xmlFileName);
}
