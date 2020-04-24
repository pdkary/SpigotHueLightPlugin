package serializers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.HueLight;

import java.io.IOException;

public class HueLightSerializer {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static HueLight serialize(JsonNode hueLightJson) throws IOException {
        return mapper.readValue(hueLightJson.toString(), HueLight.class);
    }
}
