package springboot.sm.domain.updateform;

import lombok.Data;

@Data
public class PwUpdate {

    private String loginId;
    private String password;
    private String changePw;
}
