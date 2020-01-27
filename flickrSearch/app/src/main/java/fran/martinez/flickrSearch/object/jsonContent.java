package fran.martinez.flickrSearch.object;

import java.util.ArrayList;

//Clase que representa el contenido obtenido en el Json de respuesta de Flickr
public class jsonContent {

    photos photos;



    public jsonContent.photos getPhotos() {
        return photos;
    }

    public jsonContent(jsonContent.photos photos) {
        this.photos = photos;

    }

    //clase interna
    public static class photos{

        int total;
        ArrayList<photo> photo;

        public int getTotal() {
            return total;
        }

        public ArrayList<jsonContent.photo> getPhoto() {
            return photo;
        }

        public photos( int total, ArrayList<jsonContent.photo> photo) {

            this.total = total;
            this.photo = photo;
        }
    }

    //clase interna
    public static class photo{

        int farm;
        String id,owner,secret,server,title,ownername;
        description description;

        public photo(int farm, String id, String owner, String secret, String server, String title, String ownername, description desc) {
            this.farm = farm;

            this.id = id;
            this.owner = owner;
            this.secret = secret;
            this.server = server;
            this.title = title;
            this.ownername = ownername;
            this.description = desc;
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

        public description getDescription() {
            return description;
        }
    }


    public static class description{

        String _content;

        public description(String _content) {
            this._content = _content;
        }

        public String get_content() {
            return _content;
        }
    }
}
