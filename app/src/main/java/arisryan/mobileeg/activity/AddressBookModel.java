package arisryan.mobileeg.activity;

/**
 * Created by Elad Oktarizo on 24/08/2017.
 */

public class AddressBookModel {
    private int id;
    private String name, email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
