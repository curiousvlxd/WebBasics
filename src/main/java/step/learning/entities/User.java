package step.learning.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String id;
    private String login;
    private String pass;
    private String name;
    private String salt;
    private String avatar;

    public User() {

    }
    public User( ResultSet res ) throws SQLException {
        this.setId( res.getString( "id" ) ) ;
        this.setLogin( res.getString( "login" ) ) ;
        this.setPass( res.getString( "pass" ) ) ;
        this.setName( res.getString( "name" ) ) ;
        this.setSalt( res.getString( "salt" ) ) ;
        this.setAvatar( res.getString( "avatar" ) ) ;
    }

    public String getAvatar() {
        return avatar;
    }
    public String setAvatar( String avatar ) {
        return this.avatar = avatar;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
