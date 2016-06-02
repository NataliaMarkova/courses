package ua.epamcourses.natalia_markova.project3.controller;

import org.xml.sax.SAXException;
import ua.epamcourses.natalia_markova.project3.model.TouristVoucher;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Created by natalia_markova on 02.06.2016.
 */
public class XMLOperator implements XMLValidator, XMLGenerator {

    @Override
    public boolean validate(String xmlFileName, String xsdFileName) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdFileName));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlFileName)));
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }catch(SAXException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean generate(Set<TouristVoucher> vouchers, String xmlFileName) {
        return false;
    }
}
