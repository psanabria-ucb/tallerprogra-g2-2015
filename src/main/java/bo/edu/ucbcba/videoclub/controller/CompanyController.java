package bo.edu.ucbcba.videoclub.controller;

import bo.edu.ucbcba.videoclub.dao.VideoClubEntityManager;
import bo.edu.ucbcba.videoclub.exceptions.ValidationException;
import bo.edu.ucbcba.videoclub.model.Company;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Samuel on 29/05/2016.
 */
public class CompanyController {
    public void create(String name, String country){

        Company company = new Company();
        if(name.isEmpty())
        {
            throw new ValidationException("Name can't be blank");
        }

        if(country.isEmpty())
        {
            throw new ValidationException("Country can't be blank");
        }
        // valiadcion de longitud

        if (name.length() > 25)
        {
            throw new ValidationException("Name is too long, must have less than 25 characters");
        }
        if (country.length() > 25)
        {
            throw new ValidationException("Country is too long, must have less than 25 characters");
        }

        /* if(Validar(Fname,Lname)>0) {
            throw new ValidationException("director already exists");
        }*/

        if(validatePresence(name, country)>0){
            throw new ValidationException("Company already exists");
        }
        company.setName(name);
        company.setCountry(country);


        EntityManager em = VideoClubEntityManager.createEntityManager();
        em.getTransaction().begin();
        em.persist(company);
        em.getTransaction().commit();
        em.close();

    }
    public int validatePresence(String name, String country ){
        EntityManager entityManager = VideoClubEntityManager.createEntityManager();
        TypedQuery<Company> query = entityManager.createQuery("SELECT c FROM Company c WHERE lower(c.country) = :Ccountry AND lower(c.name) = :Cname ", Company.class);
        query.setParameter("Cname", name.toLowerCase());
        query.setParameter("Ccountry",country.toLowerCase());
        List<Company> response = query.getResultList();
        int a=response.size();
        entityManager.close();
        return a;
    }

    public List<Company> getAllCompanies(){
        EntityManager em = VideoClubEntityManager.createEntityManager();
        TypedQuery<Company> query = em.createQuery("select c from Company c order by c.name", Company.class);
        List<Company> list = query.getResultList();
        em.close();
        return list;
    }
}
