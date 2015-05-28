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

public class ParBlock extends Block{

	public ParBlock(Element ele, Node guiRoot) {
		super(ele);
		setDim(guiRoot);
		setPseudoCode();
	}

	// public String toString() {
	// 	String ret =  "ID: " + id + " |Name: " + name + "\n" +
	// 	              " Top:" + top + " bottom:" + bottom + " Left:" + left + " Right:" + right + "\n";

	// 	return ret;
	// }
	public void setPseudoCode(){
		pseudoCode = "Parallel :";
	}
}