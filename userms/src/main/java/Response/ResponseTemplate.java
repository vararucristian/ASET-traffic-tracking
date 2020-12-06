package Response;

public class ResponseTemplate {
    String fname;
    String lname;
    String username;
    boolean success;

    public ResponseTemplate(String fname, String lname, String username, boolean success) {
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.success = success;
    }
}
