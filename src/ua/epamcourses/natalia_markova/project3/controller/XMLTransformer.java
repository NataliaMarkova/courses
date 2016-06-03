package ua.epamcourses.natalia_markova.project3.controller;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

/**
 * Created by natalia_markova on 02.06.2016.
 */
public class XMLTransformer {

    public void transform(String xmlFileName, String xslFileName, String resultFileName) throws Exception {
        StreamSource source = new StreamSource(xmlFileName);
        StreamSource styleSource = new StreamSource(xslFileName);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(styleSource);

        StreamResult result = new StreamResult(new File(resultFileName));
        transformer.transform(source, result);
    }

}
