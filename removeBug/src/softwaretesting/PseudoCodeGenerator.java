package softwaretesting;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.JTextArea;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class PseudoCodeGenerator {
	static String output = "";
	public static void generate(File inputFile) throws ParserConfigurationException, SAXException, IOException {
		output = "";
//		inputFile = new File("/home/shikhar/Documents/software_testing/TestCase1111.xmi");
		ParseXMI xmiParser = new ParseXMI();
		
		ArrayList<SequenceElement> list = xmiParser.parse(inputFile); 
		
		Collections.sort(list);
		Stack<Integer> stack = new Stack<Integer>();
		
		Iterator<SequenceElement> it = list.iterator();
		SequenceElement temp;
//		System.out.println("Hello");
		while(it.hasNext()){
			temp = it.next();
			while(!stack.isEmpty() && stack.peek()<temp.bottom)
				stack.pop();
			stack.push(temp.bottom);
			printTab(stack.size());
			System.out.println(temp.pseudoCode);
			output += temp.pseudoCode + "\n";
		}
		MainFrame.jTextArea2.setText(output);
	}
	
	public static void printTab(int s){
		for(int i=1; i<s;i++) {
			System.out.print("\t");
			output += "\t";
		}
	}
}
