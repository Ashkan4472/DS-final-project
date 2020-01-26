package Nodes;

/**
 * @author      Ashkan Tofangdar <ashkan4472@gmail.com>
 */

public class TrieNode <E> {
	private TrieNode<E>[] child;
	private E data;
	private boolean isEndOfWord;
	private final int NUMBER_OF_CHARACTERS = 26 + 26 + 10; // uppercase + lowercase + number.

	@SuppressWarnings("unchecked")
	public
	TrieNode() {
		this.child = new TrieNode[this.NUMBER_OF_CHARACTERS];
		this.isEndOfWord = false;
		this.data = null;
		for (int i = 0; i < this.NUMBER_OF_CHARACTERS; i++) {
			child[i] = null;
		}
	}

	// /**
	//  * this will get the index th child from node
	//  * @param index
	//  * @return a TrieNode
	//  */
	// public TrieNode<E> getChild(int index) {
	// 	return this.child[index];
	// }

	/**
	 * this will get child with char
	 * @param x the char you seek for
	 * @return a TrieNode or null
	 */
	public TrieNode<E> getChild(char x) {
		if (x >= 'A' && x <= 'Z') {
			return this.child[x - 'A'];
		} else if (x >= 'a' && x <= 'z') {
			return this.child[x - 'a' + 26];
		} else if (x >= '0' && x <= '9') {
			return this.child[x - '0' + 26 + 26];
		} else {
			return null;
		}
	}

	/**
	 * this method will check that if a char is exists in child or not
	 * @param x the char you seek
	 * @return true if exists, false if not
	 */
	public boolean isCharExist(char x) {
		if (x >= 'A' && x <= 'Z') {
			return this.child[x - 'A'] != null;
		} else if (x >= 'a' && x <= 'z') {
			return this.child[x - 'a' + 26] != null;
		} else if (x >= '0' && x <= '9') {
			return this.child[x - '0' + 26 + 26] != null;
		} else {
			return false;
		}
	}


	/**
	 * this will create a new char in child
	 * @param i the index th of child
	 */
	public void setChild(int i) {
		this.child[i] = new TrieNode<E>();
	}

	/**
	 * this method will add a char to child
	 * @param x the char you want to add
	 * @return true if added correctly, false otherwise
	 */
	public boolean addCharToChild(char x) {
		if (x >= 'A' && x <= 'Z') {
			this.child[x - 'A'] = new TrieNode<E>();
			return true;
		} else if (x >= 'a' && x <= 'z') {
			this.child[x - 'a' + 26] = new TrieNode<E>();
			return true;
		} else if (x >= '0' && x <= '9') {
			this.child[x - '0' + 26 + 26] = new TrieNode<E>();
			return true;
		} else {
			System.out.println("Only A-Z, a-z, 0-9 characters are acceptable");
			return false;
		}
	}

	/**
	 * this method sets that this node is end of some word and sets its data
	 * @param isEndOfWord boolean true if it is
	 * @param data the data
	 */
	public void setIsEndOfWord(boolean isEndOfWord, E data) {
		this.isEndOfWord = isEndOfWord;
		this.data = data;
	}

	public boolean getIsEndOfWord() {
		return this.isEndOfWord;
	}

	public E getData() {
		return this.data;
	}

	public void setData(E data) {
		this.data = data;
	}
};