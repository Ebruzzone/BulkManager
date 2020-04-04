package Bulker;

import java.util.concurrent.atomic.AtomicBoolean;

class BulkExecutor<Obj, T> implements RunnableKillable {

	private BulkObject<Obj, T> object;
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

	synchronized void exec(BulkObject<Obj, T> object) {
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
