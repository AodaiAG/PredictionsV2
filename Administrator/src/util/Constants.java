package util;

import com.google.gson.Gson;

public class Constants
{

    // global constants
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    public final static String JHON_DOE = "<Anonymous>";
    public final static int REFRESH_RATE = 2000;
    public final static String CHAT_LINE_FORMATTING = "%tH:%tM:%tS | %.10s: %s%n";

    // fxml locations
    public final static String MAIN_PAGE_FXML_RESOURCE_LOCATION = "/components/mainApp/mainApp.fxml";
    public final static String MANAGMENT_PAGE = "/components/Management/mangment.fxml";
    public final static String ALLOCATIONS_FXML = "/components/Allocations/allocations.fxml";
    public final static String EXECUTIONS_FXML = "/components/ExecutionsHistory/executions.fxml";

    // Server resources locations
    public final static String BASE_DOMAIN = "localhost";
    public final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;

    public final static String LOGIN_PAGE = FULL_SERVER_PATH + "/loginShortResponse";
    public final static String USERS_LIST = FULL_SERVER_PATH + "/userslist";
    public final static String LOGOUT = FULL_SERVER_PATH + "/chat/logout";
    public final static String SEND_CHAT_LINE = FULL_SERVER_PATH + "/pages/chatroom/sendChat";
    public final static String ALLOCATIONS_PATH = FULL_SERVER_PATH + "/allocations";

    // GSON instance
    public final static Gson GSON_INSTANCE = new Gson();
}
