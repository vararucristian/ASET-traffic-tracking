package Data;

import Response.ResponseTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserRepository {

    @PersistenceContext
    protected EntityManager em;


    private User find(String username, String password) {
        try {
            return (User) em.createNamedQuery("User.find")
                    .setParameter(1, username)
                    .setParameter(2, password)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println("Wrong data");
            return null;
        }
    }


    public ResponseTemplate authenticateUser(String password, String userName) {
        ResponseTemplate response = new ResponseTemplate("", "", "", false);
        User user = find(userName, password);
        if (user != null) {
            response = new ResponseTemplate(user.fName,
                    user.lName,
                    user.userName,
                    true);
        }
        return response;
    }

    private int getId() {
        Long id = (Long) em.createNamedQuery("User.getMaxId").getSingleResult();
        System.out.println(id.intValue());
        return id.intValue();
    }

    public boolean addUser(String fName, String lName, String password, String userName) {
        try {
            User user = new User(userName, password, fName, lName, getId());
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public User getUserByUsername(String userName) {
        try {
            return (User) em.createNamedQuery("User.getUser")
                    .setParameter(1, userName)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println("Wrong data");
            return null;
        }
    }
}
