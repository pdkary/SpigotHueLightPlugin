package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.squareup.okhttp.*;
import dtos.HueLight;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static serializers.HueLightSerializer.serialize;

public class HueHttpController {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final OkHttpClient client = new OkHttpClient();
    private final String username;
    private final Logger logger;
    private String ip;

    public HueHttpController(String ip, String username, Logger logger) {
        this.logger = logger;
        this.ip = ip;
        this.username = username;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<HueLight> getLights() throws IOException {
        ArrayList<HueLight> result = new ArrayList<>();
        String urlString = "http://" + ip + "/api/" + username + "/lights";
        URL url = new URL(urlString);
        Request request = new Request.Builder()
                .get()
                .url(urlString)
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            logger.warning("Error from Phillips Hue API: " + response.code());
            return result;
        } else {
            JsonNode jsonNode = mapper.readTree(response.body().string());
            Iterator jsonIterator = jsonNode.fields();
            while (jsonIterator.hasNext()) {
                Map.Entry entry = (Map.Entry) jsonIterator.next();
                ObjectNode value = (ObjectNode) entry.getValue();
                value.put("ID", (String) entry.getKey());
                result.add(serialize(value));
            }
        }
        return result;
    }

    public HueLight getLightByID(String id) throws IOException {
        return getLights()
                .stream()
                .filter(hueLight -> hueLight.ID.equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean toggleLight(String id) {
        try {
            HueLight light = getLightByID(id);
            toggleLight(light);
            return true;
        } catch (IOException | NullPointerException e) {
            return false;
        }
    }

    public boolean toggleLight(HueLight light) throws IOException {
        if (light == null) return false;
        String urlString = "http://" + ip + "/api/" + username + "/lights/" + light.ID + "/state";
        String putData = "{\"hue\":50000,\"on\":" + !light.state.on + ",\"bri\":200}";
        Request request = new Request.Builder()
                .url(urlString)
                .put(RequestBody.create(JSON, putData))
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            JsonNode jsonNode = mapper.readTree(response.body().string());
        }
        return true;
    }
}
