package Classes;

import Structures.Tree.KdTree.KdTree;

/**
 * @author Ashkan Tofangdar <ashkan4472@gmail.com>
 */

public class Bank {
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