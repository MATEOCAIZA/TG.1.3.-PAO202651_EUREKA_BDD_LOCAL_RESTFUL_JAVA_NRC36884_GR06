package ec.edu.monster.movil;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import java.util.List;
import java.util.Locale;

public class OperacionesActivity extends Activity {

    private EditText cuenta, importe, destino;
    private TextView salida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaciones);

        cuenta = findViewById(R.id.cuenta);
        importe = findViewById(R.id.importe);
        destino = findViewById(R.id.destino);
        salida = findViewById(R.id.salida);
        ((TextView) findViewById(R.id.nodo)).setText(
                "NODO: " + Servicio.NODO + " · " + getIntent().getStringExtra("usuario"));

        findViewById(R.id.salir).setOnClickListener(v -> finish());

        findViewById(R.id.saldo).setOnClickListener(v -> {
            String c = cuenta();
            if (c == null) return;
            Ui.enSegundoPlano(this, salida, () -> Servicio.saldo(c),
                    s -> Ui.ok(this, salida, String.format(Locale.US, "■ CUENTA %s%n  SALDO: $ %,.2f", c, s)));
        });

        findViewById(R.id.deposito).setOnClickListener(v -> {
            String c = cuenta();
            Double m = importe();
            if (c == null || m == null) return;
            Ui.enSegundoPlano(this, salida, () -> Servicio.deposito(c, m),
                    ok -> resultado(ok, String.format(Locale.US, "Depósito de $ %.2f en %s registrado", m, c)));
        });

        findViewById(R.id.retiro).setOnClickListener(v -> {
            String c = cuenta();
            Double m = importe();
            if (c == null || m == null) return;
            Ui.enSegundoPlano(this, salida, () -> Servicio.retiro(c, m),
                    ok -> resultado(ok, String.format(Locale.US, "Retiro de $ %.2f de %s registrado", m, c)));
        });

        findViewById(R.id.transferencia).setOnClickListener(v -> {
            String c = cuenta();
            Double m = importe();
            String d = destino.getText().toString().trim();
            if (c == null || m == null) return;
            if (d.isEmpty()) {
                Ui.error(this, salida, "Ingrese la cuenta destino");
                return;
            }
            Ui.enSegundoPlano(this, salida, () -> Servicio.transferencia(c, d, m),
                    ok -> resultado(ok, String.format(Locale.US, "Transferencia de $ %.2f de %s a %s registrada", m, c, d)));
        });

        findViewById(R.id.movimientos).setOnClickListener(v -> {
            String c = cuenta();
            if (c == null) return;
            Ui.enSegundoPlano(this, salida, () -> Servicio.movimientos(c), lista -> mostrarMovimientos(c, lista));
        });
    }

    private void resultado(boolean ok, String mensaje) {
        if (ok) Ui.ok(this, salida, "✔ " + mensaje);
        else Ui.error(this, salida, "Operación rechazada (revise la cuenta y el saldo)");
    }

    private void mostrarMovimientos(String c, List<String[]> lista) {
        StringBuilder sb = new StringBuilder("■ MOVIMIENTOS ").append(c).append('\n');
        sb.append("FECHA       TIPO            IMPORTE\n");
        sb.append("──────────────────────────────────\n");
        for (String[] m : lista) {
            // m = {fecha, tipo, accion, importe}
            String signo = "SALIDA".equalsIgnoreCase(m[2]) ? "-" : "+";
            sb.append(String.format(Locale.US, "%-11s %-14.14s %s%s%n", m[0], m[1], signo, m[3]));
        }
        sb.append("──────────────────────────────────\n").append("Total: ").append(lista.size());
        Ui.ok(this, salida, sb.toString());
    }

    private String cuenta() {
        String c = cuenta.getText().toString().trim();
        if (c.isEmpty()) {
            Ui.error(this, salida, "Ingrese el número de cuenta");
            return null;
        }
        return c;
    }

    private Double importe() {
        try {
            double m = Double.parseDouble(importe.getText().toString().trim().replace(',', '.'));
            if (m > 0) return m;
        } catch (NumberFormatException ignored) { }
        Ui.error(this, salida, "Importe inválido (Ej. 250.00)");
        return null;
    }
}
