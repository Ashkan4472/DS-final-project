package Structures;

public class GenericArray<E> {

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
