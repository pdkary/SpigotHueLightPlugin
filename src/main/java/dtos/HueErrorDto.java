package dtos;

import lombok.Data;

@Data
public class HueErrorDto {
    Integer type;
    String address;
    String description;
}
