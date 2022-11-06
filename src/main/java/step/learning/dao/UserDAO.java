package step.learning.dao;

import step.learning.entities.User;
import step.learning.services.DataService;
import step.learning.services.EmailService;
import step.learning.services.HashService;
import step.learning.services.GmailService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class UserDAO {
    private final Connection connection ;
    private final HashService hashService ;
    private final DataService dataService ;
    private final EmailService emailService;

    @Inject
    public UserDAO( DataService dataService,  HashService hashService, EmailService emailService) {
        this.connection = dataService.getConnection();
        this.hashService = hashService ;
        this.dataService = dataService ;
        this.emailService = emailService ;
    }
    public User getUserById(String userId){
        String sql = "SELECT u.* FROM Users u WHERE u.id = ?";
        try(PreparedStatement prep = dataService.getConnection().prepareStatement(sql)) {
        prep.setString(1, userId);
        ResultSet res = prep.executeQuery();
        if (res.next()) {
            return new User(res);
        }
        } catch (Exception ex) {
            System.out.println("UserDAO::getUserById()" + ex.getMessage()
            + "\n" + sql + " -- " + userId);
        }
        return null;
    }

    /**
     * Inserts User to database
     * @param user data to insert
     * @return user ID in table
     */
    public String add( User user ) {
        // генеруемо id
        user.setId( UUID.randomUUID().toString() ) ;
        // генеруємо сіль
        user.setSalt( hashService.hash( UUID.randomUUID().toString() ) ) ;
        // формуємо запит
        String sql = "INSERT INTO Users(`id`,`login`,`pass`,`name`,`salt`, `avatar`) VALUES(?,?,?,?,?,?)" ;
        try( PreparedStatement prep = connection.prepareStatement( sql ) ) {
            prep.setString( 1, user.getId()    ) ;
            prep.setString( 2, user.getLogin() ) ;
            prep.setString( 3, this.makePasswordHash( user.getPass(), user.getSalt() ) ) ;
            prep.setString( 4, user.getName()  ) ;
            prep.setString( 5, user.getSalt()  ) ;
            prep.setString( 6, user.getAvatar()  ) ;
            prep.setString( 7, user.getEmail()  ) ;
            prep.setString( 8, user.getCode()  ) ;
            prep.executeUpdate() ;
        }
        catch( SQLException ex ) {
            System.out.println( ex.getMessage() ) ;
            return null ;
        }
        return user.getId() ;
    }

    /**
     * Checks if login is out from DB table
     * @param login string to test
     * @return true if login NOT in DB
     */
    public boolean isLoginFree( String login ) {
        String sql = "SELECT COUNT(u.id) FROM Users u WHERE u.`login` = ? " ;
        try( PreparedStatement prep = connection.prepareStatement( sql ) ) {
            prep.setString( 1, login ) ;
            ResultSet res = prep.executeQuery() ;
            if( res.next() ) {
                return res.getInt(1) == 0 ;
            }
        }
        catch( SQLException ex ) {
            System.out.println( ex.getMessage() ) ;
            System.out.println( sql + "; " + login ) ;
        }
        return false ;
    }

    /**
     * Looks for user in DB
     * @param login Credentials: login
     * @param password Credentials: password
     * @return entities.User or null if not found
     */
    public User getUserByCredentials( String login, String password ) {
        String sql = "SELECT * FROM Users u WHERE u.`login` = ?" ;
        try( PreparedStatement prep = connection.prepareStatement( sql ) ) {
            prep.setString( 1, login ) ;
            ResultSet res = prep.executeQuery() ;
            if( res.next() ) {
                User user = new User( res ) ;
                String expectedHash = this.makePasswordHash( password, user.getSalt() ) ;
                if( expectedHash.equals( user.getPass() ) ) {
                    return user ;
                }
            }
        }
        catch( SQLException ex ) {
            System.out.println( ex.getMessage() ) ;
            System.out.println( sql + "; " + login + " " + password ) ;
        }
        return null ;
    }
    public User getUserByCredentialsOld( String login, String password ) {
        String sql = "SELECT * FROM Users u WHERE u.`login` = ? AND u.`pass` = ?" ;
        try( PreparedStatement prep = connection.prepareStatement( sql ) ) {
            prep.setString( 1, login ) ;
            prep.setString( 2, this.makePasswordHash( password, "" ) ) ;
            ResultSet res = prep.executeQuery() ;
            if( res.next() ) {
                return new User( res ) ;
            }
        }
        catch( SQLException ex ) {
            System.out.println( ex.getMessage() ) ;
            System.out.println( sql + "; " + login + " " + password ) ;
        }
        return null ;
    }

    public boolean updateUser(User changes){
        if(changes == null || changes.getId() == null){
            return false;
        }
        Map<String, String> data = new HashMap<>();
        GmailService se = new GmailService();
        if(changes.getName() != null) data.put("name", changes.getName());
        if(changes.getAvatar() != null) data.put("avatar", changes.getAvatar());
//        if(changes.getPass() != null) data.put("pass", this.makePasswordHash(changes.getPass(), changes.getSalt()));
        if (changes.getLogin() != null) data.put("login", changes.getLogin());
        if (changes.getEmail() != null) {
            data.put("email", changes.getEmail());
            String code = se.getRandom();
            changes.setCode(code);
            data.put("code", code);
        }
        if (data.isEmpty()) return false;
        boolean needCommand = false;
        StringBuilder sql = new StringBuilder("UPDATE Users SET ");
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (needCommand) sql.append(", ");
            sql.append(entry.getKey()).append(" = ?");
            needCommand = true;
        }
        sql.append(" WHERE id = ?");
        try(PreparedStatement prep = dataService.getConnection().prepareStatement(sql.toString())) {
            int i = 1;
            for (Map.Entry<String, String> entry : data.entrySet()) {
                prep.setString(i, entry.getValue());
                i++;
            }
            prep.setString(i, changes.getId());
            prep.executeUpdate();
        }
        catch (Exception ex) {
            System.out.println("UserDAO::updateUser()" + ex.getMessage()
                    + "\n" + sql + " -- " + changes);
            return false;
        }
        if (changes.getEmail() != null) {
            se.send(changes, "Confirm your email", "Your code: " + changes.getCode());
        }
        return true;
    }
    public boolean isEmailConfirmed(User user) {
        return user.getCode() == null; 
    }
    public boolean confirmEmail(User user) {
        if (user == null || user.getId() == null) {
            return false;
        }
        String sqlCommand = "UPDATE `users` AS u SET u.`code`=NULL WHERE u.`id` = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setString(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.printf("Confirmation error: %s%n", ex.getMessage());
            System.out.printf("Command: %s%n", sqlCommand);
            return false;
        }
        return true;
    }
    
    private String makePasswordHash( String password, String salt ) {
        return hashService.hash( salt + password + salt ) ;
    }
}
/*
Криптографічна сіль - дані, що додадються до основних даних перед перетвореннями.
Навіщо?
 геш від однакових даних дає однакові результати, це дозволяє впізнати однакові паролі
  за їх однаковими гешами, а також це стосується загальної ситуації (між базами даних)
Як?
 1. Константна сіль - дозволяє уникнути глобальної ситуації, не уникає локальної
    рівності. Проста для реалізації
 2. Випадкова сіль - робить всі ситуації винятковими, але ускладнює реалізацію,
    оскільки потребує збереження.
 */