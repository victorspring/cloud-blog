package life.maxima.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean enabled;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CredentialsDto> credentials;

    public UserDto(String username) {
        this.username = username;
    }
}
