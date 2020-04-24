package dtos;

import lombok.Data;

@Data
public class HueConfig {
    public String archetype;
    public String function;
    public String direction;
    public HueConfigStartup startup;
}
