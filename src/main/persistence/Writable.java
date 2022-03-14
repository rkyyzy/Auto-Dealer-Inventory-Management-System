package persistence;

import org.json.JSONObject;

// This class is modeled from sample "JsonSerializationDemo"
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}