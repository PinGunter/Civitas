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
public class CivitasJuego {

    private int indiceJugadorActual;
    private MazoSorpresas mazo;
    private Tablero tablero;
    private ArrayList<Jugador> jugadores;
    private GestorEstados gestorEstados;
    private EstadosJuego estado;

    static final int numJugadores = 4;
    static final int casillaCarcel = 5;
    static final int numCasillas = 20;

    CivitasJuego(ArrayList<Jugador> nombres) {
        jugadores = new ArrayList<>();
        for (int i = 0; i < nombres.size(); i++) {
            Jugador j = new Jugador(nombres.get(i));
            jugadores.add(j);
        }

        gestorEstados = new GestorEstados();
        gestorEstados.estadoInicial();
        estado = EstadosJuego.INICIO_TURNO;

        indiceJugadorActual = Dado.getInstance().quienEmpieza(jugadores.size());

        //mazo = new MazoSopresas();
        inicializarMazoSorpresas(); // hacer
        //tablero = new Tablero();
        inicializarTablero(); // hacer

        estado = EstadosJuego.INICIO_TURNO;

    }

    void inicializarTablero() { // AÑADIR 
        tablero = new Tablero(casillaCarcel);
        TituloPropiedad calle = new TituloPropiedad("Periodista Daniel Saucedo", 50, 1, 200, 400, 250);
        //TituloPropiedad(String nombre, float alquBase, float factrevalor, float hipbase, float precioComp, float precioEdifi)
    }

    void inicializarMazoSorpresas() { // AÑADIR
        //Sorpresa sorpresa = new Sorpresa()
    }

    void contabilizarPasosPorSalida(Jugador jugadorActual) {
        if (tablero.getPorSalida() > 0) {
            jugadorActual.pasaPorSalida();
        }
    }

    void pasarTurno() {
        if (indiceJugadorActual < jugadores.size()) {
            indiceJugadorActual++;
        } else {
            indiceJugadorActual = 0;
        }
    }

    void siguientePasoCompletado(OperacionesJuego operacion) {
        estado = gestorEstados.siguienteEstado(jugadores.get(indiceJugadorActual),
                estado, operacion);

    }

    boolean construirCasa(int ip) {
        return jugadores.get(indiceJugadorActual).construirCasa(ip);
    }

    boolean construirHotel(int ip) {
        return jugadores.get(indiceJugadorActual).construirHotel(ip);
    }

    boolean vender(int ip) {
        return jugadores.get(indiceJugadorActual).vender(ip);
    }

    boolean hipotecar(int ip) {
        return jugadores.get(indiceJugadorActual).hipotecar(ip);
    }

    boolean cancelarHipoteca(int ip) {
        return jugadores.get(indiceJugadorActual).cancelarHipoteca(ip);
    }

    boolean salirCarcelPagando() {
        return jugadores.get(indiceJugadorActual).salirCarcelPagando();
    }

    boolean salirCarcelTirando() {
        return jugadores.get(indiceJugadorActual).salirCarcelTirando();
    }

    boolean finalDelJuego() {
        boolean res = false;
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).enBancarrota()) {
                return true;
            }
        }
        return false;
    }

    ArrayList<Jugador> ranking() {
        ArrayList<Jugador> ranking = new ArrayList<Jugador>();

        for (int i = 0; i < jugadores.size(); i++) {
            ranking.add(jugadores.get(i));
        }

        for (int i = 0; i < ranking.size(); i++) {
            for (int j = i + 1; j < ranking.size(); j++) {
                if (ranking.get(i).compareTo(ranking.get(j)) < 0) {
                    Jugador jug = new Jugador(ranking.get(i));
                    ranking.set(i, ranking.get(j));
                    ranking.set(j, jug);
                }
            }
        }
        return ranking;

    }

    void mostrarRanking() {
        for (int i = 0; i < ranking().size(); i++) {
            System.out.println("Posicion " + i + ": "
                    + ranking().get(i));
        }
    }

    Casilla getCasillaActual() {
        return tablero.getCasilla(jugadores.get(indiceJugadorActual).getNumCasillaActual());
    }

    Jugador getJugadorActual() {
        return jugadores.get(indiceJugadorActual);
    }

    String infoJugadorTexto() {
        return "POSICION: " + jugadores.get(indiceJugadorActual).toString();
    }

    //void avanzaJugador()
    // necesita dos llamadas al metodo contabilizarpasoporsalida(jugadoractual)
    private void avanzaJugador() {
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int posicionActual = jugadorActual.getNumCasillaActual();
        int tirada = Dado.getInstance().tirar();
        int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla = tablero.getCasilla(posicionNueva);
        contabilizarPasosPorSalida(jugadores.get(indiceJugadorActual)); // contabilizarPasosPorSalida(jugadores.get(jugadorActual))
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(indiceJugadorActual, jugadores); // hacer recibeJugador (clase Casilla)
        contabilizarPasosPorSalida(jugadores.get(indiceJugadorActual));
    }

    //boolean comprar()
    public Boolean comprar() {
        Boolean res = false;
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int posicionActual = jugadorActual.getNumCasillaActual();
        
        // Falta saber la sorpresa en posicionActual
        
        //TituloPropiedad titulo = ;
        //res = jugadorActual.comprar(titulo);

        return res;
    }

    //OperacionesJuego siguientePaso()
    public OperacionesJuego siguientePaso() {
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        OperacionesJuego operacion = gestorEstados.operacionesPermitidas(jugadorActual, estado);
        if (operacion == OperacionesJuego.PASAR_TURNO) {
            pasarTurno();
            siguientePasoCompletado(operacion);
        }
        if (operacion == OperacionesJuego.AVANZAR) {
            avanzaJugador();
            siguientePasoCompletado(operacion);
        }
        return operacion;
    }

}
