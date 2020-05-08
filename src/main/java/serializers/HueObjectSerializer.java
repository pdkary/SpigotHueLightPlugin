package serializers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.HueLight;
import dtos.HueSimpleResponseDto;

import java.io.IOException;

public class HueObjectSerializer {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static HueLight toLight(JsonNode hueLightJson) throws IOException {
        return mapper.readValue(hueLightJson.toString(), HueLight.class);
    }

    public static HueSimpleResponseDto toSimpleResponse(JsonNode jsonNode) throws IOException {
        return mapper.readValue(jsonNode.toString(),HueSimpleResponseDto.class);
    }
}
