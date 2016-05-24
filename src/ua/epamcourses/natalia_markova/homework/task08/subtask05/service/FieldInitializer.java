package ua.epamcourses.natalia_markova.homework.task08.subtask05.service;

import ua.epamcourses.natalia_markova.homework.task08.subtask05.model.Field;
import ua.epamcourses.natalia_markova.homework.task08.subtask05.model.ShipInitializingException;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public interface FieldInitializer {
    Field initializeField() throws ShipInitializingException;
    Field initializeEmptyField();
}
