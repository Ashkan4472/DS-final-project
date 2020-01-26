package Structures;

public class Stack {
	// TODO: make this generic type too.
	private static final int MAX = 1000;
	private String a[];
	private int currentIndex;

	Stack() {
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