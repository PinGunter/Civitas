/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author abelrios
 */
public class Sorpresa {

    private String texto;
    private Tablero tablero;
    private int valor;
    private TipoSorpresa sorpresa;
    private MazoSorpresas mazo;

    Sorpresa(TipoSorpresa tipo, Tablero tablero) {
        init();
        sorpresa = tipo;
        this.tablero = tablero;
    }

    Sorpresa(TipoSorpresa tipo, Tablero t, int valor, String texto) {
        init();
        sorpresa = tipo;
        this.tablero = tablero;
        this.valor = valor;
        this.texto = texto;
    }

    Sorpresa(TipoSorpresa tipo, int valor, String texto) { //EVITARCARCEL
        init();
        sorpresa = tipo;
        this.valor = valor;
        this.texto = texto;
    }

    Sorpresa(TipoSorpresa tipo, MazoSorpresas m) { // RESTO
        init();
        sorpresa = tipo;
        mazo = m;
    }

    boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos) {
        if (todos.size() > actual && actual >= 0) {
            return true;
        }
        return false;
    }

    void informe(int actual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("Se esta aplicando una sorpresa a"
                + todos.get(actual).getNombre()); // todos[actual].nombre());
    }

    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        switch (sorpresa) {
            case IRCARCEL:
                aplicarAJugador_irCarcel(actual, todos);
            case IRCASILLA:
                aplicarAJugador_irACasilla(actual, todos);
            case PAGARCOBRAR:
                aplicarAJugador_pagarCobrar(actual, todos);
            case PORCASAHOTEL:
                aplicarAJugador_porCasaHotel(actual, todos);
            case PORJUGADOR:
                aplicarAJugador_porJugador(actual, todos);
            case SALIRCARCEL:
                aplicarAJugador_salirCarcel(actual, todos);
        }
    }

    void aplicarAJugador_irCarcel(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }

    void aplicarAJugador_irACasilla(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            int casilla = todos.get(actual).getNumCasillaActual();
            int tirada = tablero.calcularTirada(casilla, valor);
            int n = tablero.nuevaPosicion(casilla, tirada);
            todos.get(actual).moverACasilla(n);
            tablero.getCasilla(n).recibeJugador(actual, todos);
        }
    }

    void aplicarAJugador_pagarCobrar(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            todos.get(actual).modificarSaldo(valor);
        }
    }

    void aplicarAJugador_porCasaHotel(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            todos.get(actual).modificarSaldo(valor * todos.get(actual).cantidadCasasHoteles());
        }
    }

    void aplicarAJugador_porJugador(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            Sorpresa s = new Sorpresa(TipoSorpresa.PAGARCOBRAR, valor * -1, "");

            for (int i = 0; i < todos.size(); i++) // o todos.size();
            {
                if (i != actual) {
                    todos.get(i).paga(s.valor);
                }
            }

            Sorpresa s1 = new Sorpresa(TipoSorpresa.PAGARCOBRAR, valor * todos.size() - 1, "");
            todos.get(actual).recibe(s1.valor);
        }
    }

    void aplicarAJugador_salirCarcel(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            Boolean loTienen = false;
            for (int i = 0; i < todos.size() && !loTienen; i++) {
                if (todos.get(i).tieneSalvoconducto()) {
                    loTienen = true;
                }
            }

            if (!loTienen) {
                todos.get(actual).obtenerSalvoconducto(new Sorpresa(TipoSorpresa.SALIRCARCEL, -1, "")); // this?
                salirDelMazo();
            }
        }
    }

    void salirDelMazo() {
        if (sorpresa == TipoSorpresa.SALIRCARCEL) {
            mazo.inhabilitarCartaEspecial(this); // revisar
        }
    }

    void usada() {
        if (sorpresa == TipoSorpresa.SALIRCARCEL) {
            mazo.habilitarCartaEspecial(this); // revisar
        }
    }

    @Override
    public String toString() {
        return texto;
    }

    void init() {
        valor = -1;
        tablero = null;
        mazo = null;
    }
}
