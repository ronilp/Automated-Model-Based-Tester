import java.util.*;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Lifeline {
	public String id, name;
	ArrayList<String> fragments;
	
	public Lifeline(String id, String name){
		this.id = id;
		this.name = name;
		this.fragments = new ArrayList<String>();
	}
	
	public void addFragment(String frag){
		this.fragments.add(frag);
	}
	
	public void populateFragment(Element root){
		NodeList childList = root.getChildNodes();
		String id;
		for(int i=0; i<childList.getLength(); i++){
			Node node = childList.item(i);
			if(node instanceof Element){
				Element ele = (Element) node;
				if(ele.getNodeName().equals("coveredBy")){
					id = ele.getAttribute("xmi:idref");
					this.addFragment(id);
				}
			}
		}
		
	}
	
	public void display(){
		System.out.println("Lifeline Name: "+name+"\nID: "+id);
		for(int i=0;i<fragments.size();i++)
			System.out.println(fragments.get(i));
	}
}
