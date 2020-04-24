package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HueLightState {
    public boolean on;
    @JsonProperty("bri")
    public int brightness;
    public String alert;
    public String mode;
    public boolean reachable;
}
