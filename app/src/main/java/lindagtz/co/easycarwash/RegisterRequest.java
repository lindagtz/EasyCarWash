package lindagtz.co.easycarwash;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by lenovo on 16/04/2017.
 */

public class RegisterRequest extends StringRequest {
        private static final String REGISTER_REQUEST_URL = "http://easycarwash.000webhostapp.com/Register.php";
        private Map<String, String> params;

        public RegisterRequest(String username, String email, String password, String direccion, double longitud, double latitud, int telefono,
                               String firstname, String lastname, String gender, int auth_id, Response.Listener<String> listener) {
            super(Method.POST, REGISTER_REQUEST_URL, listener, null);
            params = new HashMap<>();
            params.put("username", username);
            params.put("email", email );
            params.put("password", password);
            params.put("direccion", direccion);
            params.put("longitud", longitud+"");
            params.put("latitud", latitud+"");
            params.put("telefono", telefono+"");
            params.put("firstname", firstname);
            params.put("lastname", lastname);
            params.put("gender", gender);
            params.put("auth_id", auth_id+"");

        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }

