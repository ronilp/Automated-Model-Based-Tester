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
import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Message extends SequenceElement {
	String id, name;
	String sendEvent, receiveEvent;
	int fromll, toll;
	int x1,y1,x2,y2;
	
	public Message(String id, String name, String sendEvent, String receiveEvent){
		this.id = id;
		this.name = name;
		this.sendEvent = sendEvent;
		this.receiveEvent = receiveEvent;
	}
	
	public void setMsgDim(Node root){
		String id = this.id;
		NodeList childList = root.getChildNodes();
		for(int i=0; i<childList.getLength(); i++){
			Node node = childList.item(i);
			if(node instanceof Element){
				Element ele = (Element) node;
				if(ele.getNodeName().equals("guiDiagramGuiLink") && ele.getAttribute("guiLink_Element").equals(id)){
					ArrayList<Element> eleList = new ArrayList<Element>();
					
					NodeList list = ele.getChildNodes();
					for(int j=0; j<list.getLength(); j++){
						Node node1 = list.item(j);
						if(node1 instanceof Element){
							Element ele1 = (Element) node1;
							if(ele1.getNodeName().equals("guiLineLinkWaypoint"))
								eleList.add(ele1);
						}
					}
//					System.out.println(eleList.size());
					String x1 = ((Element)getChild(eleList.get(0),"pos")).getAttribute("X");
					String y1 = ((Element)getChild(eleList.get(0),"pos")).getAttribute("Y");
					
					String x2 = ((Element)getChild(eleList.get(1),"pos")).getAttribute("X");
					String y2 = ((Element)getChild(eleList.get(1),"pos")).getAttribute("Y");
					this.x1 = Integer.parseInt(x1); 
					this.y1 = Integer.parseInt(y1); 
					this.x2 = Integer.parseInt(x2); 
					this.y2 = Integer.parseInt(y2); 
				}
			}
		}		
	}
	
	private Element getChild(Element root, String name){
		NodeList childList = root.getChildNodes();
		for(int i=0; i<childList.getLength(); i++){
			Node node = childList.item(i);
			if(node instanceof Element){
				Element ele = (Element) node;
				if(name.equals(node.getNodeName()))
					return ele;
			}
		}
		return null;
	}
	
	public void getMsgFromTo(ArrayList<Lifeline> llist) {
		for(int i=0;i<llist.size();i++){
			if(llist.get(i).fragments.contains(this.sendEvent))
				fromll = i;
			else if(llist.get(i).fragments.contains(this.receiveEvent))
				toll = i;
		}
		
	}
	public void setPseudoCode(ArrayList<Lifeline> llist){
		// pseudoCode = llist.get(fromll).name +" -> "+llist.get(toll).name + " : "+name;
		pseudoCode = name;
		top = y1;
		bottom = y1;
	}
	public String toString(){
		return "|Message :"+id+" |Name: "+name +" |Source Fragment:"+sendEvent+" |Receive Fragment:"+receiveEvent+
				"\n x1:"+x1+" y1:"+y1+" x2:"+x2+" y2:"+y2+"\n"+
				"Sender :"+fromll+" Receiver: "+toll;
	}

}