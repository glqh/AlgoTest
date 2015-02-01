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
	
	void add(Integer... nodes) {
		Integer index = Integer.MAX_VALUE;
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
		}
		
		for(Integer node : nodes) {
			Integer thisIndex = treeIndex.get(node);
			if(thisIndex == null) {
				treeIndex.put(node, index);
				childs.add( node );
			} else {
				Set<Integer> thisNodeBrothers = indexTrees.get(thisIndex);
				for(Integer bro : thisNodeBrothers) {
					treeIndex.put(bro, index);
				}
				childs.addAll( thisNodeBrothers );
			}
		}
	}
	
	void print() {
		Set<Integer> keys = indexTrees.keySet();
		for(Integer key : keys) {
			System.out.println(key + ":" + indexTrees.get(key));
		}
	}
	
	
	public static void main(String args[]) {
		Random rand = new Random(System.currentTimeMillis() / 1000);
		UF uf = new UF();
		for(int i = 0; i < 100; i++) {
			int tmp1 = rand.nextInt() % 50000;
			int tmp2 = rand.nextInt() % 50000;
			uf.add(tmp1, tmp2);
		}
		
		uf.print();
		
		
	}
}
