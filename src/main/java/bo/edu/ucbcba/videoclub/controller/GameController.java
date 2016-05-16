package bo.edu.ucbcba.videoclub.controller;

import bo.edu.ucbcba.videoclub.dao.VideoClubEntityManager;
import bo.edu.ucbcba.videoclub.model.Game;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class GameController {
    public void create (String title,
                        String description,
                        String releaseYear,
                        int rating,
                        String company){
        Game game = new Game();
        game.setTitle(title);
        game.setCompany(company);
        game.setDescription(description);
        game.setRating(rating);
        game.setReleaseYear(releaseYear);
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
