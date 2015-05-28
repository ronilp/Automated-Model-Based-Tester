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

public class BrkBlock extends Block{

	public BrkBlock(Element ele, Node guiRoot) {
		super(ele);
		cond = new String[1];
		getCond(ele);
		setDim(guiRoot);
		setPseudoCode();
	}

	public void getCond(Element root) {
		Element frag = search(root, id, "xmi:id");
		NodeList childList = frag.getChildNodes();

		for (int i = 0; i < childList.getLength(); i++) {
			Node node = childList.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				if (ele.getNodeName().equals("operand")) {
					Element guard = getChild(ele, "guard");
					Element spec = getChild(guard, "specification");
					cond[0] = spec.getAttribute("value");
					return;
				}
			}
		}
	}

	// public String toString() {
	// 	String ret =  "ID: " + id + " |Name: " + name + " |cond: " + cond[0] + "\n" +
	// 	              " Top:" + top + " bottom:" + bottom + " Left:" + left + " Right:" + right + "\n";
	// 	return ret;
	// }

	public void setPseudoCode(){
		pseudoCode = "BREAK on "+cond[0];
	}
}