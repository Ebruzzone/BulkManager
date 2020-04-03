import Bulker.BulkObject;

import kong.unirest.Unirest;
import org.json.JSONArray;

public class ApiPostExample extends BulkObject<JSONArray> {

	public ApiPostExample(JSONArray content) {
		super(content);
	}

	@Override
	public BulkObject<JSONArray> union(BulkObject<JSONArray> other) {

		ApiPostExample a = (ApiPostExample) other;

		for (int i = 0; i < other.length(); i++) {
			content.put(a.content.getJSONObject(i));
		}

		return this;
	}

	@Override
	public void exec() {

		Unirest.post("https://example/url").body(content).asString();
	}

	@Override
	public long length() {
		return content.length();
	}
}
