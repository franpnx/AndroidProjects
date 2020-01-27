package fran.martinez.flickrSearch.object;

//Clase que representa el contenido de una fila del recyclerView
public class ListItem {

    private String author, title, image, description;

    //constructor
    public ListItem(String author, String title, String image, String description) {
        this.author = author;
        this.title = title;
        this.image = image;
        this.description = description;
    }

    // getters
    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }
}
