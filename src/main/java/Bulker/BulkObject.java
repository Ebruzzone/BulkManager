package Bulker;

public abstract class BulkObject<T> {

	protected T content;

	protected BulkObject(T content) {
		this.content = content;
	}

	public abstract BulkObject<T> union(BulkObject<T> other);

	public abstract void exec();

	public abstract long length();
}
