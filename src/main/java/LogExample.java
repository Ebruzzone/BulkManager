import Bulker.BulkObject;

public class LogExample extends BulkObject<LogExample, StringBuilder> {

	public LogExample(StringBuilder content) {
		super(content);
	}

	public LogExample join(LogExample other) {

		content.append(other.content).append("£");
		return this;
	}

	public void exec() {
		Main.logger.error(content.toString());
	}

	@Override
	public long length() {
		return content.length();
	}
}
