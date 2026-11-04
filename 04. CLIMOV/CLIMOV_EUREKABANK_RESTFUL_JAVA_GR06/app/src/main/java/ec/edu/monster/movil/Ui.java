package ec.edu.monster.movil;

import android.app.Activity;
import android.widget.TextView;

/** Ejecuta la llamada al servicio fuera del hilo de la pantalla y muestra el resultado o el error. */
final class Ui {

    interface Llamada<T> { T ejecutar() throws Exception; }
    interface Respuesta<T> { void mostrar(T resultado); }

    private Ui() { }

    static <T> void enSegundoPlano(Activity a, TextView estado, Llamada<T> llamada, Respuesta<T> respuesta) {
        estado.setTextColor(a.getColor(R.color.muted));
        estado.setText("» Conectando con el servidor…");
        new Thread(() -> {
            try {
                T r = llamada.ejecutar();
                a.runOnUiThread(() -> respuesta.mostrar(r));
            } catch (Exception e) {
                String msg = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
                a.runOnUiThread(() -> error(a, estado, msg));
            }
        }).start();
    }

    static void ok(Activity a, TextView t, String msg) {
        t.setTextColor(a.getColor(R.color.green));
        t.setText(msg);
    }

    static void error(Activity a, TextView t, String msg) {
        t.setTextColor(a.getColor(R.color.danger));
        t.setText("✖ " + msg);
    }
}
