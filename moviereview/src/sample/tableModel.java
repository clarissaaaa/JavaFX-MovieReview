package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class tableModel {
    public final SimpleIntegerProperty movieid = new SimpleIntegerProperty();
    private final SimpleStringProperty title = new SimpleStringProperty();
    private final SimpleStringProperty desc = new SimpleStringProperty();

    public int getMovieid() {
        return movieid.get();
    }

    public SimpleIntegerProperty movieidProperty() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid.set(movieid);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDesc() {
        return desc.get();
    }

    public SimpleStringProperty descProperty() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc.set(desc);
    }
}
