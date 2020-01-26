package Classes;

/**
 * @author      Ashkan Tofangdar <ashkan4472@gmail.com>
 */

public class Branch {
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