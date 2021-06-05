package com.galvanize.gmdb.Domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GMDBMovie {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long Id;
    private String title;
    private String director;
    private String actors;
    private String release;
    private String description;

    @OneToMany
    private List<Review> GMDBRating = new ArrayList<>();

    public List<Review> getGMDBRating() {
        return GMDBRating;
    }

    public void setGMDBRating(List<Review> GMDBRating) {
        this.GMDBRating = GMDBRating;
    }




    public GMDBMovie(){

    }

    public GMDBMovie(String title, String director, String actors, String release, String description) {
        this.title = title;
        this.director = director;
        this.actors = actors;
        this.release = release;
        this.description = description;
    }

    public GMDBMovie(String title, String director, String actors, String release, String description, List<Review> reviewList) {
        this.title = title;
        this.director = director;
        this.actors = actors;
        this.release = release;
        this.description = description;
        this.GMDBRating = reviewList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }






}
