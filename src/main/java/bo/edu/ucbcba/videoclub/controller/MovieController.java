package bo.edu.ucbcba.videoclub.controller;

import bo.edu.ucbcba.videoclub.dao.VideoClubEntityManager;
import bo.edu.ucbcba.videoclub.exceptions.ValidationException;
import bo.edu.ucbcba.videoclub.model.Movie;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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
                       String nameImage) {

        Movie movie = new Movie();
        //--------------------Validaciones de espacios en blanco
        if (price.isEmpty()){
            throw new ValidationException("Price can't be blank");
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
        year = Integer.parseInt(releaseYear);
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (releaseYear.matches("[0-9]+")) {
            movie.setReleaseYear(Integer.parseInt(releaseYear));
        }else {
            throw new ValidationException("Release year isn't a number");
        }

        if (price.matches("[0-9]+")) {
            movie.setReleaseYear(Integer.parseInt(releaseYear));
        }else {
            throw new ValidationException("Price year isn't a number");
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

        movie.setRating(rating);
        movie.setNameImage(nameImage);
        movie.setTitle(title);
        movie.setDescription(description);
        
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
}
