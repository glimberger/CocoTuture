package fr.cnam.guillaumelimberger.cocotuture.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Subscriber entity
 *
 * @author guillaumelimberger
 */
public class Subscriber
{
    public static final String ID           = "_id";
    public static final String FIRSTNAME    = "firstname";
    public static final String LASTNAME     = "lastname";
    public static final String PHONENUMBER  = "phone";
    public static final String EMAIL        = "email";
    public static final String LOGIN        = "login";
    public static final String PASSWORD     = "password";

    /**
     * Subscriber identifier
     */
    protected int id;

    /**
     * Subscriber first name
     */
    protected String firstName;

    /**
     * Subscriber last name
     */
    protected String lastName;

    /**
     * Subscriber phone number
     */
    protected String phone;

    /**
     * Subscriber email
     */
    protected String email;

    /**
     * Subscriber login
     */
    protected String login;

    /**
     * Subscriber password
     */
    protected String password;

    public Subscriber() {}

    public Subscriber(String firstName, String lastName, String phone, String email, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return firstName + ' ' + lastName + " : " + phone;
    }
}
