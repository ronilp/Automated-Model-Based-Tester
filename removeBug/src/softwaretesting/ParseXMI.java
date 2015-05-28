package softwaretesting;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.util.*;
import java.util.*;

import javax.xml.*;
import javax.xml.transform.*;

public class ParseXMI {
	public ArrayList<SequenceElement> parse(File inputFile) throws ParserConfigurationException, SAXException, IOException {

//		File inputFile = file;

		/*Create a Document Builder*/
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		/*Create a Document from a file or stream*/
		Document doc = dBuilder.parse(inputFile);

		/*Extract root element*/	
		Element root = doc.getDocumentElement();

		Node xmiExtension = getChild(root, "xmi:Extension");
		Node umlPackage = getChild(root, "uml:Package");

		Node pkgElementCover = getChild(umlPackage, "packagedElement");
		Node pkgElement = getChild(pkgElementCover, "packagedElement");

		Node Diagrams = getChild(xmiExtension, "Diagrams");
		Node umlRootElement = getChild(Diagrams, "uml:RootElement");
		Node guiRoot = getChild(umlRootElement, "guiRootGuiDiagram");

		ArrayList<Lifeline> llist = getLifelines(pkgElement);
		ArrayList<Message> msgs = getMessages(pkgElement);

		ArrayList<Block> blocklist = getBlocks(pkgElement, guiRoot);

//		for (int i = 0; i < blocklist.size(); i++)
//			System.out.println(blocklist.get(i).pseudoCode);

//		for (int i = 0; i < llist.size(); i++) {
//			llist.get(i).display();
//			System.out.println();
//		}

		for (int i = 0; i < msgs.size(); i++) {
			msgs.get(i).setMsgDim(guiRoot);
			msgs.get(i).getMsgFromTo(llist);
			msgs.get(i).setPseudoCode(llist);
//			System.out.println(msgs.get(i).pseudoCode);
		}

//		for (int i = 0; i < msgs.size(); i++) {
//			System.out.println(msgs.get(i) + "\n");
//		}
		
		ArrayList<SequenceElement> mainArray = new ArrayList<SequenceElement>();
		mainArray.addAll(blocklist);
		mainArray.addAll(msgs);
//		printChildren(guiRoot);
		return mainArray;

	}

	Node getChild(Node root, String name) {
		NodeList childList = root.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			Node node = childList.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				if (name.equals(node.getNodeName()))
					return ele;
			}
		}
		return null;
	}

	ArrayList<Lifeline> getLifelines(Node root) {
		ArrayList<Lifeline> arr = new ArrayList<Lifeline>();
		Lifeline temp;

		NodeList childList = root.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			Node node = childList.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				if (ele.getNodeName().equals("lifeline")) {
					temp = new Lifeline(ele.getAttribute("xmi:id"), ele.getAttribute("name"));
					temp.populateFragment(ele);
					arr.add(temp);
				}
			}
		}
		return arr;
	}

	ArrayList<Message> getMessages(Node root) {
		ArrayList<Message> arr = new ArrayList<Message>();
		Message temp;

		NodeList childList = root.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			Node node = childList.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				if (ele.getNodeName().equals("message")) {
					temp = new Message(	ele.getAttribute("xmi:id"),
					                        ele.getAttribute("name"),
					                        ele.getAttribute("sendEvent"),
					                        ele.getAttribute("receiveEvent")
					                  );
					arr.add(temp);
				}
			}
		}
		return arr;
	}


	ArrayList<Block> getBlocks(Node root, Node guiRoot) {
		ArrayList<Block> arr = new ArrayList<Block>();
		Block temp = null;

		NodeList childList = root.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			Node node = childList.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				if (ele.getAttribute("interactionOperator").equals("alt")) {
					AltBlock altblock = new AltBlock(ele, guiRoot);
					Block ifblock = new Block(altblock.cond[0], altblock.top, altblock.top + altblock.sep, altblock.left, altblock.right, true);
					Block elseblock = new Block(altblock.cond[1], altblock.top+altblock.sep, altblock.bottom, altblock.left, altblock.right, false);
					arr.add(ifblock);
					arr.add(elseblock);
//					System.out.println(ifblock.top + " "+ ifblock.bottom);
//					System.out.println(elseblock.top + " "+ elseblock.bottom);
				}

				else if (ele.getAttribute("interactionOperator").equals("loop")) {
					temp = (Block)new LoopBlock(ele, guiRoot);
					arr.add(temp);
				}

				else if (ele.getAttribute("interactionOperator").equals("par")) {
					temp = (Block)new ParBlock(ele, guiRoot);
					arr.add(temp);
				}

				else if (ele.getAttribute("interactionOperator").equals("break")) {
					temp = (Block)new BrkBlock(ele, guiRoot);
					arr.add(temp);
				}

				else if (ele.getAttribute("interactionOperator").equals("opt")) {
					temp = (Block)new OptBlock(ele, guiRoot);
					arr.add(temp);
				}
			}
		}
		return arr;
	}

//	void printChildren(Node root) {
//		NodeList childList = root.getChildNodes();
//		for (int i = 0; i < childList.getLength(); i++) {
//			Node node = childList.item(i);
//			if (node instanceof Element) {
//				Element ele = (Element) node;
//				System.out.println(ele.getNodeName());
//			}
//		}
//	}
}
