package dtos;

import lombok.Data;

@Data
public class HueLightCapabilities {
    public boolean certified;
    public HueCapabilityControl control;
    public HueCapabilityStreaming streaming;
}
