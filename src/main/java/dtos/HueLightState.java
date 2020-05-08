package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

@Data
public class HueLightState {
    @Nullable
    public String hue;
    public boolean on;
    @JsonProperty("bri")
    public int brightness;
    @Nullable
    public String alert;
    @Nullable
    public String mode;
    @Nullable
    public Boolean reachable;
}
