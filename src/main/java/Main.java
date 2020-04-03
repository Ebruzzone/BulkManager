import Bulker.BulkManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.async.AsyncLoggerContextSelector;

public class Main {

	public static org.apache.logging.log4j.core.async.AsyncLogger logger;

	public static void main(String[] args) {

		System.setProperty("Log4jContextSelector", AsyncLoggerContextSelector.class.getName());
		logger = (org.apache.logging.log4j.core.async.AsyncLogger) LogManager.getLogger();

		BulkManager<StringBuilder> manager = new BulkManager<>(700L, 10000, 50000, 32);

		for (int i = 0; i < 10000000; i++) {
			manager.add(new LogExample(new StringBuilder("ciao")));
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		manager.kill();
	}
}
