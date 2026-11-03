package ec.edu.monster.modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class BancoClienteControlador {

    private final String BASE_URL =
            "http://26.243.233.245:8080/WS_EurekaBank/webresources/coreBancario";

    public boolean login(String usuario, String clave) {

        try {

            URL url = new URL(
                    BASE_URL
                    + "/login?usuario="
                    + usuario
                    + "&clave="
                    + clave
            );

            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(
                                    conn.getInputStream()
                            )
                    );

            String respuesta = br.readLine();

            JSONObject json =
                    new JSONObject(respuesta);

            return json.getBoolean("login");

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public double consultarSaldo(String cuenta) {

        try {

            URL url = new URL(
                    BASE_URL
                    + "/saldo/"
                    + cuenta
            );

            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(
                                    conn.getInputStream()
                            )
                    );

            String respuesta = br.readLine();

            JSONObject json =
                    new JSONObject(respuesta);

            return json.getDouble("saldo");

        } catch (Exception e) {

            e.printStackTrace();
            return -1;
        }
    }

    public boolean realizarDeposito(
            String cuenta,
            double importe,
            String empleado) {

        try {

            URL url =
                    new URL(
                            BASE_URL + "/deposito"
                    );

            HttpURLConnection conn =
                    (HttpURLConnection)
                            url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String params =
                    "cuenta="
                    + cuenta
                    + "&importe="
                    + importe;

            OutputStream os =
                    conn.getOutputStream();

            os.write(params.getBytes());

            os.flush();
            os.close();

            return conn.getResponseCode() == 200;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public boolean realizarRetiro(
            String cuenta,
            double importe,
            String empleado) {

        try {

            URL url =
                    new URL(
                            BASE_URL + "/retiro"
                    );

            HttpURLConnection conn =
                    (HttpURLConnection)
                            url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String params =
                    "cuenta="
                    + cuenta
                    + "&importe="
                    + importe;

            OutputStream os =
                    conn.getOutputStream();

            os.write(params.getBytes());

            os.flush();
            os.close();

            return conn.getResponseCode() == 200;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public boolean realizarTransferencia(
            String origen,
            String destino,
            double importe,
            String empleado) {

        try {

            URL url =
                    new URL(
                            BASE_URL
                            + "/transferencia"
                    );

            HttpURLConnection conn =
                    (HttpURLConnection)
                            url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String params =
                    "cuentaOrigen="
                    + origen
                    + "&cuentaDestino="
                    + destino
                    + "&importe="
                    + importe;

            OutputStream os =
                    conn.getOutputStream();

            os.write(params.getBytes());

            os.flush();
            os.close();

            return conn.getResponseCode() == 200;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public String consultarMovimientos(String cuenta) {

        try {

            URL url =
                    new URL(
                            BASE_URL
                            + "/movimientos/"
                            + cuenta
                    );

            HttpURLConnection conn =
                    (HttpURLConnection)
                            url.openConnection();

            conn.setRequestMethod("GET");

            BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(
                                    conn.getInputStream()
                            )
                    );

            StringBuilder sb =
                    new StringBuilder();

            String linea;

            while ((linea = br.readLine()) != null) {
                sb.append(linea);
            }

            return sb.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return "[]";
        }
    }
}