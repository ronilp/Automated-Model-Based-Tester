package softwaretesting;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.util.*;
import java.util.*;

import javax.xml.*;
import javax.xml.transform.*;

public class Block extends SequenceElement{
	public String id, name, type;
	public String cond[];
	public int right, left;

	public Block(Element root) {
		super();
		this.id = root.getAttribute("xmi:id");
		this.name = root.getAttribute("name");
		this.type = root.getAttribute("interactionOperator");
	}

	public Block(String cond, int top,int bottom, int left, int right, boolean isif){
		this.cond = new String[1];
		this.cond[0] = cond;
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
		if(isif) pseudoCode = "IF "+cond;
		else pseudoCode = "ELSE";
	}

	public void setDim(Node root) {
		Element ele = null;
		if (root instanceof Element) ele = search(root, id, "guiLink_Element");

		Element nodeRect = getChild(ele, "nodeRect");
		top = Integer.parseInt(nodeRect.getAttribute("Top"));
		bottom = Integer.parseInt(nodeRect.getAttribute("Bottom"));
		left = Integer.parseInt(nodeRect.getAttribute("Left"));
		right = Integer.parseInt(nodeRect.getAttribute("Right"));
	}

	public Element search(Node root, String id, String key) {
		Element ele = (Element)root;
		if (ele.getAttribute(key).equals(id)) return ele;

		NodeList childList = root.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			Node node = childList.item(i);
			if (node instanceof Element) {
				Element elem = (Element) node;
				Element res = search(elem, id, key);
				if (res != null) return res;
			}
		}
		return null;
	}

	public Element getChild(Element root, String name) {
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
}
