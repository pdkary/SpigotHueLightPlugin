package dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
<<<<<<< HEAD
=======
import com.garbagedump.exe;
>>>>>>> 06c91b0b3998d4b14ceab3ef80c4bcc25e38f649
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
