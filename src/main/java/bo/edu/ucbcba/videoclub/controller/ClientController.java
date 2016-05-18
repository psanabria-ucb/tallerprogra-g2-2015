package bo.edu.ucbcba.videoclub.controller;

import bo.edu.ucbcba.videoclub.dao.VideoClubEntityManager;
import bo.edu.ucbcba.videoclub.exceptions.ControllerException;
import bo.edu.ucbcba.videoclub.exceptions.ValidationException;
import bo.edu.ucbcba.videoclub.model.Client;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.*;
import java.util.List;

/**
 * Created by privado on 16/05/2016.
 */
public class ClientController {
    public void create(String ci,
                       String firstname,
                       String lastname,
                       String address) {

        Client client = new Client();
        /*client.setCi(ci);
        */

        if (ci.matches("[0-9]+")) {
            client.setCi(ci);
            client.setFirstname(firstname);
            client.setLastname(lastname);
            client.setAddress(address);

            EntityManager entityManager = VideoClubEntityManager.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(client);
            entityManager.getTransaction().commit();
            entityManager.close();

        }
        else {
            throw new ValidationException("Ci isn't a number");

        }


    }


    public List<Client> searchClient(String q) {
        EntityManager entityManager = VideoClubEntityManager.createEntityManager();
        TypedQuery<Client> query = entityManager.createQuery("select c from Client c WHERE lower(c.ci) like :ci", Client.class);
        query.setParameter("ci", "%" + q.toLowerCase() + "%");
        List<Client> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public boolean deleteClient(String q){
        EntityManager entityManager = VideoClubEntityManager.createEntityManager();
        try {

            entityManager.getTransaction().begin();

            TypedQuery<Client> query = entityManager.createQuery("delete from Client c WHERE lower(c.ci) like :ci", Client.class);
            query.setParameter("ci", "%" + q.toLowerCase() + "%");
            int response = query.executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch(Exception ex) {
            entityManager.getTransaction().rollback();
            return false; // let upper methods know this did not go well

        }
    }


}
