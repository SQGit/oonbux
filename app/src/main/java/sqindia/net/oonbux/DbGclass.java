package sqindia.net.oonbux;

/**
 * Created by Salman on 5/27/2016.
 */
//asdf
public class DbGclass {

    String _size;
    String _pickup;
    String _photo;
    String _id;

    public DbGclass() {
    }

    public DbGclass(String size, String pickup, String photo, String id) {

        this._size = size;
        this._pickup = pickup;
        this._photo = photo;
        this._id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_size() {
        return _size;
    }

    public void set_size(String _size) {
        this._size = _size;
    }

    public String get_pickup() {
        return _pickup;
    }

    public void set_pickup(String _pickup) {
        this._pickup = _pickup;
    }

    public String get_photo() {
        return _photo;
    }

    public void set_photo(String _photo) {
        this._photo = _photo;
    }
}
