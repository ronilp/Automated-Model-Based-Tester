
public class StatementInfo {
	public enum Type{LOOP, IF, ELSE, PARALLEL, BREAK, MESSAGE, START, STOP}
	
	public int indent;
	public int lineno;
	public Type type;
	public String cond;
	
	public StatementInfo(int indent, int lineno, Type type, String cond){
		this.indent = indent;
		this.lineno = lineno;
		this.type = type;
		this.cond = cond;
	}
	
	public String toString(){
		return "indent:"+indent+"\tlineno: "+ lineno + "\ttype:"+type+"\tcond:"+cond;
	}
}
