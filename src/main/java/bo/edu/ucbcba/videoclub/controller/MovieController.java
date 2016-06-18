package bo.edu.ucbcba.videoclub.controller;

import bo.edu.ucbcba.videoclub.dao.VideoClubEntityManager;
import bo.edu.ucbcba.videoclub.exceptions.ValidationException;
import bo.edu.ucbcba.videoclub.model.Director;
import bo.edu.ucbcba.videoclub.model.Game;
import bo.edu.ucbcba.videoclub.model.Movie;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.*;
import java.util.Calendar;
import java.util.List;

public class MovieController {
    public void create(String title,
                       String description,
                       String releaseYear,
                       int rating,
                       String hoursLength,
                       String minutesLength,
                       String price,
                       String nameImage, Director d) {

        Movie movie = new Movie();
        //--------------------Validaciones de espacios en blanco
        if (price.isEmpty()){
            throw new ValidationException("Price can't be blank");
        }
        if(releaseYear.length() > 5)
            throw new ValidationException("Release Year can't have more than 5 characters");
        if(price.length() > 7)
            throw new ValidationException("Price can't have more than 7 characters");


        if(minutesLength.length() > 4)
            throw new ValidationException("Minutes  can't have more than 4 characters");
        if(hoursLength.length() > 4)
            throw new ValidationException("Hours can't have more than 4 characters");


        if(nameImage.isEmpty()){
            throw new ValidationException("Image can't be blank");
        }

        if (description.isEmpty()){
            description = " ";
        }
        if (releaseYear.isEmpty()){
            throw new ValidationException("Year can't be blank");
        }

        if (title.isEmpty()){
            throw new ValidationException("Title can't be blank");
        }

        if (hoursLength.isEmpty()){
            throw new ValidationException("Hours can't be blank");
        }

        if (minutesLength.isEmpty()){
            throw new ValidationException("Minutes can't be blank");
        }
        //--------------------Validacion de Año
        int year, currentYear;

        if (releaseYear.matches("[0-9]+")) {
            movie.setReleaseYear(Integer.parseInt(releaseYear));
        }else {
            throw new ValidationException("Release year isn't a number");
        }


        year = Integer.parseInt(releaseYear);
        currentYear = Calendar.getInstance().get(Calendar.YEAR);


        if (price.matches("[0-9]+")) {
            movie.setPrice(Integer.parseInt(price));
        }else {
            throw new ValidationException("Price isn't a number");
        }

        if (year <= currentYear && year > 1887){
            movie.setReleaseYear(Integer.parseInt(releaseYear));
        }else{
            throw new ValidationException("Year must be before " + String.valueOf(currentYear+1) + " and after 1887 ");
        }

        //-----------------------Validacion de Tiempo de duracion

        int hours, minutes;
        if (!hoursLength.matches("[0-9]+"))
            throw new ValidationException("Year isn't a number");
        hours = Integer.parseInt(hoursLength);

        if (!minutesLength.matches("[0-9]+"))
            throw new ValidationException("Minutes field isn't a number");
        minutes = Integer.parseInt(minutesLength);

        if (minutes >= 60)
            throw new ValidationException("Minutes can't be greater than 59");
        movie.setLength(hours * 60 + minutes);

        //-----------------Validacion Longitud de titulo

        int length;
        length = title.length();
        if(length > 100)
            throw new ValidationException("Tile is too long, must have less than 101 characters");

        //-----------------Validacion Longitud de descripcion
        int lengthDescription;
        lengthDescription = description.length();
        if (lengthDescription > 250)
            throw
                    new ValidationException("Description is too long, must have less than 251 characters");
        else {
            movie.setDescription(description);
        }


        if(ValidarM(title)>0)
        {
            throw new ValidationException("Movie already exists");
        }

        movie.setRating(rating);
        movie.setNameImage(nameImage);
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setDirector(d);
        
        EntityManager entityManager = VideoClubEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(movie);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Movie> searchMovies(String q) {
        EntityManager entityManager = VideoClubEntityManager.createEntityManager();
        TypedQuery<Movie> query = entityManager.createQuery("select m from Movie m WHERE lower(m.title) like :title", Movie.class);
        query.setParameter("title", "%" + q.toLowerCase() + "%");
        List<Movie> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public List<Movie> searchMovies2(String q) {
        EntityManager entityManager = VideoClubEntityManager.createEntityManager();
        TypedQuery<Movie> query = entityManager.createQuery("select m from Movie m WHERE lower(m.title) = :title", Movie.class);
        query.setParameter("title",q.toLowerCase());
        List<Movie> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public int ValidarM(String q) {
        EntityManager entityManager = VideoClubEntityManager.createEntityManager();
        TypedQuery<Movie> query = entityManager.createQuery("select m from Movie m WHERE lower(m.title) = :title", Movie.class);
        query.setParameter("title",q.toLowerCase());
        List<Movie> response = query.getResultList();
        int a=response.size();
        entityManager.close();
        return a;
    }
    public List<Movie> searchDirectors(String q) {
        EntityManager entityManager = VideoClubEntityManager.createEntityManager();
        //TypedQuery<Movie> query = entityManager.createQuery("select m from Movie m WHERE (lower(m.director.firstName)+ SPACE(1) + lower(m.director.lastName)) like :direc", Movie.class);
        TypedQuery<Movie> query = entityManager.createQuery("select m from Movie m WHERE lower(concat(m.director.firstName,' ',m.director.lastName)) like :direc", Movie.class);
        query.setParameter("direc", "%" + q.toLowerCase() + "%");
        List<Movie> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public void delete(String q){
        int confirmation = JOptionPane.showConfirmDialog(null, "Do you really want to delete this Game?", "Delete",  JOptionPane.YES_NO_OPTION);
        if (confirmation == 0) {
            EntityManager entityManager = VideoClubEntityManager.createEntityManager();
            entityManager.getTransaction().begin();
            TypedQuery  query = entityManager.createQuery("select m.id from Movie m WHERE lower(m.title) like :codigo", Movie.class);
            query.setParameter("codigo",q.toLowerCase());
            entityManager.remove(entityManager.find(Movie.class, query.getSingleResult()));
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }
}
