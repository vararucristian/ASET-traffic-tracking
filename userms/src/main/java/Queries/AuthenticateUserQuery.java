package Queries;

<<<<<<< HEAD
public class AuthenticateQuery {
=======
public class AuthenticateUserQuery implements Query {
>>>>>>> eed65b5... API GATEWAY patter (FACADE) + Handler factory

    private String userName;
    private String password;

<<<<<<< HEAD
    public AuthenticateQuery(String userName, String password) {
=======
    public AuthenticateUserQuery(String userName, String password) {
>>>>>>> eed65b5... API GATEWAY patter (FACADE) + Handler factory
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
