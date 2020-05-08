package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HueCapabilityControl {
    @JsonProperty("mindimlevel")
    public int minDimLevel;
    @JsonProperty("maxlumen")
    public int maxLumen;
}
