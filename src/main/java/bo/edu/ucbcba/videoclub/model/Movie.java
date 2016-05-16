// Movie class, this class represents a movie table
package bo.edu.ucbcba.videoclub.model;

import javax.persistence.*;

// Movie Entity
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; // Primary Key, and Auto Generated

    @Column(length = 100)
    private String title;

    @Lob
    @Column(length = 500)
    private String description; // Lob will create as TEXT instead of VARCHAR

    private int length; // In minutes
    private int releaseYear;
    private int rating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
