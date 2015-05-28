import java.util.Arrays;


public class State {
	public Boolean[] trueVisit;
	public Boolean[] falseVisit;
	public TreeNode currNode;
	public StringBuffer path;
	
	State(TreeNode currNode, String path){
		this.currNode = currNode;
		this.trueVisit = new Boolean[100];
		this.falseVisit = new Boolean[100];
		Arrays.fill(this.trueVisit, false);
		Arrays.fill(this.falseVisit, false);
		this.path = new StringBuffer(path);
	}
	
	State(State s1){
		this.currNode = s1.currNode;
		this.trueVisit = new Boolean[100];
		this.falseVisit = new Boolean[100];
		System.arraycopy( s1.trueVisit, 0, this.trueVisit, 0, s1.trueVisit.length);
		System.arraycopy( s1.falseVisit, 0, this.falseVisit, 0, s1.falseVisit.length);
		this.path = new StringBuffer(s1.path);
	}
	
	public String toString(){
		return currNode.stmt.lineno +" "+path;
	}
	
}
