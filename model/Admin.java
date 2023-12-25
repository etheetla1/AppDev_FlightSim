package model;

public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public boolean login() {
        return super.getUsername().equals("admin") && super.getPassword().equals("admin1122");
    }

}
