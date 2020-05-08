package dtos;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class HueToggleResponseDto implements HueSuccessObject {
    @JsonIgnore
    public static final String LIGHT_KEY_PATTERN = "\\/lights\\/(\\d)\\/state\\/([a-z]{2,3})";
    @JsonIgnore
    private static final Pattern PATTERN = Pattern.compile(LIGHT_KEY_PATTERN);
    @JsonAnySetter
    public Map<String, Object> dataMap =new LinkedHashMap<>();

//
//    @JsonAnySetter
//    void setData(String key, Object Value) {
//        Matcher matcher = PATTERN.matcher(key);
//        String lightId = matcher.group(1);
//        String type = matcher.group(2);
//        dataMap.put("id",lightId);
//        dataMap.put(type,Value);
//    }
}
