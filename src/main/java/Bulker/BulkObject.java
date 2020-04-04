package Bulker;

public abstract class BulkObject<Obj, T> {

	protected T content;

	protected BulkObject(T content) {
		this.content = content;
	}

	public abstract BulkObject<Obj, T> union(Obj other);

	public abstract void exec();

	public abstract long length();
}
