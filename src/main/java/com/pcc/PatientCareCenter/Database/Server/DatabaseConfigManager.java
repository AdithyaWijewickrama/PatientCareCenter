package com.pcc.PatientCareCenter.Database.Server;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DatabaseConfigManager {
    public static final String DB_FILE = "db_config.xml";

    public static void writeConfig(String url, String username, String password) {
        writeConfig(DB_FILE, url, username, password);
    }

    public static Map<String, String> readConfig() {
        return readConfig(DB_FILE);
    }

    public static void writeConfig(String filePath, String url, String username, String password) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("databaseConfig");
            doc.appendChild(rootElement);

            if (url != null)
                addElement(doc, rootElement, "url", url);

            addElement(doc, rootElement, "username", Objects.requireNonNullElse(username, "postgres"));

            addElement(doc, rootElement, "password", Objects.requireNonNullElse(password, "FEaXf2tDOcI9iSm6/yeHTg==:FMZkYFNNBwDLUf4q7hCjvw==:UHeNy/Ak6F4L7tdoj0vKWA=="));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            File xlm = new File(filePath);
            StreamResult result;
            boolean newFile = xlm.exists();
            if (!xlm.exists()) {
                newFile = xlm.createNewFile();
            }
            if (newFile) {
                result = new StreamResult(filePath);
                transformer.transform(source, result);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to write database config", e);
        }
    }

    public static Map<String, String> readConfig(String filePath) {
        try {
            File xmlFile = new File(filePath);
            if (xmlFile.exists()) {


                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();

                Map<String, String> config = new HashMap<>();
                NodeList nodes = doc.getDocumentElement().getChildNodes();

                for (int i = 0; i < nodes.getLength(); i++) {
                    if (nodes.item(i) instanceof Element element) {
                        config.put(element.getTagName(), element.getTextContent());
                    }
                }

                return config;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read database config", e);
        }
    }

    private static void addElement(Document doc, Element parent, String tagName, String value) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(value));
        parent.appendChild(element);
    }

    public static void main(String[] args) {
        String configFile = "db_config.xml";

        writeConfig(configFile,
                "jdbc:postgresql://localhost:5432/mydb",
                "admin",
                "secret123"
        );

        Map<String, String> config = readConfig(configFile);
        assert config != null;
        System.out.println("Database URL: " + config.get("url"));
        System.out.println("Username: " + config.get("username"));
        System.out.println("Password: " + config.get("password"));
    }
}