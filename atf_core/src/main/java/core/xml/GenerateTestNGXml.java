package core.xml;

import org.testng.annotations.Test;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by olehk on 10/04/2017.
 */
public class GenerateTestNGXml {


    private static Map<String, String> getAllEnvByPrefix(String prefix){
        Map<String, String> environmentVariables = System.getenv();
        Map<String, String> out = new HashMap<>();
        for (String envName : environmentVariables.keySet()) {
            if (envName.startsWith(prefix)){
                out.put(envName, environmentVariables.get(envName));
            }
        }
        return out;
    }

    private static void setAttribute(Document doc, Element elem, String key, String value){
        Attr attr = doc.createAttribute(key);
        attr.setValue(value);
        elem.setAttributeNode(attr);
    }

    private static void addParameter(Document doc, Element elem, String name, String value){
        Element param = doc.createElement("parameter");
        setAttribute(doc, param, "name", name);
        setAttribute(doc, param, "value", value);
        elem.appendChild(param);
    }

    private static void generateXML() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root element
        Document doc = docBuilder.newDocument();
        Element suite = doc.createElement("suite");
        doc.appendChild(suite);
        Map<String, String> suiteAttributes = new HashMap<>();
        suiteAttributes.put("name", "Test suite");
        suiteAttributes.put("verbose", "1");
        suiteAttributes.put("preserve-order", "false");
        suiteAttributes.forEach((k,v)->setAttribute(doc, suite, k, v));

        // add parameters
        getAllEnvByPrefix("GLB_").forEach((k,v)->addParameter(doc, suite, k, v));

        // add test section
        Element test = doc.createElement("test");
        suite.appendChild(test);
        setAttribute(doc, test, "name", "Test");

        // add groups section
        Element groups = doc.createElement("groups");
        test.appendChild(groups);
        // add run section
        Element run = doc.createElement("run");
        groups.appendChild(run);
        String groupForExecution = System.getenv("GROUP_TO_TEST")!=null?System.getenv("GROUP_TO_TEST"):"Smoke";
        String[] groupsToTest = groupForExecution.split(",");
        for (String groupToTest : groupsToTest) {
            // add include sections
            Element include = doc.createElement("include");
            run.appendChild(include);
            setAttribute(doc, include, "name", groupToTest.trim());
        }

        // add packages section
        Element packages = doc.createElement("packages");
        test.appendChild(packages);
        String packagesForExecution = System.getenv("PACKAGES_TO_TEST")!=null?System.getenv("PACKAGES_TO_TEST"):".*";
        String[] packagesToTest = packagesForExecution.split(",");
        // add package
        for (String packageToTest : packagesToTest) {
            // add include sections
            Element pack = doc.createElement("package");
            packages.appendChild(pack);
            setAttribute(doc, pack, "name", packageToTest.trim());
        }

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://testng.org/testng-1.0.dtd");
        DOMSource source = new DOMSource(doc);
        File file = new File("TestNG.xml").getAbsoluteFile();
        StreamResult result = new StreamResult(file);

        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);
        transformer.transform(source, result);
    }

    @Test
    public void testCase() throws TransformerException, ParserConfigurationException {
        generateXML();
    }

    public static void main(String[] args) throws TransformerException, ParserConfigurationException {
        generateXML();
    }

}
