package softwaretesting;

public class SequenceElement implements Comparable<SequenceElement>{
	public int top, bottom;
	public String pseudoCode;

	public SequenceElement() {}

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
	public String toString() {
		return getPseudoCode();
	}
}