package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class userReviewTable {
    private final SimpleStringProperty username = new SimpleStringProperty();
    private final SimpleStringProperty review = new SimpleStringProperty();
    private final SimpleStringProperty rating = new SimpleStringProperty();
    private final SimpleStringProperty avrg = new SimpleStringProperty();
    private final SimpleIntegerProperty movieid = new SimpleIntegerProperty();

    public String getAvrg() {
        return avrg.get();
    }

    public SimpleStringProperty avrgProperty() {
        return avrg;
    }

    public void setAvrg(String avrg) {
        this.avrg.set(avrg);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getReview() {
        return review.get();
    }

    public SimpleStringProperty reviewProperty() {
        return review;
    }

    public void setReview(String review) {
        this.review.set(review);
    }

    public String getRating() {
        return rating.get();
    }

    public SimpleStringProperty ratingProperty() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating.set(rating);
    }

    public int getMovieid() {
        return movieid.get();
    }

    public SimpleIntegerProperty movieidProperty() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid.set(movieid);
    }
}
