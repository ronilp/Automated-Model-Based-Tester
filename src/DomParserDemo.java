import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.Validator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
Document.getDocumentElement() 	- Returns the root element of the document.
Node.getFirstChild() 		- Returns the first child of a given Node.
Node.getLastChild() 		- Returns the last child of a given Node.
Node.getNextSibling() 		- These methods return the next sibling of a given Node.
Node.getPreviousSibling() 	- These methods return the previous sibling of a given Node.
Node.getAttribute(attrName) 	- For a given Node, returns the attribute with the requested name.
*/

public class DomParserDemo {
	public static void main(String[] args) {

		try {
			File inputFile = new File("/home/shikhar/workspace/Software_testing/src/input.txt");

		// Create a Document Builder
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		// Create a Document from a file or stream
			Document doc = dBuilder.parse(inputFile);

		// Extract root element
			Element root = doc.getDocumentElement();

		// Get attribute of any element
			System.out.println("Root id :"+ root.getAttribute("id"));

		// Getting list of attributes
			NamedNodeMap attr = root.getAttributes();
			System.out.println(attr.getNamedItem("color"));


		//
			NodeList nList2 = root.getChildNodes();
			for(int i=0; i<nList2.getLength(); i++){
				Node node = nList2.item(i);
				System.out.println(node.getNodeName());
			}

			System.out.println(nList2.getLength());
			// Get all elements by Tag name
			NodeList nList = doc.getElementsByTagName("student");

			for (int i = 0; i < nList.getLength(); i++) {
				// Get ith node from nodelist
				Node nNode = nList.item(i);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println("Student roll no : " + eElement.getAttribute("rollno"));
					System.out.println("First Name : " + eElement.getElementsByTagName("firstname") .item(0).getTextContent());
					System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0) .getTextContent());
					System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0) .getTextContent());
					System.out.println("Marks : " + eElement.getElementsByTagName("marks").item(0) .getTextContent());
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}