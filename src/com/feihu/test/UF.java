package com.feihu.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/*并查集测试*/
public class UF {
	Map<Integer, Integer> treeIndex = new HashMap();
	Map<Integer, Set<Integer>> indexTrees = new HashMap();
	
	public void add(Integer... nodes) {
		Integer index = Integer.MAX_VALUE;
		
		/*按照所有待加入节点最小代表节点，当然如果节点第一次加入，代表节点会是他自己*/
		for(Integer node : nodes) {
			
			Integer tmpIndex = treeIndex.get(node);
			if(tmpIndex == null) {
				tmpIndex = node;
			}
			
			if(  tmpIndex < index ) {
				index = tmpIndex;
			}
		}
		Set<Integer> childs = indexTrees.get(index);
		if(childs == null) {
			childs = new HashSet();
			indexTrees.put(index, childs);
		}
		
		for(Integer node : nodes) {
			Integer thisIndex = treeIndex.get(node);
			if(thisIndex == null) {
				treeIndex.put(node, index);
				childs.add( node );
			} else {
				if(index.equals(thisIndex)) {
					continue;
				}
				Set<Integer> thisNodeBrothers = indexTrees.get(thisIndex);
				for(Integer bro : thisNodeBrothers) {
					treeIndex.put(bro, index);
				}
				if(!thisIndex.equals( index )) {
					indexTrees.remove( thisIndex );
				}
				childs.addAll( thisNodeBrothers );
			}
		}
	}
	
	/*判断是否为关联*/
	public boolean isBrother(Integer node1, Integer node2) {
		Integer index1 = treeIndex.get(node1);
		if(index1 == null) {
			return false;
		}
		
		Integer index2 = treeIndex.get(node2);
		if(index2 == null) {
			return false;
		}
		
		if(index1.equals(index2)) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public void print() {
		Set<Integer> keys = indexTrees.keySet();
		for(Integer key : keys) {
			System.out.println("key "  + key + ":" + indexTrees.get(key));
		}
	}
	
	
	public static void main(String args[]) {
		
		UF uf = new UF();
		System.out.println(System.currentTimeMillis());
		for(int i = 0; i < 100000; i++) {
			uf.add(i, i - 1);
		}
		System.out.println(System.currentTimeMillis());
		System.out.println( uf.isBrother(-1, 99) );
		System.out.println( uf.isBrother(2, 88) );
		System.out.println( uf.isBrother(-1, 101) );
		System.out.println( uf.isBrother(5, 99999) );
		//uf.print();
	}
}
