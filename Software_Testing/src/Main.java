import java.util.*;

public class Main {
	public static void main(String [] args) {

		SequenceElement s1 = new SequenceElement(0, 0, "msg0()");
		SequenceElement s2 = new SequenceElement(10, 60, "while(i<10)");
		SequenceElement s3 = new SequenceElement(15, 15, "msg1()");
		SequenceElement s4 = new SequenceElement(20, 30, "if(j<20)");
		SequenceElement s5 = new SequenceElement(30, 40, "else");
		SequenceElement s6 = new SequenceElement(23, 27, "if(j==15)");
		SequenceElement s7 = new SequenceElement(25, 25, "msg2 ()");
		SequenceElement s8 = new SequenceElement(45, 55, "break");
		SequenceElement s9 = new SequenceElement(50, 50, "msg4()");
		SequenceElement s10 = new SequenceElement(35, 35, "msg3()");
		
		ArrayList<SequenceElement> list = new ArrayList<SequenceElement>();
		list.add(s1);
		list.add(s2);
		list.add(s3);
		list.add(s4);
		list.add(s5);
		list.add(s6);
		list.add(s7);
		list.add(s8);
		list.add(s9);
		list.add(s10);
		
		Collections.sort(list);
		Stack<Integer> stack = new Stack<Integer>();
		
		Iterator<SequenceElement> it = list.iterator();
		SequenceElement temp;
		while(it.hasNext()){
			temp = it.next();
			while(!stack.isEmpty() && stack.peek()<temp.bottom)
				stack.pop();
			stack.push(temp.bottom);
			printTab(stack.size());
			System.out.println(temp.pseudoCode);
		}
	}
	
	public static void printTab(int s){
		for(int i=1; i<s;i++)
			System.out.print("\t");
	}
}
