package Data;

public class User {
    String userName;
    String passWord;
    String fName;
    String lName;
    String id;

    public User(String userName, String passWord, String fName, String lName, String id) {
        this.userName = userName;
        this.passWord = passWord;
        this.fName = fName;
        this.lName = lName;
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getId() {
        return id;
    }
}
