package org.vika.chat.util; /**
 * Created by Администратор on 26.04.2015.
 */

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

public final class ServletUtil {
    public static final String APPLICATION_JSON = "application/json";

    private ServletUtil() {
    }

    public static String getMessageBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

}
