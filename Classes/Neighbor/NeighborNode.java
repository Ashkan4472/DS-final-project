package Classes.Neighbor;

import Classes.Bank;
import Structures.Tree.KdTree.KdTree;;

public class NeighborNode {
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