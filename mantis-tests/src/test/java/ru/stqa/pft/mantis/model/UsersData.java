package ru.stqa.pft.mantis.model;

public class UsersData {

    private int id = Integer.MAX_VALUE;

    private String username;
    private String name;
    private String email;

    public UsersData withId(int id) {
        this.id = id;
        return this;
    }

    public UsersData withUsername(String username) {
        this.username = username;
        return this;
    }

    public UsersData withName(String name) {
        this.name = name;
        return this;
    }

    public UsersData withEmail(String email) {
        this.email = email;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "UsersData{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersData usersData = (UsersData) o;

        if (id != usersData.id) return false;
        return username != null ? username.equals(usersData.username) : usersData.username == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }
}