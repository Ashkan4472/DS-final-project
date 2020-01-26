package Classes.Neighbor;

import Classes.Bank;

public class Neighborhood {
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
		for (int i = 0; i < this.currentIndex; i++) {
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
		for (int i = 0; i < this.currentIndex; i++) {
			if (neighbors[i].getName().equals(name)) {
				return neighbors[i];
			}
		}
		return null;
	}

	public NeighborNode[] searchArea(int x, int y) {
		NeighborNode[] result = new NeighborNode[this.currentIndex];
		int counter = -1;
		for (int i = 0; i < this.currentIndex; i++) {
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
		for (int i = 0; i < this.currentIndex; i++) {
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

		for (int i = foundIndex; i < this.currentIndex - 1; i++) {
			this.neighbors[i] = this.neighbors[i + 1];
		}
		this.neighbors[currentIndex] = null;
		this.currentIndex--;
	}
}