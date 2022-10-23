package dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String nick;
    private String email;
    private String login;
    private String password;
    private String avatarUrl;

    public UserDTO(String nick, String email, String login, String password, String avatarUrl) {
        this.nick = nick;
        this.email = email;
        this.login = login;
        this.password = password;
        this.avatarUrl = avatarUrl;
    }
}
