package bo.edu.ucbcba.videoclub.controller;

import bo.edu.ucbcba.videoclub.dao.VideoClubEntityManager;
import bo.edu.ucbcba.videoclub.exceptions.ValidationException;
import bo.edu.ucbcba.videoclub.model.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by privado on 30/05/2016.
 */
public class UserController {
    public void create(String username,
                       String password) {

        User user = new User();




        //-----------validacion de longitud

        if(username.length() > 25)
            throw new ValidationException("Username is too long, must have less than 10 characters");

        if(username.length() < 4)
            throw new ValidationException("Username is too short, must have more than 4 characters");

        if(password.length() > 25)
            throw new ValidationException("Password is too long, must have less than 25 characters");

        if(password.length() < 6)
            throw new ValidationException("Password is too short, must have more than 6 characters");

        if (username.matches("[0-9]+")) {
            throw new ValidationException("Username can't be only a number, must have letters");
        }
        if(password.matches("[0-9]+")){
            throw new ValidationException("Password can't be only a number, must have letters");
        }
        if(password.matches("a-zA-Z")){
            throw new ValidationException("Password can't be only a letters, must have numbers");
        }
        else{
            EntityManager entityManager = VideoClubEntityManager.createEntityManager();
            TypedQuery<User> query = entityManager.createQuery("select u from Client u WHERE u.username like :username", Client.class);
            query.setParameter("username", username);
            List<User> response = query.getResultList();

            if(response.size() == 0){
                user.setUsername(username);
                user.setPassword(password);
                user.setUsertype(2);
                user.setIsAuthenticated(0);

                entityManager.getTransaction().begin();
                entityManager.persist(user);
                entityManager.getTransaction().commit();
                entityManager.close();

            }else {
                entityManager.close();
                throw new ValidationException("Already exist a Client with CI: '" + username +"'");

            }
        }
    }

    public boolean validateUser(String usr, String pswd){
        EntityManager entityManager = VideoClubEntityManager.createEntityManager();
        TypedQuery<User> query = entityManager.createQuery("select u from User u WHERE u.username like :username AND u.password like :password", User.class);
        query.setParameter("username", usr);
        query.setParameter("password", pswd);
        List<User> response = query.getResultList();
        entityManager.close();
        if(response.size() == 1){
            return true;
        }
        else {
            return false;
        }
    }

    public List<User> searchUser(String q) {
        EntityManager entityManager = VideoClubEntityManager.createEntityManager();
        TypedQuery<User> query = entityManager.createQuery("select u from Client u WHERE lower(u.username) like :username", User.class);
        query.setParameter("username", "%" + q.toLowerCase() + "%");
        List<User> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public int deleteUser(String q){
        EntityManager entityManager = VideoClubEntityManager.createEntityManager();
        try {

            entityManager.getTransaction().begin();
            TypedQuery<User> query = entityManager.createQuery("select u from Client u WHERE lower(u.username) like :username", User.class);
            query.setParameter("username", q);
            List<User> response = query.getResultList();

/*
            if(response.size() > 1){
                entityManager.close();
                return 3;
            }
            */
            if(response.size() == 0){
                entityManager.close();
                return 2;
            }
            else{

                query = entityManager.createQuery("delete from User u WHERE lower(u.username) like :username", User.class);
                query.setParameter("username", q);
                query.executeUpdate();
                entityManager.getTransaction().commit();
                entityManager.close();
                return 1;
            }

        } catch(Exception ex) {
            entityManager.getTransaction().rollback();
            return 2; // let upper methods know this did not go well
        }
    }
}