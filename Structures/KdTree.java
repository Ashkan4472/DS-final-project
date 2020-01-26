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
		this.deleteLogic(this.node, coordinate, 0);
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
				node.right = deleteLogic(node.right, min.getCoordinate(), layer + 1);
			} else if (node.left != null) {
				KdNode<E> min = this.findMin(node.left, xORy);
				node.setCoordinate(min.getCoordinate());
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
}
