package Model;

import java.util.Objects;

public class User {
    private final String name;
    private final String emailAddress;
    private final String password;
    private final boolean moderator;

    public User(String name, String emailAddress, String password, boolean moderator) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.password = password;
        this.moderator = moderator;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public boolean isModerator() {
        return moderator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(emailAddress, user.emailAddress) && Objects.equals(password, user.password);
    }

    @Override
    public String toString() {
        return name;
    }
}
