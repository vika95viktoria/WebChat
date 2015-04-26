/**
 * Created by Администратор on 26.04.2015.
 */

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public final class MessageUtil {
    public static final String TOKEN = "token";
    public static final String MESSAGES = "messages";
    private static final String TN = "TN";
    private static final String EN = "EN";
    private static final String ID = "id";
    private static final String DESCRIPTION = "description";
    private static final String USER = "user";

    private MessageUtil() {
    }

    public static String getToken(int index) {
        Integer number = index * 8 + 11;
        return TN + number + EN;
    }

    public static int getIndex(String token) {
        return (Integer.valueOf(token.substring(2, token.length() - 2)) - 11) / 8;
    }

    public static JSONObject stringToJson(String data) throws ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(data.trim());
    }

    public static Message jsonToMessage(JSONObject json) {
        Object id = json.get(ID);
        Object description = json.get(DESCRIPTION);
        Object user = json.get(USER);

        if (id != null && description != null && user != null) {
            return new Message((String) id, (String) description, (String)user);
        }
        return null;
    }

}
