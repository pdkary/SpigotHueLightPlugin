package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dtos.HueLight;
import dtos.HueSimpleResponseDto;
import dtos.HueUsernameResponseDto;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import serializers.HueObjectSerializer;
import utils.InventoryHelper;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class HueHttpController {

    private static final String hue_id = "hue_id";
    public static final Header CONTENT_HEADER = new BasicHeader("Content-Type", "application/json");
    public static final Header CHARSET_HEADER = new BasicHeader("charset", "utf-8");
    private static final String username_data = "{\"devicetype\":\"my_hue_app#minecraft steve\"}";

    private static final ObjectMapper mapper = new ObjectMapper();
    public static Material TRIGGER = Material.REDSTONE_LAMP;
    private final String username;
    private final Logger logger;
    private String ip;

    public HueHttpController(String username, Logger logger) {
        this.logger = logger;
        this.username = username;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() throws IOException {
        if (ip == null) {
            logger.warning("No IP address set! Use a quartz block with a sign");
            return null;
        }
        HttpResponse response = Request.Post(
                new StringBuilder()
                        .append("http://")
                        .append(ip)
                        .append("/api/")
                        .toString())
                .setHeaders(CHARSET_HEADER, CONTENT_HEADER)
                .connectTimeout(1000)
                .socketTimeout(1000)
                .bodyString(username_data, ContentType.APPLICATION_JSON)
                .execute().returnResponse();

        Integer responseCode = response.getStatusLine().getStatusCode();


        if (responseCode != 200) {
            logger.warning("Error from Phillips Hue API: " + response.getStatusLine().getStatusCode());
            return null;
        }
        List<HueSimpleResponseDto> responses = getResponses(response);
        String username = responses
                .stream()
                .map(s -> s.success)
                .filter(Objects::nonNull)
                .map(s -> ((HueUsernameResponseDto) s).username)
                .findFirst().orElse("");
        return username;
    }

    public List<HueLight> getLights() throws IOException {
        ArrayList<HueLight> result = new ArrayList<>();
        if (ip == null) {
            logger.warning("No IP address set! Use a quartz block with a sign");
            return result;
        }
        HttpResponse response = Request.Get(
                new StringBuilder()
                        .append("http://")
                        .append(ip)
                        .append("/api/")
                        .append(username)
                        .append("/lights")
                        .toString())
                .setHeaders(CONTENT_HEADER, CHARSET_HEADER)
                .connectTimeout(1000)
                .socketTimeout(1000)
                .execute()
                .returnResponse();

        Integer responseCode = response.getStatusLine().getStatusCode();
        String content = EntityUtils.toString(response.getEntity());

        if (responseCode != 200) {
            logger.warning("Error from Phillips Hue API: " + response.getStatusLine().getStatusCode());
            return result;
        }
        JsonNode jsonNode = mapper.readTree(content);
        Iterator jsonIterator = jsonNode.fields();
        while (jsonIterator.hasNext()) {
            Map.Entry entry = (Map.Entry) jsonIterator.next();
            ObjectNode value = (ObjectNode) entry.getValue();
            value.put("ID", (String) entry.getKey());
            result.add(HueObjectSerializer.toLight(value));
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

    public boolean toggleLight(String id) throws IOException {
        HueLight light = getLightByID(id);
        return toggleLight(light);
    }

    public boolean toggleLight(HueLight light) throws IOException {
        if (light == null) return false;
        String urlString = "http://" + ip + "/api/" + username + "/lights/" + light.ID + "/state";
        String putData = "{\"hue\":50000,\"on\":" + !light.state.on + ",\"bri\":200}";
        HttpResponse response = Request.Put(urlString)
                .connectTimeout(1000)
                .socketTimeout(1000)
                .setHeaders(CONTENT_HEADER, CHARSET_HEADER)
                .bodyString(putData, ContentType.APPLICATION_JSON)
                .execute().returnResponse();
        Integer responseCode = response.getStatusLine().getStatusCode();
        if (responseCode != 200) {
            logger.warning("Error from Phillips Hue API: " + response.getStatusLine().getStatusCode());
            return false;
        }
        return getResponses(response)
                .stream()
                .allMatch(HueSimpleResponseDto::isSuccessful);
    }

    private List<HueSimpleResponseDto> getResponses(HttpResponse response) throws IOException {
        JsonNode entity = mapper.readTree(EntityUtils.toString(response.getEntity()));
        List<HueSimpleResponseDto> responseDtos = new ArrayList<>();
        for (JsonNode jsonNode : entity) {
            HueSimpleResponseDto hueSimpleResponseDto = HueObjectSerializer.toSimpleResponse(jsonNode);
            responseDtos.add(hueSimpleResponseDto);
        }
        return responseDtos;
    }

    public Inventory getHueInventoryMenu() throws IOException {
        return InventoryHelper.getInventory(getLights(), TRIGGER);
    }

}
