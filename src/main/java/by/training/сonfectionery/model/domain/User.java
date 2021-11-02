package by.training.сonfectionery.model.domain;

public class User extends Entity {
    private String firstName;
    private String lastName;
    private String email;
    private String image = null;
    private Role role;
    private Status status;

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setRole(Role role) {
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

    public Status getStatus() {
        return status;
    }

    public Role getRole() {
        return role;
    }

    public String getImage() {
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
        private final String value;
        private final int id;

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
        private final String value;
        private final int id;

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


    public static class UserBuilder {
        User user;

        public UserBuilder() {
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
        StringBuilder stringBuilder = new StringBuilder("User{");
        stringBuilder.append("id=").append(getId());
        stringBuilder.append(", firstName=").append(firstName);
        stringBuilder.append(", lastName=").append(lastName);
        stringBuilder.append(", email=").append(email);
        stringBuilder.append(", role=").append(role.value);
        stringBuilder.append(", status=").append(status.value);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (firstName == null ? 0 : firstName.hashCode());
        result = 31 * result + (lastName == null ? 0 : lastName.hashCode());
        result = 31 * result + (email == null ? 0 : email.hashCode());
        result = 31 * result + (image == null ? 0 : image.hashCode());
        result = 31 * result + (role == null ? 0 : role.hashCode());
        result = 31 * result + (status == null ? 0 : status.hashCode());
        return result;
    }

    @Override

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        User user = (User) o;
        return user.getId() == getId() &&
                user.firstName == null ? firstName == null : firstName.equals(user.firstName) &&
                user.lastName == null ? lastName == null : lastName.equals(user.lastName) &&
                user.email == null ? email == null : email.equals(user.email) &&
                user.role == null ? role == null : role.equals(user.role) &&
                user.status == null ? status == null : status.equals(user.status) &&
                user.image == null ? image == null : image.equals(user.image);
    }


}
