package by.teachmeskills.shop.utils;

import by.teachmeskills.shop.commands.enums.RequestParamsEnum;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestDataGetter {
    private RequestDataGetter() {
    }

    public static Map<String, String> getData(HttpServletRequest req) {
        Enumeration<String> parameterNames = req.getParameterNames();
        Map<String, String> userData = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            if (key.equals(RequestParamsEnum.COMMAND.getValue())) {
                continue;
            }
            String value = req.getParameter(key);
            userData.put(key, value);
        }
        return userData;
    }
}
