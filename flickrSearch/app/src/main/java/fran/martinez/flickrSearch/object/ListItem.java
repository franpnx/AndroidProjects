package fran.martinez.flickrSearch.object;

//Clase que representa el contenido de una fila del recyclerView
public class ListItem {

    private String author, title, image;

    //constructor
    public ListItem(String author, String title, String image) {
        this.author = author;
        this.title = title;
        this.image = image;
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
}
