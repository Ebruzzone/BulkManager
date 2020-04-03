import Bulker.BulkObject;

public class LogExample extends BulkObject<StringBuilder> {

	public LogExample(StringBuilder content) {
		super(content);
	}

	public BulkObject<StringBuilder> union(BulkObject<StringBuilder> other) {

		LogExample o = (LogExample) other;
		content.append(o.content).append("£");
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
