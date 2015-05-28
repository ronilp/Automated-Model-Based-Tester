/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaretesting;

/**
 *
 * @author Yash
 */
import java.util.*;

import org.w3c.dom.*;

import java.util.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.*;
import javax.xml.transform.*;

public class AltBlock extends Block{
	public int sep;

	public AltBlock(Element ele, Node guiRoot) {
		super(ele);
		cond = new String[2];
		getCond(ele);
		setDim(guiRoot);
		setPseudoCode();
	}

	public void getCond(Element root) {
		Element frag = search(root, id, "xmi:id");
		int count=0;

		NodeList childList = frag.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			Node node = childList.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				if (ele.getNodeName().equals("operand")) {
					Element guard = getChild(ele, "guard");
					Element spec = getChild(guard, "specification");
					cond[count++] = spec.getAttribute("value");
				}
			}
		}
	}

	public void setDim(Node root) {
		Element ele = null;
		if (root instanceof Element) ele = search(root, id, "guiLink_Element");

		Element nodeRect = getChild(ele, "nodeRect");
		top = Integer.parseInt(nodeRect.getAttribute("Top"));
		bottom = Integer.parseInt(nodeRect.getAttribute("Bottom"));
		left = Integer.parseInt(nodeRect.getAttribute("Left"));
		right = Integer.parseInt(nodeRect.getAttribute("Right"));

		Element separator = getChild(ele, "Separators");
		separator = getChild(separator, "separator");
		this.sep = Integer.parseInt(separator.getAttribute("position"));

	}

	// public String toString() {
	// 	String ret =  "ID: " + id + " |Name: " + name + " |cond1: " + cond[0] + " |cond2 :" + cond[1] + "\n" +
	// 	              "Separator :" + sep + " Top:" + top + " bottom:" + bottom + " Left:" + left + " Right:" + right + "\n";
	// 	return ret;
	// }

	public void setPseudoCode(){
		pseudoCode = "ALT BLOCK";
	}
}