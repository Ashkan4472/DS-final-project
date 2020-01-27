import java.util.Scanner;

class Neighborhood {
	private NeighborNode[] neighbors;
	private int size;
	private int currentIndex;

	public Neighborhood() {
		this.size = 10;
		this.currentIndex = -1;
		this.neighbors = new NeighborNode[size];
	}

	private void checkSize() {
		if (this.currentIndex == this.size - 1) {
			size += 10;
			NeighborNode[] temp = new NeighborNode[size];
			for (int i = 0; i < this.currentIndex; i++) {
				temp[i] = this.neighbors[i];
			}
			this.neighbors = temp;
		}
	}

	public void insert(int x[], int y[], String name) {
		this.checkSize();
		this.currentIndex++;
		this.neighbors[this.currentIndex] = new NeighborNode(x, y, name);
	}
	
	public void insert(int x[], int y[], String name, Bank banks[]) {
		this.checkSize();
		this.currentIndex++;
		this.neighbors[this.currentIndex] = new NeighborNode(x, y, name, banks);
	}

	public void insert(NeighborNode n) {
		this.checkSize();
		this.currentIndex++;
		this.neighbors[this.currentIndex] = n;
	}

	public NeighborNode search(int x[], int y[]) {
		for (int i = 0; i <= this.currentIndex; i++) {
			if (
				neighbors[i].getXCoordinate()[0] == x[0] && 
				neighbors[i].getXCoordinate()[1] == x[1] &&
				neighbors[i].getYCoordinate()[0] == y[0] && 
				neighbors[i].getYCoordinate()[1] == y[1]
			) {
				return neighbors[i];
			}
		}
		return null;
	}
	
	public NeighborNode search(String name) {
		for (int i = 0; i <= this.currentIndex; i++) {
			if (neighbors[i].getName().equals(name)) {
				return neighbors[i];
			}
		}
		return null;
	}

	public NeighborNode[] searchArea(int x, int y) {
		NeighborNode[] result = new NeighborNode[this.currentIndex + 1];
		int counter = -1;
		for (int i = 0; i <= this.currentIndex; i++) {
			if (
				x >= neighbors[i].getXCoordinate()[0] && 
				x <= neighbors[i].getXCoordinate()[1] &&
				y >= neighbors[i].getYCoordinate()[0] && 
				y <= neighbors[i].getYCoordinate()[1]
			) {
				counter++;
				result[counter] = neighbors[i];
			}
		}
		return result;
	}

	public void delete(int x[], int y[]) {
		int foundIndex = -1;
		for (int i = 0; i <= this.currentIndex; i++) {
			if (
				neighbors[i].getXCoordinate()[0] == x[0] && 
				neighbors[i].getXCoordinate()[1] == x[1] &&
				neighbors[i].getYCoordinate()[0] == y[0] && 
				neighbors[i].getYCoordinate()[1] == y[1]
			) {
				foundIndex = i;
				break;
			}
		}

		for (int i = foundIndex; i < this.currentIndex; i++) {
			this.neighbors[i] = this.neighbors[i + 1];
		}
		this.neighbors[currentIndex] = null;
		this.currentIndex--;
	}
}

class NeighborNode {
	private int x[];
	private int y[];
	private String name;
	public KdTree<Bank> banks;

	public NeighborNode(int x[], int y[], String name) {
		this.x = new int[2];
		this.y = new int[2];
		int tmp;
		if (x[0] > x[1]) {
			tmp = x[0];
			x[0] = x[1];
			x[1] = tmp;
		}
		if (y[0] > y[1]) {
			tmp = y[0];
			y[0] = y[1];
			y[1] = tmp;
		}
		this.x = x;
		this.y = y;
		this.name = name;
		this.banks = new KdTree<Bank>();
	}
	
	NeighborNode(int x[], int y[], String name, Bank banks[]) {
		this.x = new int[2];
		this.y = new int[2];
		int tmp;
		if (x[0] > x[1]) {
			tmp = x[0];
			x[0] = x[1];
			x[1] = tmp;
		}
		if (y[0] > y[1]) {
			tmp = y[0];
			y[0] = y[1];
			y[1] = tmp;
		}
		this.x = x;
		this.y = y;
		this.name = name;
		this.banks = new KdTree<Bank>();
		for (Bank bank : banks) {
			this.banks.addPoint(bank.getCoordinate(), bank);
		}
	}

	public String getName() {
		return this.name;
	}

	public int[] getXCoordinate() {
		return this.x;
	}

	public int[] getYCoordinate() {
		return this.y;
	}

}

class Bank {
	private int[] coordinate;
	private String name;
	public KdTree<Branch> branches;
	private static int TOTAL_BRANCH_NUMBER = 0;

	public Bank(int coordinate[], String name) {
		this.coordinate = coordinate; // Warning: this would not copy data.
		this.name = name;
		this.branches = new KdTree<Branch>();
	}

	public Bank(int coordinate[], String name, Branch branches[]) {
		this.coordinate = coordinate; // Warning: this would not copy data.
		this.name = name;
		this.branches = new KdTree<Branch>();
		for (Branch branch : branches) {
			this.branches.addPoint(branch.getCoordinate(), branch);
			TOTAL_BRANCH_NUMBER++;
		}
	}

	public void addBranch(Branch branch) {
		this.branches.addPoint(branch.getCoordinate(), branch);
		TOTAL_BRANCH_NUMBER++;
	}

	public int getBranchNumber() {
		return TOTAL_BRANCH_NUMBER;
	}

	public void deleteBranch(Branch branch) {
		this.branches.delete(branch.getCoordinate());
	}

	public void deleteBranch(int[] coordinate) {
		this.branches.delete(coordinate);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getCoordinate() {
		return this.coordinate;
	}

	public void setCoordinate(int[] coordinate) {
		this.coordinate = coordinate;
	}

	@Override
	public String toString() {
		return "Bank: \n" + "name: " + this.name + "\nX: " + this.coordinate[0] + "\nY: " + this.coordinate[1];
	}
}

class Branch {
	private String name;
	private int[] coordinate;
	private String bankName;
	private static int VISITED = 0;

	public Branch(int[] coordinate, String name, String bankName) {
		this.bankName = bankName;
		this.name = name;
		this.coordinate = coordinate;
		this.bankName = bankName;
	}

	public String getBankName() {
		return this.bankName;
	}

	public int getVisited() {
		return VISITED;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getCoordinate() {
		return this.coordinate;
	}

	public void setCoordinate(int[] coordinate) {
		this.coordinate = coordinate;
	}

	@Override
	public String toString() {
		VISITED++;
		return "branch name: " + getName() + "\nbank name: " + this.bankName + "\nX: " + getCoordinate()[0] + " Y:" + getCoordinate()[1];
	}
}

class KdNode <E> {
	public E data;
	private int x;
	private int y;
	public KdNode<E> left;
	public KdNode<E> right;

	/**
	 * Constructor of KdNode.
	 * <p>
	 * this is KdNode constructor that is mostly used in KdTree
	 * it has a data and a left and right child witch are public
	 * </p>
	 * 
	 * @param x x coordinate of data
	 * @param y y coordinate of data
	 */
	public KdNode(int x, int y) {
		this.left = null;
		this.right = null;
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructor of KdNode.
	 * <p>
	 * this is KdNode constructor that is mostly used in KdTree
	 * it has a data and a left and right child witch are public
	 * </p>
	 * 
	 * @param data is the data that KdNode saves inside
	 * @param x x coordinate of data
	 * @param y y coordinate of data
	 */
	public KdNode(E data, int x, int y) {
		this.data = data;
		this.left = null;
		this.right = null;
		this.x = x;
		this.y = y;
	}

	/**
	 * this method get coordinate of node
	 * @return an int array like [x, y]
	 */
	public int[] getCoordinate() {
		int[] result = {this.x, this.y};
		return result;
	}

	/**
	 * this method set node coordinate.
	 * @param coordinate an int array like [x, y]
	 */
	public void setCoordinate(int[] coordinate) {
		this.x = coordinate[0];
		this.y = coordinate[1];
	}

	@Override
	public String toString() {
		return this.data.toString();
	}

}

class KdTree <E> {
	private KdNode<E> node;

	/**
	 * Constructor of KdNode.
	 * <p>
	 * creates a KdTree with null root node
	 * </p>
	 */
	public KdTree() {
		this.node = null;
	}

	/**
	 * returns the current node (the current is root)
	 * <p>
	 * this method returns the root of KdTree
	 * </p>
	 * @return a KdNode that is root
	 */
	public KdNode<E> getNode() {
		return this.node;
	}

	/**
	 * this method adds a point to KdTree
	 * @param coordinate
	 * @param data
	 */
	public void addPoint(int[] coordinate, E data) {
		this.node = this.addPointLogic(node, coordinate, data, 0);
	}

	private KdNode<E> addPointLogic(KdNode<E> node, int[] coordinate, E data, int layer) {
		if (node != null) {
			int xORy = layer % 2;
			if (coordinate[xORy] < node.getCoordinate()[xORy]) {
				node.left = addPointLogic(node.left, coordinate, data, layer + 1);
			} else {
				node.right = addPointLogic(node.right, coordinate, data, layer + 1);
			}
			return node;
		} else {
			return new KdNode<E>(data, coordinate[0], coordinate[1]);
		}
	}

	/**
	 * this method returns found node with coordinate give
	 * <p>
	 * this method returns found node with coordinate give if
	 * if it wasn't found, returns null
	 * </p>
	 * @param coordinate
	 * @return node in the coordinate.
	 */
	public KdNode<E> search(int[] coordinate) {
		return this.searchLogic(this.node, coordinate, 0);
	}

	private KdNode<E> searchLogic(KdNode<E> node, int[] coordinate, int layer) {
		if (node == null) {
			return null;
		}
		if (node.getCoordinate()[0] == coordinate[0] && node.getCoordinate()[1] == coordinate[1]) {
			return node;
		}

		int xORy = layer % 2;
		if (coordinate[xORy] >= node.getCoordinate()[xORy]) {
			return searchLogic(node.left, coordinate, layer + 1);
		}
		return searchLogic(node.right, coordinate, layer + 1);
	}

	/**
	 * this method deletes a node with given coordinate
	 * @param point
	 */
	public void delete(int[] coordinate) {
		this.node = this.deleteLogic(this.node, coordinate, 0);
	}

	private KdNode<E> deleteLogic(KdNode<E> node, int[] coordinate, int layer) {
		if (node == null) {
			return null;
		}

		int xORy = layer % 2;
		if (node.getCoordinate()[0] == coordinate[0] && node.getCoordinate()[1] == coordinate[1]) {
			if (node.right != null) {
				KdNode<E> min = this.findMin(node.right, xORy);
				node.setCoordinate(min.getCoordinate());
				node.data = min.data;
				node.right = deleteLogic(node.right, min.getCoordinate(), layer + 1);
			} else if (node.left != null) {
				KdNode<E> min = this.findMin(node.left, xORy);
				node.setCoordinate(min.getCoordinate());
				node.data = min.data;
				node.left = deleteLogic(node.left, min.getCoordinate(), layer + 1);
			} else {
				node = null;
				return null;
			}
			return node;
		}
		if (coordinate[xORy] < node.getCoordinate()[xORy]) {
			node.left = deleteLogic(node.left, coordinate, layer + 1);
		} else {
			node.right = deleteLogic(node.right, coordinate, layer + 1);
		}
		return node;
	}

	private KdNode<E> findMin(KdNode<E> node, int xORy) {
		return findMinLogic(node, xORy, 0);
	}

	private KdNode<E> findMinLogic(KdNode<E> node, int sentXOrY, int layer) {
		if (node == null) {
			return null;
		}
		int xORy = layer % 2;
		if (xORy == sentXOrY) {
			if (node.left == null) {
				return node;
			}
			return findMinLogic(node.left, sentXOrY, layer + 1);
		}
		return this.minNode(node, findMinLogic(node.left, sentXOrY, layer + 1),
				findMinLogic(node.right, sentXOrY, layer + 1), sentXOrY);
	}

	private KdNode<E> minNode(KdNode<E> x, KdNode<E> y, KdNode<E> z, int xORy) {
		KdNode<E> res = x;
		if (y != null && y.getCoordinate()[xORy] < res.getCoordinate()[xORy]) {
			res = y;
		}
		if (z != null && z.getCoordinate()[xORy] < res.getCoordinate()[xORy]) {
			res = z;
		}
		return res;
	}

	/**
	 * this method prints all nodes in tree
	 */
	public void printAll() {
		printRecursive(this.node);
	}

	private void printRecursive(KdNode<E> head) {
		if (head == null) {
			return;
		}
		System.out.println(head);
		printRecursive(head.left);
		printRecursive(head.right);
	}

	/**
	 * this method return nearest node to a given coordinate
	 * @param coordinate
	 * @return nearest node
	 */
	public KdNode<E> nearest(int[] coordinate) {
		nearestLogic(this.node, coordinate, 0);
		this.minDist = Double.MAX_VALUE;
		return closestNode;
	}

	private double minDist = Double.MAX_VALUE;
	private KdNode<E> closestNode;
	private void nearestLogic(KdNode<E> head, int[] coordinate, int layer) {
		if (head == null)
			return;
		double dist = Math.pow((head.getCoordinate()[0] - coordinate[0]), 2) + Math.pow((head.getCoordinate()[1] - coordinate[1]), 2);
		if (dist < minDist) {
			this.minDist = dist;
			this.closestNode = head;
		}
		int xORy = layer % 2;
		if (coordinate[xORy] < head.getCoordinate()[xORy]) {
			if (head.left != null) {
				nearestLogic(head.left, coordinate, layer + 1);
			}
			if (head.right != null && coordinate[xORy] + minDist >= head.getCoordinate()[xORy]) {
				nearestLogic(head.right, coordinate, layer + 1);
			}
		} else {
			if (head.right != null) {
				nearestLogic(head.right, coordinate, layer + 1);
			}
			if (head.left != null && coordinate[xORy] + minDist >= head.getCoordinate()[xORy]) {
				nearestLogic(head.left, coordinate, layer + 1);
			}
		}
	}

	private GenericArray<E> inRange;
	private int counter = 0;
	/**
	 * returns an array of objects with KdNodes object of nodes in given coordinate and radius of range
	 * @param coordinate
	 * @param range
	 * @return an array of Objects with KdNodes (need convert)
	 */
	public GenericArray<E> findAllInRange(int[] coordinate, int range) {
		this.counter = 0;
		// TODO: use custom array later.
		inRange = new GenericArray<>(1000);
		findAllInRangeLogic(this.node, coordinate, range, 0);
		return inRange;
	}

	private void findAllInRangeLogic(KdNode<E> head, int[] coordinate, int range, int layer) {
		if (head == null)
			return;
		double dist = Math.pow((head.getCoordinate()[0] - coordinate[0]), 2) + Math.pow((head.getCoordinate()[1] - coordinate[1]), 2);
		if (dist <= range) {
			inRange.set(counter, head.data);
			this.counter++;
		}
		int xORy = layer % 2;
		if (coordinate[xORy] < head.getCoordinate()[xORy]) {
			if (head.left != null) {
				findAllInRangeLogic(head.left, coordinate, range, layer + 1);
			}
			if (head.right != null && coordinate[xORy] + range > head.getCoordinate()[xORy]) {
				findAllInRangeLogic(head.right, coordinate, range, layer + 1);
			}
		} else {
			if (head.right != null) {
				findAllInRangeLogic(head.right, coordinate, range, layer + 1);
			}
			if (head.left != null && coordinate[xORy] + range > head.getCoordinate()[xORy]) {
				findAllInRangeLogic(head.left, coordinate, range, layer + 1);
			}
		}
	}

	private GenericArray<E> allNodes;
	/**
	 * returns an array of object (need to convert to KdNode) with all nodes
	 * @return
	 */
	public GenericArray<E> getAllNodes() {
		this.counter = -1;
		allNodes = new GenericArray<E>(1000);
		this.getAllNodesLogic(this.node);
		return allNodes;
	}

	private void getAllNodesLogic(KdNode<E> node) {
		if (node == null) {
			return;
		}
		counter++;
		this.allNodes.set(counter, node.data);
		this.getAllNodesLogic(node.left);
		this.getAllNodesLogic(node.right);
	}
}

class TrieNode <E> {
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
}

class TrieTree <E> {
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
	 * this method will search and find a data with given name
	 * @param name the string to search with
	 * @return data represent the name
	 */
	public E search(String name) {
		TrieNode<E> T = findNode(name);
		if (T != null && T.getIsEndOfWord()) {
			return T.getData();
		} else {
			return null;
		}
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

class GenericArray<E> {

	private Object[] arr;
	public int length;

	public GenericArray(int length) {
		arr = new Object[length];
		this.length = length;
	}

	public E get(int i) {
		@SuppressWarnings("unchecked")
		final E e = (E)arr[i];
		return e;
	}

	public void set(int i, E e) {
		arr[i] = e;
	}
}


class Stack {
	// TODO: make this generic type too.
	private static final int MAX = 1000;
	private String a[];
	private int currentIndex;

	public Stack() {
		this.a = new String[MAX];
		this.currentIndex = -1;
	}

	public boolean isEmpty() {
		return a[0].isEmpty();
	}

	public void push(String input) {
		if (currentIndex >= (MAX - 1)) {
			System.out.println("stack is full");
			// TODO: add ability to upgrade stack size.
		} else {
			currentIndex++;
			a[currentIndex] = input;
		}
	}

	public String pop() {
		if (currentIndex < 0) {
			System.out.println("stack is empty");
			return "";
		} else {
			return a[currentIndex--];
		}
	}

	public String top() {
		if (currentIndex < 0) {
			System.out.println("stack is empty");
			return "";
		} else {
			return a[currentIndex];
		}
	}
	
	@Override
	public String toString() {
		String x = "";
		for (int i = 0; i <= this.currentIndex; i++) {
			x += a[i] + '\n';
		}
		return x;
	}
}

public class Main {
	public static Stack commands;
	public static TrieTree<Bank> bankTrieTree;
	public static TrieTree<Branch> branchTrieTree;
	public static Neighborhood neighborhoods;
	public static KdTree<Bank> banksKdTree;
	public static KdTree<Branch> branchesKdTree;
	public static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		printWelcome();
		showHelp();
		initialize();
		while (true) {
			getCommand();
		}
	}

	public static void initialize() {
		commands = new Stack();
		neighborhoods = new Neighborhood();
		bankTrieTree = new TrieTree<Bank>();
		branchTrieTree = new TrieTree<Branch>();
		banksKdTree = new KdTree<Bank>();
		branchesKdTree = new KdTree<Branch>();
	}

	public static void addNeighbor() {
		System.out.println("\n\n");
		System.out.println("**Please enter the coordinate of neighborhood: ");

		int[] x = new int[2];
		System.out.print("> X0: ");
		x[0] = getNumber("X0");
		System.out.print("> X1: ");
		x[1] = getNumber("X1");

		while (x[0] == x[1]) {
			System.out.println("** X0 and X1 cannot be same **");
			System.out.print("> X1: ");
			x[1] = getNumber("X1");
		}

		int[] y = new int[2];
		System.out.print("> Y0: ");
		y[0] = getNumber("Y0");
		System.out.print("> Y1: ");
		y[0] = getNumber("Y1");

		while (y[0] == y[1]) {
			System.out.println("** Y0 and Y1 cannot be same **");
			System.out.print("> Y1: ");
			y[1] = getNumber("Y1");
		}
		System.out.print("> name: ");
		String name = getString("name");

		if (neighborhoods.search(name) != null ) {
			System.out.println("** There is already a neighbor exist with name " + name);
			return;
		}

		if (neighborhoods.search(x, y) != null) {
			System.out.println("There is already a neighborhood exist with These coordinate.");
			return;
		}

		NeighborNode n = new NeighborNode(x, y, name);
		neighborhoods.insert(n);
		commands.push("addN x0=" + x[0] + " x1=" + x[1] + " y0=" + y[0] + " y1=" + y[1] + " name=" + name );
		return;
	}

	public static void addBank() {
		System.out.println("** Please enter information required: ");
		int[] coordinate = new int[2];
		System.out.print("> X: ");
		coordinate[0] = getNumber("X");
		System.out.print("> Y: ");
		coordinate[1] = getNumber("Y");
		
		if (banksKdTree.search(coordinate) != null) {
			System.out.println("** There is a << bank >> already exist with this coordinates");
			return;
		}

		if (branchesKdTree.search(coordinate) != null) {
			System.out.println("** There is a << branch >> already exist with this coordinates");
			return;
		}

		System.out.print("> name: ");
		String name = getString("name");

		if (bankTrieTree.search(name) != null) {
			System.out.println("** There is already a bank exists with name " + name);
			return;
		}

		NeighborNode[] neighborNodes = neighborhoods.searchArea(coordinate[0], coordinate[1]);
		if (neighborNodes.length == 0 || neighborNodes[0] == null) {
			System.out.println("*** There is no neighborhood in coordinate that you entered");
			return;
		}
		Bank b = new Bank(coordinate, name);
		for (NeighborNode x : neighborNodes) {
			if (x != null) {
				x.banks.addPoint(coordinate, b);
			}
		}
		bankTrieTree.insert(name, b);
		banksKdTree.addPoint(coordinate, b);
		commands.push("addB x=" + coordinate[0] + " y=" + coordinate[1] + " name=" + name);
		return;
	}

	public static void addBranch() {
		System.out.println("** Please enter information required: ");
		int[] coordinate = new int[2];
		System.out.print("> X: ");
		coordinate[0] = getNumber("X");
		System.out.print("> Y: ");
		coordinate[1] = getNumber("Y");
		if (banksKdTree.search(coordinate) != null) {
			System.out.println("** There is already a <<bank>> exist with this coordinates");
			return;
		}
		
		if (branchesKdTree.search(coordinate) != null) {
			System.out.println("** There is already a <<branch>> exist with this coordinates");
			return;
		}

		System.out.print("> bank name: ");
		String bankName = getString("bank name");
		Bank b = bankTrieTree.search(bankName);
		if (b == null) {
			System.out.println("** There is no bank exist with name " + bankName);
			return;
		}

		System.out.print("> branch name: ");
		String branchName = getString("branch name");
		if (branchTrieTree.search(branchName) != null) {
			System.out.println("** There is already a branch exists with name " + branchName);
		}

		Branch branch = new Branch(coordinate, branchName, bankName);
		branchTrieTree.insert(branchName, branch);
		branchesKdTree.addPoint(coordinate, branch);
		b.addBranch(branch);
		commands.push("addBr x=" + coordinate[0] + " y=" + coordinate[1] + " name=" + branchName);
		return;
	}

	public static void deleteBranch() {
		int[] coordinate = new int[2];
		System.out.println("** Please enter information required: ");
		System.out.print("> X: ");
		coordinate[0] = getNumber("X");
		System.out.print("> Y: ");
		coordinate[1] = getNumber("Y");

		Branch branch = branchesKdTree.search(coordinate).data;
		if (branch == null) {
			System.out.println("** There is no branch with given coordinate");
			return;
		}

		Bank b = bankTrieTree.search(branch.getBankName());
		System.out.println(b);
		b.deleteBranch(branch);
		branchesKdTree.delete(coordinate);
		branchTrieTree.delete(branch.getName());
		commands.push("delBr x=" + coordinate[0] + " y=" + coordinate[1] + " name=" + branch.getName() + " bankName=" + branch.getBankName());
	}

	public static void neighborhoodBankList() {
		System.out.println("** Please enter information required: ");
		System.out.print("> neighborhood name: ");
		String name = getString("neighborhood name");

		NeighborNode n = neighborhoods.search(name);
		if (n == null) {
			System.out.println("** no neighbor found with name " + name);
			return;
		}
		n.banks.printAll();
	}

	public static void bankBranchesList() {
		System.out.println("** Please enter information required: ");
		System.out.print("> bank name: ");
		String name = getString("bank name");
		Bank b = bankTrieTree.search(name);
		if (b == null) {
			System.out.println("no bank found with name " + name);
			return;
		}
		GenericArray<Branch> nodes =  b.branches.getAllNodes();
		for (int i = 0; i < nodes.length; i++) {
			if (nodes.get(i) != null) {
				System.out.println(nodes.get(i));
			}
		}
	}

	public static void nearestToMe(String type) {
		int[] coordinate = new int[2];
		System.out.println("\n** Please enter information required");
		System.out.print("> X: ");
		coordinate[0] = getNumber("X");
		System.out.print("> Y: ");
		coordinate[1] = getNumber("Y");
		System.out.print("The nearest bank to point given is: ");
		if (type.equals("bank")) {
			System.out.println(banksKdTree.nearest(coordinate).data);
		} else {
			KdNode<Branch> nb = branchesKdTree.nearest(coordinate);
			System.out.println(nb.data);
		}
	}

	public static void findBankInRange() {
		int[] coordinate = new int[2];
		System.out.println("\n** Please enter information required");
		System.out.print("> X: ");
		coordinate[0] = getNumber("X");
		System.out.print("> Y: ");
		coordinate[1] = getNumber("Y");
		System.out.print("> range: ");
		int range = getNumber("range");

		System.out.print("Available banks are: ");
		GenericArray<Bank> availBanks = banksKdTree.findAllInRange(coordinate, range);
		for (int i = 0; i < availBanks.length; i++) {
			if (availBanks.get(i) == null) {
				break;
			} else {
				System.out.println(availBanks.get(i));
			}
		}
	}

	public static void bankWithLargestBranch() {
		GenericArray<Bank> allBanks = banksKdTree.getAllNodes();
		Bank mostBank = bankTrieTree.search(allBanks.get(0).getName());
		for (int i = 1; i < allBanks.length && allBanks.get(i) != null; i++) {
			Bank x = bankTrieTree.search(allBanks.get(i).getName());
			if (x != null) {
				if (x.getBranchNumber() > mostBank.getBranchNumber()) {
					mostBank = x;
				}
			}
		}

		System.out.println(mostBank);
	}

	public static void famousBank() {
		GenericArray<Branch> totalBranches = branchesKdTree.getAllNodes();

		String[] bankNames = new String[totalBranches.length];
		int[] branchVisitedTotal = new int[totalBranches.length];

		for (int index = 0; index < totalBranches.length; index++) {
			Branch branch = branchTrieTree.search(totalBranches.get(index).getName());
			if (branch == null)
				break;
			for (int i = 0; i < totalBranches.length; i++) {
				if (bankNames[i] == null || bankNames[i].equals("")) {
					bankNames[i] = branch.getBankName();
					branchVisitedTotal[i] += branch.getVisited();
				} else if (bankNames[i].equals(branch.getBankName())) {
					branchVisitedTotal[i] += branch.getVisited();
				}
			}
		}

		int index = 0;
		for (int i = 1; i < totalBranches.length; i++) {
			if (branchVisitedTotal[i] > branchVisitedTotal[index]) {
				index = i;
			}
		}

		if (bankNames[index] != null && !bankNames[index].equals("")) {
			System.out.println(bankTrieTree.search(bankNames[index]));
		}
	}

	public static void undo(int until) {
		String done = "";
		for (int i = 0; i < until; i++) {
			done = commands.pop();
			String command = done.substring(0, done.indexOf(" "));

			if (command.equals("addN")) {
				int[] x = {
					Integer.parseInt(done.substring(done.indexOf("x0=") + 3, done.indexOf(" x1="))),
					Integer.parseInt(done.substring(done.indexOf("x1=") + 3, done.indexOf(" y0=")))
				};
				int[] y = {
					Integer.parseInt(done.substring(done.indexOf("y0=") + 3, done.indexOf(" y1="))),
					Integer.parseInt(done.substring(done.indexOf("y1=") + 3, done.indexOf(" name=")))
				};
				String name = done.substring(done.indexOf("name=") + 5);
				neighborhoods.delete(x, y);
			}
			else if (command.equals("addB")) {
				int[] coordinate = {
					Integer.parseInt(done.substring(done.indexOf("x=") + 2, done.indexOf(" y"))),
					Integer.parseInt(done.substring(done.indexOf("y=") + 2, done.indexOf(" name=")))
				};
				String name = done.substring(done.indexOf("name=") + 5);
				NeighborNode[] nn = neighborhoods.searchArea(coordinate[0], coordinate[1]);
				for (NeighborNode neighborNode : nn) {
					if (neighborNode == null) {
						break;
					}
					neighborNode.banks.delete(coordinate);
				}
				bankTrieTree.delete(name);
				banksKdTree.delete(coordinate);
			}
			else if (command.equals("addBr")) {
				int[] coordinate = {
					Integer.parseInt(done.substring(done.indexOf("x=") + 2, done.indexOf(" y"))),
					Integer.parseInt(done.substring(done.indexOf("y=") + 2, done.indexOf(" name=")))
				};
				String name = done.substring(done.indexOf("name=") + 5);
				Branch bb = branchTrieTree.search(name);
				branchTrieTree.delete(name);
				branchesKdTree.delete(coordinate);
				(bankTrieTree.search(bb.getBankName())).deleteBranch(bb);
			}
			else if (command.equals("delBr")) {
				int[] coordinate = {
					Integer.parseInt(done.substring(done.indexOf("x=") + 2, done.indexOf(" y"))),
					Integer.parseInt(done.substring(done.indexOf("y=") + 2, done.indexOf(" name=")))
				};
				String branchName = done.substring(done.indexOf("name=") + 5, done.indexOf(" bankName="));
				String bankName = done.substring(done.indexOf("bankName=") + 9);
				Branch bb = new Branch(coordinate, branchName, bankName);
				Bank b = bankTrieTree.search(bankName);
				b.addBranch(bb);
				branchesKdTree.addPoint(coordinate, bb);
				branchTrieTree.insert(branchName, bb);
			}
		}
	}

	public static void getCommand() {
		System.out.println("\nPlease enter your command: ");
		System.out.print("> ");
		String command = in.nextLine();
		if (command.equalsIgnoreCase("q") || command.equalsIgnoreCase("quit")) {
			System.out.println("\n bye bye \n");
			System.exit(0);
		}

		if (command.equals("addN")) {
			addNeighbor();
		}
		else if (command.equals("addB")) {
			addBank();
		}
		else if (command.equals("addBr")) {
			addBranch();
		}
		else if (command.equals("delBr")) {
			deleteBranch();
		}
		else if (command.equals("listB")) {
			neighborhoodBankList();
		}
		else if (command.equals("listBrs")) {
			bankBranchesList();
		}
		else if (command.equals("nearB")) {
			nearestToMe("bank");
		}
		else if (command.equals("nearBr")) {
			nearestToMe("branch");
		}
		else if (command.equals("availB")) {
			findBankInRange();
		}
		else if (command.equals("mostBr")) {
			bankWithLargestBranch();
		}
		else if (command.equals("fameB")) {
			famousBank();
		}
		else if (command.indexOf("undo ") == 0) {
			String number = command.substring(command.indexOf(" ") + 1);
			if (number.matches(".*[^0-9].*")) {
				System.out.println("*** you need to enter a number after undo");
			} else {
				undo(Integer.parseInt(number));
			}
		}
		else if (command.equalsIgnoreCase("clear")) {
			System.out.print("\033[H\033[2J"); // this line will clear console.
			System.out.flush();
		}
		else if (command.equals("h") || command.equals("help") || command.equals("?")) {
			showHelp();
		}
		else {
			System.out.println("\n***Sorry your command not found please try again");
		}
	}

	public static int getNumber(String message) {
		String input = in.nextLine();
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e) {
			System.out.println("** You need to enter a number **");
			System.out.print("> " + message + ": ");
			return getNumber(message);
		}
	}

	public static String getString(String message) {
		String x = in.nextLine();
		if(x.matches(".*[^A-Za-z0-9].*")) {
			System.out.println("** Your string must be in [A-Z], [a-z], [0-9] **");
			System.out.print("> " + message + ": ");
			return getString(message);
		} else {
			return x;
		}
	}

	public static void showHelp() {
		System.out.println("\n\n");
		System.out.println("\t-Here is commands that you can enter: \n");
		System.out.println("> addN => this command will add new neighborhood");
		System.out.println("> addB => this command will add new main bank");
		System.out.println("> addBr => this command will add new branch to main bank");
		System.out.println("> delBr => this command will remove a branch");
		System.out.println("> listB => this command will show all banks in a neighborhood");
		System.out.println("> listBrs => this command will show all coordinate of bank branches");
		System.out.println("> nearB => this command will show nearest bank to you");
		System.out.println("> nearBr => this command will show nearest bank branch to you");
		System.out.println("> availB => this command will show all available banks in radius of R from you");
		System.out.println("> mostBr => this command will show the bank that have most branch of all");
		System.out.println("> fameB => this command will show the most visited bank of all");
		System.out.println();
		System.out.println("(*) undo P => you can use this command to undo the commands to you entered");
		System.out.println("(*) h or help or ? => show help");
		System.out.println("(*) clear => clear screen");
		System.out.println("(*) q or quit => exits the program");
	}

	public static void printWelcome() {
		System.out.print("\033[H\033[2J"); // this line will clear console.
		System.out.flush();
		System.out.println("\t\tM#########M MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM  MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM ");
		System.out.println("\t\tM#\"\"\"\"\"\"\"'M MMP\"\"\"\"\"\"\"MMM\"\"\"\"\"\"\"`YMM\"\"MMMMM\"\"M  MM\"\"\"\"\"\"\"\"`MM\"\"MM\"\"\"\"\"\"\"`YMM\"\"\"\"\"\"'YMMMM\"\"\"\"\"\"\"\"`MMM\"\"\"\"\"\"\"`MM ");
		System.out.println("\t\t##  mmmm. `MM' .mmmm  MMM  mmmm.  MM  MMMM' .M  MM  mmmmmmmMM  MM  mmmm.  MM  mmmm. `MMM  mmmmmmmMMM  mmmm,  M ");
		System.out.println("\t\t#'        .MM         `MM  MMMMM  MM       .MM  M'      MMMMM  MM  MMMMM  MM  MMMMM  MM`      MMMMM'        .M ");
		System.out.println("\t\tM#  MMMb.'YMM  MMMMM  MMM  MMMMM  MM  MMMb. YM  MM  MMMMMMMMM  MM  MMMMM  MM  MMMMM  MMM  MMMMMMMMMM  MMMb. \"M ");
		System.out.println("\t\tM#  MMMM'  MM  MMMMM  MMM  MMMMM  MM  MMMMb  M  MM  MMMMMMMMM  MM  MMMMM  MM  MMMM' .MMM  MMMMMMMMMM  MMMMM  M ");
		System.out.println("\t\tM#       .;MM  MMMMM  MMM  MMMMM  MM  MMMMM  M  MM  MMMMMMMMM  MM  MMMMM  MM       .MMMM        .MMM  MMMMM  M ");
		System.out.println("\t\tM#########M MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM  MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM ");
	}
}