package Nodes;

/**
 * @author      Ashkan Tofangdar <address @ example.com>
 */

public class KdNode <E> {
	public E data;
	public KdNode<E> left;
	public KdNode<E> right;

	/**
	 * Constructor of KdNode.
	 * <p>
	 * this is KdNode constructor that is mostly used in KdTree
	 * it has a data and a left and right child witch are public
	 * </p>
	 */
	KdNode() {
		this.left = null;
		this.right = null;
	}

	/**
	 * Constructor of KdNode.
	 * <p>
	 * this is KdNode constructor that is mostly used in KdTree
	 * it has a data and a left and right child witch are public
	 * </p>
	 * 
	 * @param data is the data that KdNode saves inside
	 */
	KdNode(E data) {
		this.data = data;
		this.left = null;
		this.right = null;
	}

	@Override
	public String toString() {
		return this.data.toString();
	}

}