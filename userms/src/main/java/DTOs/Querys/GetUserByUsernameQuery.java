package DTOs.Querys;

public class GetUserByUsernameQuery {
    private String userName;

    public GetUserByUsernameQuery(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
