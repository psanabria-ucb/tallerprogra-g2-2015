package bo.edu.ucbcba.videoclub.model;

import javax.persistence.*;

/**
 * Created by privado on 30/05/2016.
 */
@Entity
@Table (name = "TABLE_OF_USERS")
public class User {
    @Id
    private String username;

    @Lob
    @Column
    private String password;

    @Column
    private int usertype;

    @Column
    private int isAuthenticated;

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

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public int getIsAuthenticated() {
        return isAuthenticated;
    }

    public void setIsAuthenticated(int isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }
}
