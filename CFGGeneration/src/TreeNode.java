import java.util.*;
public class TreeNode {
	public TreeNode parent;
	public TreeNode trueChild;
	public TreeNode falseChild;
	public StatementInfo stmt;
	boolean visited;
//	public int value;
	
	TreeNode(StatementInfo stmt ){
		this.stmt = stmt;
		this.parent = null;
		this.trueChild = null;
		this.falseChild = null;
		this.visited = false;
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder("Node: "+this.stmt.type+"("+this.stmt.lineno+")");
//		str.append("Node: "+this.stmt.type+"\t");
		
		if(this.parent != null)
			str.append("\t|| parent: " + this.parent.stmt.type + "("+ this.parent.stmt.lineno+")");
		else
			str.append("\t|| parent: NULL");
		if(this.trueChild != null)
			str.append("\t|| trueChild: "+ this.trueChild.stmt.type + "("+ this.trueChild.stmt.lineno+")");
		else
			str.append("\t|| trueChild: NULL");
		if(this.falseChild != null)
			str.append("\t|| falseChild: "+ this.falseChild.stmt.type + "("+ this.falseChild.stmt.lineno+")");
		else
			str.append("\t|| falseChild: NULL");
		str.append("\n");
		return str.toString();
		
	}
	
	public void traverse(){
		Queue<TreeNode> q = new LinkedList<TreeNode>();
		TreeNode tmp = this;
		q.add(tmp);
		
		while(!q.isEmpty()){
			tmp = q.remove();
			if(tmp.trueChild != null && tmp.visited == false)
				q.add(tmp.trueChild);				
			
			if(tmp.falseChild != null  && tmp.visited == false)
				q.add(tmp.falseChild);
			
			tmp.visited = true;
			System.out.println(tmp);			
		}		
	}

	public void generateDot(){
		ArrayList<String> code = new ArrayList<String>();
		Queue<TreeNode> q = new LinkedList<TreeNode>();
		TreeNode tmp = this;
		String temp;
		q.add(tmp);
		
		code.add(new String("digraph G {"));
		while(!q.isEmpty()){
			tmp = q.remove();
			if(tmp.trueChild != null && tmp.visited == false)
				q.add(tmp.trueChild);				
			
			if(tmp.falseChild != null  && tmp.visited == false)
				q.add(tmp.falseChild);
			
			tmp.visited = true;
			switch(tmp.stmt.type){
			case IF:
			case LOOP:
				if(tmp.trueChild != null){
					temp = tmp.stmt.lineno + " -> " + tmp.trueChild.stmt.lineno +" [style=bold,label=\"True\"];";
					if( !code.contains(temp) ) code.add(temp);
				}
				if(tmp.falseChild != null){
					temp = tmp.stmt.lineno + " -> " + tmp.falseChild.stmt.lineno + " [style=bold,label=\"False\"];";
					if( !code.contains(temp) ) code.add(temp);
				}
				break;
			case BREAK:
				if(tmp.trueChild != null){
					temp = tmp.stmt.lineno + " -> " + tmp.trueChild.stmt.lineno +" [style=bold,label=\"False\"];";
					if( !code.contains(temp) ) code.add(temp);
				}
				if(tmp.falseChild != null){
					temp = tmp.stmt.lineno + " -> " + tmp.falseChild.stmt.lineno + " [style=bold,label=\"True\"];";
					if( !code.contains(temp) ) code.add(temp);
				}
				break;
			case PARALLEL:
				if(tmp.trueChild != null){
					temp = tmp.stmt.lineno + " -> " + tmp.trueChild.stmt.lineno +" [style=bold,label=\"Parallel\"];";
					if( !code.contains(temp) ) code.add(temp);
				}
				if(tmp.falseChild != null){
					temp = tmp.stmt.lineno + " -> " + tmp.falseChild.stmt.lineno + " [style=bold,label=\"Sequential\"];";
					if( !code.contains(temp) ) code.add(temp);
				}
				break;
			default:
				if(tmp.trueChild != null){
					temp = tmp.stmt.lineno + " -> " + tmp.trueChild.stmt.lineno +";";
					if( !code.contains(temp) ) code.add(temp);
				}
				if(tmp.falseChild != null){
					temp = tmp.stmt.lineno + " -> " + tmp.falseChild.stmt.lineno + ";";
					if( !code.contains(temp) ) code.add(temp);
				}
				break;
			}
//			System.out.println(tmp);			
		}
		code.add("}");
		for(int i=0; i<code.size(); i++){
			System.out.println(code.get(i));
		}
	}
}