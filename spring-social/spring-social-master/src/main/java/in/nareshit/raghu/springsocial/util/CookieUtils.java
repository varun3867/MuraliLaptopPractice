package in.nareshit.raghu.springsocial.util;

import org.springframework.util.SerializationUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Optional;

public class CookieUtils {

    public static Optional<Cookie> getCookie( HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        System.out.println("Entered into CookieUtils.getCookie");
        System.out.println("name in getCookie is : "+name);
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
            	System.out.println("for loop --> "+cookie.getName());
                if (cookie.getName().equals(name)) {
                    return Optional.of(cookie);
                }
            }
        }

        return Optional.empty();
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
    	System.out.println("CookieUtils.addCookie()");
    	System.out.println("name is : "+name);
    	System.out.println("value is : "+value);
    	System.out.println("Age is : "+maxAge);
    	Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
    	System.out.println("CookieUtils.deleteCookie()");
    	Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }

    public static String serialize(Object object) {
    	System.out.println("CookieUtils.serialize()");
    	return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(object));
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
    	System.out.println("CookieUtils.deserialize()");
    	return cls.cast(SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())));
    }


}
