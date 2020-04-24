package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@JsonDeserialize(builder = HueLight.HueLightBuilder.class)
@Builder(builderClassName = "HueLightBuilder", toBuilder = true)
public class HueLight {
    @JsonProperty("ID")
    public String ID;
    public String type;
    public String name;

    @JsonProperty("modelid")
    public String modelId;

    @JsonProperty("manufacturername")
    public String manufacturerName;

    @JsonProperty("productname")
    public String productName;

    @JsonProperty("uniqueid")
    public String uniqueId;

    @JsonProperty("swversion")
    public String swVersion;

    @JsonProperty("swconfigid")
    public String swConfigId;

    @JsonProperty("productid")
    public String productId;

    public HueLightState state;

    @JsonProperty("swupdate")
    public HueSwUpdate swUpdate;
    public HueLightCapabilities capabilities;
    public HueConfig config;

    @JsonPOJOBuilder(withPrefix = "")
    public static class HueLightBuilder {
    }
}
