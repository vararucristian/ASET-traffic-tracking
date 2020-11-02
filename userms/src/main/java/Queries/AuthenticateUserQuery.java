package Queries;

public class AuthenticateUserQuery implements Query {

private String userName;
private String password;

public AuthenticateUserQuery(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
