package Bulker;

import java.util.concurrent.atomic.AtomicBoolean;

class BulkExecutor<T> implements RunnableKillable {

	private BulkObject<T> object;
	private AtomicBoolean alive;

	BulkExecutor() {
		alive = new AtomicBoolean(true);
	}

	public void kill() {
		alive.set(false);

		synchronized (this) {
			this.notify();
		}
	}

	synchronized void exec(BulkObject<T> object) {
		this.object = object;
		this.notify();
	}

	public synchronized void run() {

		while (alive.get()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (object != null) {
				object.exec();
				object = null;
			}
		}
	}
}
