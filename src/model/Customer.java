package model;

import controllers.CustomerDatabase;

public class Customer extends User {

    private String firstName;
    private String lastName;
    private String address;
    private String zip;
    private String state;
    private String email;
    private String ssn;
    private String securityQuestion;

    public Customer(String username, String password) {
        super(username, password);
    }

    public Customer(String firstName, String lastName, String address, String zip, String state, String email,
            String ssn, String securityQuestion, String username, String password) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zip = zip;
        this.state = state;
        this.email = email;
        this.ssn = ssn;
        this.securityQuestion = securityQuestion;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    @Override
    public boolean login() {

        CustomerDatabase customerDatabase = new CustomerDatabase();
        Customer customer = customerDatabase.signIn(super.getUsername(), super.getPassword());
        if (customer != null) {
            setFirstName(customer.getFirstName());
            setLastName(customer.getLastName());
            setAddress(customer.getAddress());
            setZip(customer.getZip());
            setState(customer.getState());
            setEmail(customer.getEmail());
            setSsn(customer.getSsn());
            setSecurityQuestion(customer.getSecurityQuestion());
            return true;

        } else {
            return false;
        }
    }

}
