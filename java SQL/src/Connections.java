public class Connections {
    // Database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/banking"; // Change to your database
    private static final String USER = "UserName"; // Your MySQL username
    private static final String PASSWORD = "YourPassword"; // Your MySQL password
    public static String getUrl(){
        return URL;
    }
    public static String getUser(){
        return USER;
    }
    public static String getPassword(){
        return PASSWORD;
    }
}
