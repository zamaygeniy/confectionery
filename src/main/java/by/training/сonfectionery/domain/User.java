package by.training.сonfectionery.domain;

import java.io.InputStream;

public class User extends Entity {
    private String firstName;
    private String lastName;
    private String email;
    private String image = null;
    private Role role;
    private Status status;


    public void setStatus(Status status){
        this.status = status;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus(){
        return status;
    }

    public Role getRole(){
        return role;
    }

    public String getImage(){
        return image;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public User() {
    }

    public enum Role {
        ADMIN("admin", 1),
        USER("user", 2),
        GUEST("guest", 3);
        private String value;
        private int id;

        Role(String value, int id) {
            this.value = value;
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public int getId() {
            return id;
        }
    }

    public enum Status {
        NON_ACTIVATED("non_activated", 1),
        ACTIVATED("activated", 2),
        BLOCKED("blocked", 3);
        private String value;
        private int id;

        Status(String value, int id) {
            this.id = id;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getId() {
            return id;
        }
    }


    public static class UserBuilder{
        User user;

        public UserBuilder(){
            user = new User();
        }

        public UserBuilder setId(int id) {
            user.setId(id);
            return this;
        }

        public UserBuilder setFirstName(String firstName) {
            user.setFirstName(firstName);
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            user.setLastName(lastName);
            return this;
        }

        public UserBuilder setEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public UserBuilder setImage(String image) {
            user.setImage(image);
            return this;
        }

        public UserBuilder setRole(Role role) {
            user.setRole(role);
            return this;
        }

        public UserBuilder setStatus(Status status) {
            user.setStatus(status);
            return this;
        }

        public User createUser() {
            return user;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User{id=");
        sb.append(getId());
        sb.append(", firstName=");
        sb.append(firstName);
        sb.append(", lastName=");
        sb.append(lastName);
        sb.append(", email=");
        sb.append(email);
        sb.append(", role=");
        sb.append(role.value);
        sb.append(", status=");
        sb.append(status.value);
        sb.append("}");
        return sb.toString();
    }

}
