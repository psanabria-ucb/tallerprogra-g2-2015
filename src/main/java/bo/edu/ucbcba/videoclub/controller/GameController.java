package bo.edu.ucbcba.videoclub.controller;

import bo.edu.ucbcba.videoclub.dao.VideoClubEntityManager;
import bo.edu.ucbcba.videoclub.exceptions.ValidationException;
import bo.edu.ucbcba.videoclub.model.Client;
import bo.edu.ucbcba.videoclub.model.Company;
import bo.edu.ucbcba.videoclub.model.Game;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Calendar;
import java.util.List;

public class GameController {
    public void create(String title,
                       String description,
                       String releaseYear,
                       int rating,
                       String price,
                       Company company) {
        Game game = new Game();
        //--------------------Validaciones de espacios en blanco

        if (description.isEmpty()) {
            description = " ";
        }
        if (releaseYear.isEmpty()) {
            throw new ValidationException("Year can't be blank");
        }

        if (price.isEmpty()) {
            throw new ValidationException("Price can't be blank");
        }

        if (title.isEmpty()) {
            throw new ValidationException("Title can't be blank");
        }


        //--------------------Validacion de Año


        int year, currentYear;

        if (releaseYear.matches("[0-9]+")) {
            game.setReleaseYear(Integer.parseInt(releaseYear));
        }else {
            throw new ValidationException("Release year isn't a number");
        }

        year = Integer.parseInt(releaseYear);
        currentYear = Calendar.getInstance().get(Calendar.YEAR);


        if (price.matches("[0-9]+")) {
            game.setPrice(Integer.parseInt(price));
        }else {
            throw new ValidationException("Price isn't a number");
        }

        if (year <= currentYear && year >= 1947){
            game.setReleaseYear(Integer.parseInt(releaseYear));
        }else{
            throw new ValidationException("Year must be before " + String.valueOf(currentYear+1) + " and after 1946 ");
        }

        //-----------------Validacion Longitud de titulo

        int length;
        length = title.length();
        if (length > 100)
            throw new ValidationException("Tile is too long, must have less than 101 characters");
        else {
            game.setTitle(title);
        }

        //-----------------Validacion Longitud de descripcion

        int lengthDescription;
        lengthDescription = description.length();
        if (lengthDescription > 250)
            throw
                    new ValidationException("Description is too long, must have less than 251 characters");
        else {
            game.setDescription(description);
        }

        game.setRating(rating);
        game.setCompany(company);


        EntityManager entityManager = VideoClubEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(game);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Game> searchGames(String q) {
        EntityManager entityManager = VideoClubEntityManager.createEntityManager();
        TypedQuery<Game> query = entityManager.createQuery("select g from Game g WHERE lower(g.title) like :title", Game.class);
        query.setParameter("title", "%" + q.toLowerCase() + "%");
        List<Game> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public boolean deleteGame(String q){
        EntityManager entityManager = VideoClubEntityManager.createEntityManager();
        try {

            entityManager.getTransaction().begin();

            TypedQuery<Client> query = entityManager.createQuery("delete from Game g WHERE lower(g.title) like :title", Client.class);
            query.setParameter("title", "%" + q.toLowerCase() + "%");
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
