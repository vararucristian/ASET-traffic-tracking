package Queries;

public class CreateUserQuery implements Query{
    private String userName;
    private String password;
    private String fName;
    private String lName;

    public CreateUserQuery(String userName, String password, String fName, String lName) {
        this.userName = userName;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }
}
