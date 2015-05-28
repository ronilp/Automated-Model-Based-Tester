import java.util.*;

public class Fragment {
	String id, eventId;
	public int top, right, bottom, left;
	ArrayList<Message> messages = null;
	
	public Fragment(String id){
		this.id = id;
	}
	
	public void setDim(String top, String bottom, String left, String right){
		this.top = Integer.parseInt(top);
		this.bottom = Integer.parseInt(bottom);
		this.left = Integer.parseInt(left);
		this.right = Integer.parseInt(right);
	}
	
	public String toString(){
		return "Fragment :"+id+" eventId: "+eventId +" Top:"+top+" bottom:"+bottom+" Left:"+left+" Right:"+right;	
	}
}
