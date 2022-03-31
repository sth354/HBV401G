package Model;

public class User {
    private String name;
    private String emailAddress;
    private String password;
    private boolean moderator;

    public User(String name, String emailAddress, String password, boolean moderator) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.password = password;
        this.moderator = moderator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isModerator() {
        return moderator;
    }

    public void setModerator(boolean moderator) {
        this.moderator = moderator;
    }
}
