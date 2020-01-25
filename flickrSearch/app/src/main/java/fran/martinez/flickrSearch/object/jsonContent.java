package fran.martinez.flickrSearch.object;

import java.util.ArrayList;

//Clase que representa el contenido obtenido en el Json de respuesta de Flickr
public class jsonContent {

    photos photos;
    String stat;


    public jsonContent.photos getPhotos() {
        return photos;
    }

    public jsonContent(jsonContent.photos photos, String stat) {
        this.photos = photos;
        this.stat = stat;
    }

    //clase interna
    public static class photos{

        int page, pages, perpage,total;
        ArrayList<photo> photo;

        public int getTotal() {
            return total;
        }

        public ArrayList<jsonContent.photo> getPhoto() {
            return photo;
        }

        public photos(int page, int pages, int perpage, int total, ArrayList<jsonContent.photo> photo) {
            this.page = page;
            this.pages = pages;
            this.perpage = perpage;
            this.total = total;
            this.photo = photo;
        }
    }

    //clase interna
    public static class photo{

        int farm,isfriend,isfamily;
        String id,owner,secret,server,title,ownername;

        public photo(int farm, int isfriend, int isfamily, String id, String owner, String secret, String server, String title, String ownername) {
            this.farm = farm;
            this.isfriend = isfriend;
            this.isfamily = isfamily;
            this.id = id;
            this.owner = owner;
            this.secret = secret;
            this.server = server;
            this.title = title;
            this.ownername = ownername;
        }

        public int getFarm() {
            return farm;
        }

        public String getId() {
            return id;
        }

        public String getSecret() {
            return secret;
        }

        public String getServer() {
            return server;
        }

        public String getTitle() {
            return title;
        }

        public String getOwnername() {
            return ownername;
        }
    }
}
