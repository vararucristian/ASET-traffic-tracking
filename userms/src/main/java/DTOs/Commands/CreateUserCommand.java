package DTOs.Commands;

import DTOs.DTOOperation;

public class CreateUserCommand extends DTOOperation {
    private String userName;
    private String password;
    private String confirmPassword;
    private String fName;
    private String lName;

    public CreateUserCommand(String userName, String password, String confirmPassword, String fName, String lName) {
        this.userName = userName;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }
}

