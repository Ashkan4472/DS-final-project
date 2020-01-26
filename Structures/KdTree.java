package Structures;

/**
 * @author      Ashkan Tofangdar <address @ example.com>
 */

import Nodes.KdNode;

public class KdTree <E> {
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
			if (node.getCoordinate()[xORy] >= coordinate[xORy]) {
				node.right = addPointLogic(node.right, coordinate, data, layer + 1);
			} else {
				node.left = addPointLogic(node.left, coordinate, data, layer + 1);
			}
			return node;
		} else {
			return new KdNode<E>(data, coordinate[0], coordinate[1]);
		}
	}
}
