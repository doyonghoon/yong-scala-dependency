package yong.intern;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A helper class handling a http request to load dependencies.
 * */
public class DependencyRequest {
    public static List<String> requestDependency(String query) {
        List<String> res = new ArrayList<>();
        if (query.isEmpty()) {
            return res;
        }

        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get("http://search.maven.org/solrsearch/select")
                    .header("accept", "application/json")
                    .queryString("q", query)
                    .queryString("wt", "json")
                    .asJson();

            JsonNode json = jsonResponse.getBody();
            JSONArray array = json.getObject().getJSONObject("response").getJSONArray("docs");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                res.add(obj.getString("g") + " % " + obj.getString("a") + " % " + obj.getString("latestVersion"));
            }

        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return res;
    }
}
