/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaretesting;

import java.util.*;

import org.w3c.dom.*;

/**
 *
 * @author Yash
 */
public class LoopBlock extends Block {

	public LoopBlock(Element ele, Node guiRoot) {
		super(ele);
		cond = new String[2];
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
				if (ele.getNodeName().equals("operand") && ele != null) {
					Element guard = getChild(ele, "guard");
					Element maxint = getChild(guard, "maxint");
					Element minint = getChild(guard, "minint");
					cond[0] = maxint.getAttribute("value");
					cond[1] = minint.getAttribute("value");
				}
			}
		}
	}

	// public String toString() {
	// 	String ret =  "ID: " + id + " |Name: " + name + " |cond1: " + cond[0] + " |cond2 :" + cond[1] + "\n" +
	// 	              " Top:" + top + " bottom:" + bottom + " Left:" + left + " Right:" + right + "\n";
	// 	return ret;
	// }

	public void setPseudoCode(){
		pseudoCode = "LOOP "+cond[1]+" to "+cond[0];
	}
}

