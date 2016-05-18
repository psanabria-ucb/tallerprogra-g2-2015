package bo.edu.ucbcba.videoclub.controller;

import bo.edu.ucbcba.videoclub.dao.VideoClubEntityManager;
import bo.edu.ucbcba.videoclub.exceptions.ValidationException;
import bo.edu.ucbcba.videoclub.model.Game;
import bo.edu.ucbcba.videoclub.model.Movie;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Calendar;
import java.util.List;

public class GameController {
    public void create (String title,
                        String description,
                        String releaseYear,
                        int rating,
                        String company){
        Game game = new Game();
        //--------------------Validaciones de espacios en blanco

        if (description.isEmpty()){
            description = " ";
        }
        if (releaseYear.isEmpty()){
            throw new ValidationException("Year can't be blank");
        }

        if (title.isEmpty()){
            throw new ValidationException("Title can't be blank");
        }

        if (company.isEmpty()){
            throw new ValidationException("Company can't be blank");
        }

        //--------------------Validacion de Año
        int year, currentYear;
        year = Integer.parseInt(releaseYear);
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (releaseYear.matches("[0-9]+")) {
            game.setReleaseYear((releaseYear));
        }else {
            throw new ValidationException("Release year isn't a number");
        }

        if (year < currentYear && year > 1887){
            game.setReleaseYear((releaseYear));
        }else{
            throw new ValidationException("Year must be before " + String.valueOf(currentYear+1) + " and after 1887 ");
        }


        //-----------------Validacion Longitud de titulo

        int length;
        length = title.length();
        if(length > 100)
            throw new ValidationException("Tile is too long, must have less than 101 characters");

        game.setRating(rating);
        game.setTitle(title);
        game.setDescription(description);


        EntityManager entityManager = VideoClubEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(game);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Game> searchGames (String q){
        EntityManager entityManager = VideoClubEntityManager.createEntityManager();
        TypedQuery<Game> query = entityManager.createQuery("select g from Game g WHERE lower(g.title) like :title", Game.class);
        query.setParameter("title", "%" + q.toLowerCase() + "%");
        List<Game> response = query.getResultList();
        entityManager.close();
        return response;
    }
}
