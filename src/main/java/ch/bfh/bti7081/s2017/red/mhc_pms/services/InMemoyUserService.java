package ch.bfh.bti7081.s2017.red.mhc_pms.services;

import ch.bfh.bti7081.s2017.red.mhc_pms.domain.Sha1PasswordService;
import ch.bfh.bti7081.s2017.red.mhc_pms.domain.PasswordService;
import ch.bfh.bti7081.s2017.red.mhc_pms.domain.User;
import ch.bfh.bti7081.s2017.red.mhc_pms.presenter.UserManagementPresenter;
import ch.bfh.bti7081.s2017.red.mhc_pms.ui.panels.NewUserCreatePanel;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rolf Zurbrügg
 */
public class InMemoyUserService implements UserService {


    /** The Constant log. */
    static final Logger log = Logger.getRootLogger();

    private final static List<User> users = new ArrayList<>();
    private static PasswordService passwordService = new Sha1PasswordService();
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public User findUserById(int userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> findUserByFilter(String filter) {
        return null;
    }

    @Override
    public User getUserByUserName(String name)   {

        try {
            for(User u: users){
                log.debug("U name: "+u.getUsername());
                if(u.getUsername().equals(name)){
                    return u;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void createOrUpdateUser(User user) {

    }

    @Override
    public void deleteUser(int userID) {

    }

    @Override
    public boolean checkPassword(String userName, String password) {

        log.debug("checking password for:" + userName);

        User userTestPassword = getUserByUserName(userName);
        log.debug("got user:" +userTestPassword.getUsername());

        byte[] userSalt = userTestPassword.getSalt();
        log.debug("user salt "+ userSalt);
        byte[] passwordHash = passwordService.returnPasswordHashSalted(password,userSalt);
        String enteredPasswordHashBase64 = java.util.Base64.getEncoder().encodeToString(passwordHash);

        System.out.println("entered password hash: "+ enteredPasswordHashBase64 + "\nuser password hash: " + userTestPassword.getPasswordHash());


        //test if the password matches the specified user

        if(userTestPassword.getPasswordHash().equals(enteredPasswordHashBase64)){
            return true;
        }
        return false;
    }

    @Override
    public void addUser(User newUser) {
        users.add(newUser);
        log.debug("User added in memory userS: U name: " + newUser.getUsername() + "\npw:" + newUser.getPasswordHash());
        String asurers = "";
        for(User u: users){
            asurers+=u.getUsername()+", "+u.getPasswordHash()+", ";
        }
        log.debug(asurers);
    }


}
