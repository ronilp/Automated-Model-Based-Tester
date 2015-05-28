
public class SequenceElement implements Comparable<SequenceElement>{
	int top, bottom;
	String pseudoCode;
	

	public SequenceElement(int top, int bottom, String pseudoCode) {
		this.top = top;
		this.bottom = bottom;
		this.pseudoCode = pseudoCode;
	}


	public int compareTo(SequenceElement o) {
		if(this.getTop() > o.getTop())
			return 1;
		else if(this.getTop() < o.getTop())
			return -1;
		else
			return 0;
	}

	public int getTop() {return top; }
	public int getBottom() {return bottom; }
	public String getPseudoCode() {return pseudoCode; }

	public void setTop(int top) {this.top = top; }
	public void setBottom(int bottom) {this.bottom = bottom; }
	public void setPseudoCode(String pseudoCode) {this.pseudoCode = pseudoCode; }
	
}