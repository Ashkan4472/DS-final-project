package Nodes;

/**
 * @author      Ashkan Tofangdar <address @ example.com>
 */

public class KdNode <E> {
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

	public int[] getCoordinate() {
		int[] result = {this.x, this.y};
		return result;
	}

	@Override
	public String toString() {
		return this.data.toString();
	}

}