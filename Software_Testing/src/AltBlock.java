import java.util.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.*;
import javax.xml.transform.*;

public class AltBlock {
	String cond[];
	ArrayList<String> covers;
	String id, name;
	int top, right, left, bottom, sep;
	
	public AltBlock(Element ele){
		this.id = ele.getAttribute("xmi:id");
		this.name = ele.getAttribute("name");
		covers = new ArrayList<String>();
		cond = new String[2];
		extractInfo(ele);
	}
	
	private void extractInfo(Element root){
		int condCount=0;
		NodeList childList = root.getChildNodes();
		for(int i=0; i<childList.getLength(); i++){
			Node node = childList.item(i);
			if(node instanceof Element){
				Element ele = (Element) node;
				if(ele.getNodeName().equals("covered"))
					this.covers.add(ele.getAttribute("xmi:idref"));
				else if(ele.getNodeName().equals("operand")){
					Element guard = getChild(ele, "guard");
					Element spec = getChild(guard, "specification");
					cond[condCount++] = spec.getAttribute("value");
				}
			}
		}
	}
	
	public void setAltDim(Node root){
		NodeList childList = root.getChildNodes();
		for(int i=0; i<childList.getLength(); i++){
			Node node = childList.item(i);
			if(node instanceof Element){
				Element ele = (Element) node;
				if(ele.getAttribute("guiLink_Element").equals(this.id)){
					Element nodeRect = getChild(ele, "nodeRect");
					
					this.top = Integer.parseInt(nodeRect.getAttribute("Top"));
					this.bottom = Integer.parseInt(nodeRect.getAttribute("Bottom"));
					this.left = Integer.parseInt(nodeRect.getAttribute("Left"));
					this.right = Integer.parseInt(nodeRect.getAttribute("Right"));
					
					Element separator = getChild(ele, "Separators");
					separator = getChild(separator, "separator");
					this.sep = Integer.parseInt(separator.getAttribute("position"));
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
	
	
	public String toString(){
		String ret =  "ID: "+id+" |Name: "+name+" |cond1: "+cond[0]+" |cond2 :"+cond[1]+ "\n"+
				"Separator :"+sep+" Top:"+top+" bottom:"+bottom+" Left:"+left+" Right:"+right+"\n";
		
		for(int i=0;i<covers.size();i++)
			ret += "cover "+i+": " +covers.get(i)+"\n";
		
		return ret;
	}
	
}
