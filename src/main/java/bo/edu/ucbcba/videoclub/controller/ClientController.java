package bo.edu.ucbcba.videoclub.controller;

import bo.edu.ucbcba.videoclub.dao.VideoClubEntityManager;
import bo.edu.ucbcba.videoclub.exceptions.ValidationException;
import bo.edu.ucbcba.videoclub.model.Client;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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


    public List<Client> searchClient(String q) {
        EntityManager entityManager = VideoClubEntityManager.createEntityManager();
        TypedQuery<Client> query = entityManager.createQuery("select c from Client c WHERE lower(c.ci) like :ci", Client.class);
        query.setParameter("ci", "%" + q.toLowerCase() + "%");
        List<Client> response = query.getResultList();
        entityManager.close();
        return response;
    }

}
