package algorithms.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * 一颗M阶的B树,M取>=3并且是奇数,方便编程,节点的关键字个数就不能为0
 * 根节点最少有2个孩子，内部节点最少有ceil(M/2)个孩子，最多有M个孩子
 * 根节点至少有1个关键字，其他节点最少有ceil(M/2)-1个关键字，最多有M-1个关键字
 * 每个节点的关键字数量为它的孩子数量-1
 * 所有的叶子节点在同一层
 * */
public class BTreeDemo{

	public static void main(String[] args){
		
	}
	
	private static class BTree {
		
		private final int m;//阶数
		private final int min;//关键字个数的最小值
		private Node root;
		
		public BTree(int m){
			this.m = m;
			this.min = (int) Math.ceil(m/2.0) - 1;
		}
		
		public Entry searchEntry(int key){
			return searchEntry(root,key);
		}

		/*
		 * 递归二分搜索key所在的键值对
		 * */
		public Entry searchEntry(Node node,int key){
			if(node == null)return null;
			
			int index = Collections.binarySearch(node.getEntrys(), new Entry(key,null));
			if(index > 0){
				return node.getEntrys().get(index);
			}else{
				if(node.getChildNodes().size() == 0)return null;
				
				//如果index是负数，那么index = -insertionIndex-1,要将插入点还原出来继续递归
				int insertionIndex = -index - 1;
				return searchEntry(node.getChildNodes().get(insertionIndex),key);
			}
		}
		
		public Node searchNode(int key){
			return searchNode(root,key);
		}
		
		public Node searchNode(Node node,int key){
			if(node == null)return null;
			
			int index = Collections.binarySearch(node.getEntrys(), new Entry(key,null));
			if(index > 0){
				return node;
			}else{
				if(node.getChildNodes().size() == 0)return null;
				
				int inseartionIndex = -index-1;
				return searchNode(node.getChildNodes().get(inseartionIndex),key);
			}
		}
		
		/*
		 * 增加关键字方法
		 * */
		public void add(Entry entry){
			if(root == null){
				Node node = new Node();
				node.add(entry);
				root = node;
				return;
			}
			
		}
		
		public void add(Node node,Entry entry){
			
			//如果是子节点，直接增加关键字，判断是否需要分裂
			if(node.getChildNodes().size() == 0){
				
				//当前元素关键字数量未满，直接添加
				if(node.getEntrys().size() < min - 1){
					node.add(entry);
					return;
				}
				
				//当前关键字数量已满，添加到本届点并分裂
				node.getEntrys().add(entry);
				split(node);
				return;
			}
			
		}
		
		
		
	}
	
	private static class Node implements Comparable<Node>{
		
		private final List<Entry> entrys;
		private final List<Node> childNodes;
		private Node parentNode;
		
        public Node() {
            entrys = new ArrayList<>();
            childNodes = new ArrayList<>();
        }
		
		public List<Entry> getEntrys() {
			return entrys;
		}
		public List<Node> getChildNodes() {
			return childNodes;
		}
		public Node getParentNode() {
			return parentNode;
		}
		public void setParentNode(Node parentNode) {
			this.parentNode = parentNode;
		}
		
		public Node add(Entry entry){
			this.entrys.add(entry);
			Collections.sort(entrys);
			return this;
		}
		
		public Node addChild(Node node){
			this.childNodes.add(node);
			Collections.sort(childNodes);
			return this;
		}
		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.entrys.get(0).getKey(), o.getEntrys().get(0).getKey());
		}
		
        @Override
        public String toString() {
            return "Node{" +
                    "entrys=" + entrys +
                    ", childNodes=" + childNodes +
                    '}';
        }
		
	}
	
	private static class Entry implements Comparable<Entry>{
		
		final int key;
		String value;
		
		public Entry(int key,String value){
			this.key = key;
			this.value = value;
		}
		
		public int getKey(){
			return this.key;
		}
		
		public String getValue(){
			return this.value;
		}
		
		public String setValue(String newValue){
			String oldValue = this.value;
			this.value = newValue;
			return oldValue;
		}

		@Override
		public int compareTo(Entry o) {
			return Integer.compare(key, o.getKey());
		}
		
        @Override
        public String toString() {
            return "{key = " + key + "}";
        }
		
	}

}
