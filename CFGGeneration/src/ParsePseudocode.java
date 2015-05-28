import java.awt.geom.GeneralPath;
import java.io.*;
import java.util.*;


public class ParsePseudocode {

	public static void main(String [] args) throws IOException{
		
		File file = new File("/home/shikhar/workspace/Software_testing/CFGGeneration/src/test2.txt");
		FileInputStream fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		
		ArrayList<StatementInfo> stmts =  new ArrayList<StatementInfo>();		
		String line = null;
		StatementInfo si;
		int lineno = 1;
		
		while ((line = br.readLine()) != null){
			si = getStatementInfo(line, lineno);
			stmts.add(si);
			lineno++;
		}br.close();
		
		
//		for(int i=0;i<stmts.size();i++){
//			System.out.println(stmts.get(i));
//		}
		
		Stack<TreeNode> stk = new Stack<TreeNode>();
		StatementInfo stmt;
		boolean isPrevElse = false;
		int currIndent, prevIndent = 0;
		TreeNode root = new TreeNode(new StatementInfo(0, 0, StatementInfo.Type.START, ""));
		stmts.add(new StatementInfo(0, -1, StatementInfo.Type.STOP, ""));
		TreeNode currNode = null, stkNode = null;
		TreeNode prevNode = root;
		
		for(int i=0;i<stmts.size();i++){
			stmt = stmts.get(i);
			currIndent = stmt.indent;
			currNode = new TreeNode(stmt);
						
			//Check the stack for >= currIndent
			for(int j=stk.size()-1; j>=0; j--){
				
				stkNode = stk.get(j);
				
				if(currNode.stmt.type == StatementInfo.Type.ELSE) break;
				if( stkNode.stmt.indent < currIndent) break;
				
				switch(stk.get(j).stmt.type){
					case IF:
						stkNode.falseChild = currNode;
						break;
					case BREAK:
						stkNode.falseChild = currNode;
						break;
					case PARALLEL:
						stkNode.falseChild = currNode;
						break;
					case LOOP:
						prevNode.trueChild = stkNode;
						stkNode.falseChild = currNode;
						break;
					case MESSAGE:
						stkNode.trueChild = currNode;
						break;
					default:
						System.out.println("NOT FOUND");
				}
				stk.remove(j);
			}
			
			if(isPrevElse){
				prevNode.stmt = stmt;
				isPrevElse = false;
				continue;
			}
			
			switch(stmt.type){
				case IF:
					stk.push(currNode);
					if((prevNode.trueChild!=null && prevNode.trueChild.stmt.type != StatementInfo.Type.LOOP )|| prevNode.trueChild==null) prevNode.trueChild = currNode;
					currNode.parent = prevNode;
					break;
				case BREAK:
					stk.push(currNode);
					if((prevNode.trueChild!=null && prevNode.trueChild.stmt.type != StatementInfo.Type.LOOP )|| prevNode.trueChild==null) prevNode.trueChild = currNode;
					currNode.parent = prevNode;
					break;
				case ELSE:
					isPrevElse = true;
					// System.out.println("++++++" + prevNode);

					// if((prevNode.trueChild!=null && prevNode.trueChild.stmt.type != StatementInfo.Type.LOOP )|| prevNode.trueChild==null) stk.push(prevNode);
					stk.push(prevNode);
					for(int j=stk.size()-1; j>=0; j--){
						stkNode = stk.get(j);
						if((stkNode.stmt.indent == currNode.stmt.indent) && (stkNode.stmt.type == StatementInfo.Type.IF)){
							stkNode.falseChild = currNode;
							stk.remove(j);
							for(int k = stk.size()-1; k>=j && k>=0; k--)
								stk.get(k).stmt.indent = currNode.stmt.indent;
							break;
						}
					}					
					break;
				case PARALLEL:
					stk.push(currNode);
					if((prevNode.trueChild!=null && prevNode.trueChild.stmt.type != StatementInfo.Type.LOOP )|| prevNode.trueChild==null) prevNode.trueChild = currNode;
					currNode.parent = prevNode;
					break;
				case LOOP:
					stk.push(currNode);
					if((prevNode.trueChild!=null && prevNode.trueChild.stmt.type != StatementInfo.Type.LOOP )|| prevNode.trueChild==null) prevNode.trueChild = currNode;
					
					currNode.parent = prevNode;
					break;
				case MESSAGE:
					if((prevNode.trueChild!=null && prevNode.trueChild.stmt.type != StatementInfo.Type.LOOP )|| prevNode.trueChild==null) prevNode.trueChild = currNode;
					currNode.parent = prevNode;
					break;
				case STOP:
					prevNode.trueChild = currNode;
			}
			prevNode = currNode;
			int k;
//			System.out.println("StackContent");
//			for(k=0; k<stk.size(); k++) System.out.println(stk.get(k).stmt.type + "-" + stk.get(k).stmt.indent);
//			System.out.println();
		}
//		root.traverse();
		// currNode = stmts.get(0);
		StringBuffer path = new StringBuffer("0");
		boolean[] trueVisit = new boolean[100];
		boolean[] falseVisit = new boolean[100];
		Arrays.fill(trueVisit, false);
		Arrays.fill(falseVisit, false);
		
		root.generateDot();
		genPaths(root);
	 
	}

	public static void genPaths(TreeNode root){
		State state, temp;
		int id;
		boolean split;

		Stack<State> stack = new Stack<State>();
		stack.push(new State(root, "0"));
		while(!stack.isEmpty()){
			state = stack.peek(); stack.pop();
//			System.out.println(state.currNode.stmt.lineno);
			id = state.currNode.stmt.lineno;

			/*Stop is reached*/
			if(id == -1){
				System.out.println(state.path);
				continue;
			}
			
//			System.out.println(state.trueVisit[id]);
//			if(state.trueVisit == null){
//				System.out.println("GII");
//			}
			
			if( (!state.trueVisit[id]) && (state.currNode.trueChild!=null) && (!state.falseVisit[id]) && (state.currNode.falseChild!=null)){
				temp = new State(state);
				temp.trueVisit[id] = true;
				temp.path.append("->" + state.currNode.falseChild.stmt.lineno);
				temp.currNode = temp.currNode.falseChild;
				stack.push(temp);

				state.path.append("->" + state.currNode.trueChild.stmt.lineno);
				state.currNode = state.currNode.trueChild;
				state.trueVisit[id] = true;
				stack.push(state);
				continue;
			}

			if((!state.trueVisit[id]) && (state.currNode.trueChild!=null) ){
				state.path.append("->" + state.currNode.trueChild.stmt.lineno);
				state.currNode = state.currNode.trueChild;
				state.trueVisit[id] = true;
				stack.push(state);
				continue;
			}

			if( (!state.falseVisit[id]) && (state.currNode.falseChild!=null) ){
				state.path.append("->" + state.currNode.falseChild.stmt.lineno);
				state.currNode = state.currNode.falseChild;
				state.falseVisit[id] = true;
				stack.push(state);
				continue;
			}
		} 
	}

	private static StatementInfo getStatementInfo(String line, int lineno) {
		int indent = 0, i;
		StatementInfo.Type type;
		String cond = "";
		
		for(i=0;i <line.length(); i++)
			if(line.charAt(i) == '\t')
				indent++;
		
		line = line.trim();
		String[] lineToken = line.split("\\s");
		
		if(lineToken[0].equals("IF")){
			type = StatementInfo.Type.IF;
			for(i=1; i<lineToken.length; i++)
				cond = cond + lineToken[i];
		}
		else if(lineToken[0].equals("ELSE")){
			type = StatementInfo.Type.ELSE;
		}
		else if(lineToken[0].equals("BREAK")){
			type = StatementInfo.Type.BREAK;
			for(i=2; i<lineToken.length; i++)
				cond = cond + lineToken[i];
		}
		else if(lineToken[0].equals("Parallel")){
			type = StatementInfo.Type.PARALLEL;
		}
		else if(lineToken[0].equals("LOOP")){
			type = StatementInfo.Type.LOOP;
			for(i=1; i<lineToken.length; i++)
				cond = cond + lineToken[i];
		}
		else{
			type = StatementInfo.Type.MESSAGE;
			cond = line;
		}
		
		return new StatementInfo(indent, lineno, type, cond);
	}
}
