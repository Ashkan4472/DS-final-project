package Structures.Tree.TrieTree;

public class TrieTree <E> {
	private TrieNode<E> head;

	/**
	 * this method is constructor of TrieTree
	 */
	public TrieTree() {
		this.head = new TrieNode<E>();
	}

	/**
	 * this method will add a data with name in TrieTree
	 * @param name the name to save
	 * @param data the data represent that name
	 */
	public void insert(String name, E data) {
		TrieNode<E> T = this.head;
		for (int i = 0; i < name.length(); i++) {
			char x = name.charAt(i);
			if (!T.isCharExist(x)) {
				T.addCharToChild(x);
			}
			T = T.getChild(x);
		}
		T.setIsEndOfWord(true, data);
	}
	
	/**
	 * this method will search and find a node with given name
	 * @param name the string to search with
	 * @return a TrieNode represent the name
	 */
	private TrieNode<E> findNode(String name) {
		TrieNode<E> T = this.head;
		for (int i = 0; i < name.length(); i++) {
			char x = name.charAt(i);
			if (!T.isCharExist(x)) {
				return null;
			}
			T = T.getChild(x);
		}
		return T;
	}

	/**
	 * this method would delete data in TrieTree
	 * @param name the name to remove
	 */
	public void delete(String name) {
		TrieNode<E> T = findNode(name);
		if (T != null && T.getIsEndOfWord()) {
			T.setIsEndOfWord(false, null);
		}
	}
}