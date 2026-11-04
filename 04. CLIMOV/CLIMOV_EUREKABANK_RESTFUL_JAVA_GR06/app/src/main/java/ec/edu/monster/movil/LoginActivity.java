package ec.edu.monster.movil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences prefs = getSharedPreferences("eureka", MODE_PRIVATE);
        EditText servidor = findViewById(R.id.servidor);
        EditText usuario = findViewById(R.id.usuario);
        EditText clave = findViewById(R.id.clave);
        TextView estado = findViewById(R.id.estado);

        ((TextView) findViewById(R.id.nodo)).setText("NODO: " + Servicio.NODO + "   ■ EN LÍNEA");
        servidor.setText(prefs.getString("host", Servicio.HOST_POR_DEFECTO));

        findViewById(R.id.ingresar).setOnClickListener(v -> {
            String u = usuario.getText().toString().trim();
            String c = clave.getText().toString();
            if (u.isEmpty() || c.isEmpty()) {
                Ui.error(this, estado, "Ingrese usuario y clave");
                return;
            }
            Servicio.host = servidor.getText().toString().trim();
            prefs.edit().putString("host", Servicio.host).apply();

            Ui.enSegundoPlano(this, estado, () -> Servicio.login(u, c), ok -> {
                if (!ok) {
                    Ui.error(this, estado, "Usuario o clave incorrectos");
                    return;
                }
                estado.setText("");
                clave.setText("");
                startActivity(new Intent(this, OperacionesActivity.class).putExtra("usuario", u));
            });
        });
    }
}
