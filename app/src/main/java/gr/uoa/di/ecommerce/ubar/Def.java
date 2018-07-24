package gr.uoa.di.ecommerce.ubar;

public class Def {
    //service paths
    public static final String SERVER_URL = "http://192.168.1.2:8080/";
    public static final String LOGIN_PATH = "user/service/login";
    public static final String CHECK_MAIL_PATH = "user/service/checkEmail";
    public static final String CHECK_USR_PATH = "user/service/checkUsername";
    public static final String REGISTER_PATH = "user/service/register";


    // server returns -1 as id when something is not found
    public static final int not_found = -1;

    //better static checking
    public static final String id = "id";
    public static final String username = "username";
    public static final String name = "name";
    public static final String surname = "surname";
    public static final String password = "password";
    public static final String type = "type";
    public static final String phone = "phone";
    public static final String email = "email";
    public static final String address = "address";
    public static final String driver = "driver";
    public static final String passenger = "passenger";

    //content types of html request message
    public static final String APP_JSON = "application/json; charset=utf-8";
    public static final String TEXT_PLAIN = "text/plain";


}
