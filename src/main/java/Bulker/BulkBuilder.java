package Bulker;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

class BulkBuilder<T> implements RunnableKillable {

	private AtomicBoolean alive;
	private BulkExecutor<T> executor;
	private BulkManager<T> manager;
	private ConcurrentLinkedQueue<BulkObject<T>> objects;
	private LinkedList<BulkObject<T>> prepObjects;
	private AtomicLong size;

	BulkBuilder(BulkManager<T> manager) {
		this.manager = manager;
		this.executor = new BulkExecutor<>();

		objects = new ConcurrentLinkedQueue<>();
		prepObjects = new LinkedList<>();

		alive = new AtomicBoolean(true);
		size = new AtomicLong(0);
		new Thread(executor).start();
	}

	public void kill() {
		alive.set(false);
		executor.kill();

		synchronized (this){
			this.notify();
		}
	}

	void add(BulkObject<T> object) {
		objects.add(object);
	}

	public void run() {

		long sizeTemp, len;

		while (alive.get()) {

			manager.builderWait();

			sizeTemp = objects.size();
			len = 0;

			for (long i = 0; i < sizeTemp; i++) {
				BulkObject<T> s = objects.poll();
				prepObjects.addLast(s);
				len += s == null ? 0 : s.length();
			}

			if (manager.isEnough(size.addAndGet(len), len)) {

				executor.exec(manager.union(prepObjects));

				prepObjects.clear();
				size.set(0);
			}
		}
	}
}
