package core.xml;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by olehk on 13/04/2017.
 */
public class Reader {

    public static String read(String tag, String attributeName, String file) throws ParserConfigurationException, IOException, SAXException {
        String out = null;
        File fXmlFile = new File(file).getAbsoluteFile();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName(tag);
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element parameter = (Element) nNode;
                String parameterName = parameter.getAttribute("name");
                if (parameterName.equals(attributeName)){
                    out = parameter.getAttribute("value");
                    break;
                }
            }

        }
        return out;
    }

    public static String readParam(String paramName){
        try{
            return read("parameter", paramName, "TestNG.xml");
        }catch (Exception e){
            return null;
        }

    }

    @Test
    public void testReadXML(){
        Assert.assertNotNull(readParam("USERDOMAIN"));
    }

}
