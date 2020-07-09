package com.excilys.formation.java.cdb.dtos;

public class UserDTO {
    
    private String id;
    private String username;
    private String password;
    private String role;
        
    /**
     * @param username
     * @param password
     * @param role
     */
    public UserDTO(String username, String password, String role) {
        super();
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + "]";
    }    
}
