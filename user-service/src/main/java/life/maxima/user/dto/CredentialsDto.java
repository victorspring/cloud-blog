package life.maxima.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CredentialsDto {

    private boolean temporary;
    private String type;
    private String value;

}
