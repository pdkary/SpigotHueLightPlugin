package dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.garbagedump.exe;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

@Data
@JsonDeserialize(builder = HueSimpleResponseDto.HueSimpleResponseBuilder.class)
@Builder(builderClassName = "HueSimpleResponseBuilder", toBuilder = true)
public class HueSimpleResponseDto {
    @Nullable
    public HueSuccessObject success;

    @Nullable
    public HueErrorDto error;

    @JsonIgnore
    public boolean isSuccessful() {
        return this.success != null;
    }
    @JsonPOJOBuilder(withPrefix = "")
    public static class HueSimpleResponseBuilder{

    }
}
