/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.Random;

/**
 *
 * @author salva
 */
public class Dado {

    private Random random;
    private int ultimoResultado;
    private boolean debug;

    static final private Dado instance = new Dado();
    static final private int SalidaCarcel = 5;

    private Dado() {
        debug = false;
        random = new Random();
    }
// EXAMEN
    //hacemos publica la visibilidad para usarla en el paquete exammen

    public static Dado getInstance() {
        return instance;
    }
// FIN DE EXAMEN

    int tirar() {
        int tirada = 0;
        if (debug) {
            tirada = 1;
        } else {
            tirada = random.nextInt(6) + 1;
        }
        ultimoResultado = tirada;
        return tirada;
    }

    boolean salgoDeLaCarcel() {
        int tirada = tirar();
        if (tirada >= SalidaCarcel) {
            ultimoResultado = tirada;
        }
        return tirada >= SalidaCarcel;
    }

    int quienEmpieza(int n) {
        return random.nextInt(n);
    }
// EXAMEN
    //hacemos publica la visibilidad para usarla en el paquete exammen

    public void setDebug(Boolean d) {
        debug = d;
        if (debug) {
            Diario.getInstance().ocurreEvento("Activado modo debug del dado");
        } else {
            Diario.getInstance().ocurreEvento("Desactivado modo debug del dado");
        }
    }
// FIN DE EXAMEN

    int getUltimoResultado() {
        return ultimoResultado;
    }
    // FIN DE EXAMEN
}
