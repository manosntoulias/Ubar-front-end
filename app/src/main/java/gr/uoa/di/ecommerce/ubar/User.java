package gr.uoa.di.ecommerce.ubar;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String username;
    private String password;
    private String surname;
    private String name;
    private String email;
    private String address;
    private String phone;

    public User(String usr, String pass, String surn, String name, String mail, String addr, String ph)
    {
        username = usr;
        password = pass;
        surname = surn;
        name = name;
        email = mail;
        address = addr;
        phone = ph;
    }

    public User() {}

    public void set(String usr, String pass, String surn, String name, String mail, String addr, String ph)
    {
        username = usr;
        password = pass;
        surname = surn;
        name = name;
        email = mail;
        address = addr;
        phone = ph;
    }

    public String mapJSON(JSONObject jobj) throws JSONException {

            jobj.put(Def.username, username);
            jobj.put(Def.password, password);
            jobj.put(Def.surname, surname);
            jobj.put(Def.name, name);
            jobj.put(Def.email, email);
            jobj.put(Def.address, address);
            jobj.put(Def.phone, phone);

            return jobj.toString();
    }

    public boolean empty() {
        if ("".equals(username) ||
            "".equals(password) ||
            "".equals(surname) ||
            "".equals(name) ||
            "".equals(email) ||
            "".equals(address) ||
            "".equals(phone))
                return true;
        return false;
    }

    public void setUsername(String usrname) {
        this.username = usrname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
