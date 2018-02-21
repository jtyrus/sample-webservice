package cb_service.sample;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MemberUtils {

	private static final ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Return memberId.
	 * @param name
	 * @return
	 */
	public long getMemberId(String name) {
		
		return name.hashCode()%System.currentTimeMillis();
		
	}
	
	public <T>JsonObject objectToJsonObject(T obj) {
		try {
			return JsonObject.fromJson(mapper.writeValueAsString(obj));
		} catch (Exception e) {
			return null;
		}
	}
	
	public <T> T jsonDocumentToObject(JsonDocument doc, Class<T> clazz) {
		
		try {
			return mapper.readValue(doc.content().toString(), clazz);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
}
