package ec.edu.monster.movil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Cliente del servicio REST WS_EurekaBank (Payara), recurso coreBancario.
 * Mismas llamadas que la consola y el escritorio; org.json ya viene en Android.
 */
final class Servicio {

    static final String NODO = "REST · JAVA";
    /** Misma IP de Radmin VPN que usa la web. En el emulador: 10.0.2.2:8080. */
    static final String HOST_POR_DEFECTO = "26.243.233.245:8080";
    private static final String RUTA = "/WS_EurekaBank/webresources/coreBancario";

    static String host = HOST_POR_DEFECTO;

    private Servicio() { }

    static boolean login(String usuario, String clave) throws Exception {
        String r = get("/login?usuario=" + enc(usuario) + "&clave=" + enc(clave));
        return new JSONObject(r).getBoolean("login");
    }

    static double saldo(String cuenta) throws Exception {
        return new JSONObject(get("/saldo/" + enc(cuenta))).getDouble("saldo");
    }

    static boolean deposito(String cuenta, double importe) throws Exception {
        return estado(post("/deposito", "cuenta=" + enc(cuenta) + "&importe=" + numero(importe)));
    }

    static boolean retiro(String cuenta, double importe) throws Exception {
        return estado(post("/retiro", "cuenta=" + enc(cuenta) + "&importe=" + numero(importe)));
    }

    static boolean transferencia(String origen, String destino, double importe) throws Exception {
        return estado(post("/transferencia",
                "cuentaOrigen=" + enc(origen) + "&cuentaDestino=" + enc(destino) + "&importe=" + numero(importe)));
    }

    /** Cada movimiento: {fecha, tipo, accion, importe}. El servidor responde 404 si la cuenta no tiene movimientos. */
    static List<String[]> movimientos(String cuenta) throws Exception {
        List<String[]> lista = new ArrayList<>();
        String r = get("/movimientos/" + enc(cuenta));
        if (r == null) return lista;
        JSONArray arr = new JSONArray(r);
        for (int i = 0; i < arr.length(); i++) {
            JSONObject m = arr.getJSONObject(i);
            lista.add(new String[]{m.optString("fecha"), m.optString("tipo"), m.optString("accion"),
                    String.format(Locale.US, "%.2f", m.optDouble("importe"))});
        }
        return lista;
    }

    // ── HTTP ────────────────────────────────────────────────────────────

    private static String get(String ruta) throws Exception {
        HttpURLConnection c = abrir(ruta);
        return leer(c);
    }

    private static String post(String ruta, String formulario) throws Exception {
        HttpURLConnection c = abrir(ruta);
        c.setRequestMethod("POST");
        c.setDoOutput(true);
        c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        try (OutputStream os = c.getOutputStream()) {
            os.write(formulario.getBytes(StandardCharsets.UTF_8));
        }
        return leer(c);
    }

    private static HttpURLConnection abrir(String ruta) throws Exception {
        HttpURLConnection c = (HttpURLConnection) new URL("http://" + host + RUTA + ruta).openConnection();
        c.setConnectTimeout(8000);
        c.setReadTimeout(15000);
        c.setRequestProperty("Accept", "application/json");
        return c;
    }

    /** Devuelve el cuerpo; null si es 404; lanza el error del servidor en otro caso. */
    private static String leer(HttpURLConnection c) throws Exception {
        int codigo = c.getResponseCode();
        if (codigo == 404) return null;
        String cuerpo = texto(codigo < 400 ? c.getInputStream() : c.getErrorStream());
        if (codigo >= 400) throw new Exception("El servidor respondió HTTP " + codigo + " " + cuerpo);
        return cuerpo;
    }

    private static boolean estado(String json) throws Exception {
        return json != null && new JSONObject(json).optInt("estado") == 1;
    }

    private static String enc(String s) throws Exception {
        return URLEncoder.encode(s, "UTF-8");
    }

    private static String numero(double d) {
        return String.format(Locale.US, "%.2f", d);
    }

    private static String texto(InputStream in) throws Exception {
        if (in == null) return "";
        try (InputStream is = in) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            for (int n; (n = is.read(buf)) > 0; ) out.write(buf, 0, n);
            return out.toString("UTF-8");
        }
    }
}
