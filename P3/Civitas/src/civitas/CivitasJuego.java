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
        estado = gestorEstados.estadoInicial();

        indiceJugadorActual = Dado.getInstance().quienEmpieza(jugadores.size());

        mazo = new MazoSopresas();

        estado = EstadosJuego.INICIO_TURNO;

    }

    private void inicializaTablero(MazoSorpresas mazo) {
        tablero = new Tablero(casillaCarcel);
        //TituloPropiedad casita1 = new TituloPropiedad()
    }

    private void inicializaMazoSorpresas(Tablero tablero) {

    }

    private void contabilizarPasosPorSalida(Jugador jugadorActual) {
        if (tablero.getPorSalida() > 0) {
            jugadorActual.pasaPorSalida();
        }
    }

    private void pasarTurno() {
        if (indiceJugadorActual < jugadores.size()) {
            indiceJugadorActual++;
        } else {
            indiceJugadorActual = 0;
        }
    }

    public void siguientePasoCompletado(OperacionesJuego operacion) {
        estado = gestorEstados.siguienteEstado(jugadores.get(indiceJugadorActual),
                estado, operacion);

    }

    public boolean construirCasa(int ip) {
        return jugadores.get(indiceJugadorActual).construirCasa(ip);
    }

    public boolean construirHotel(int ip) {
        return jugadores.get(indiceJugadorActual).construirHotel(ip);
    }

    public boolean vender(int ip) {
        return jugadores.get(indiceJugadorActual).vender(ip);
    }

    public boolean hipotecar(int ip) {
        return jugadores.get(indiceJugadorActual).hipotecar(ip);
    }

    public boolean cancelarHipoteca(int ip) {
        return jugadores.get(indiceJugadorActual).cancelarHipoteca(ip);
    }

    public boolean salirCarcelPagando() {
        return jugadores.get(indiceJugadorActual).salirCarcelPagando();
    }

    public boolean salirCarcelTirando() {
        return jugadores.get(indiceJugadorActual).salirCarcelTirando();
    }

    //Cambiada visibilidad paquete->publica para usar en controlador
    public boolean finalDelJuego() {
        boolean res = false;
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).enBancarrota()) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Jugador> ranking() {
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

    public void mostrarRanking() {
        ArrayList<Jugador> ranking = ranking();
        for (int i = 0; i < ranking.size(); i++) {
            System.out.println("Posicion " + i + ": "
                    + ranking.get(i));
        }
    }

    public Casilla getCasillaActual() {
        return tablero.getCasilla(jugadores.get(indiceJugadorActual).getNumCasillaActual());
    }

    public Jugador getJugadorActual() {
        return jugadores.get(indiceJugadorActual);
    }

    public String infoJugadorTexto() {
        return "POSICION: " + jugadores.get(indiceJugadorActual).toString();
    }

    //void avanzaJugador()
    //boolean comprar()
    //OperacionesJuego siguientePaso()
}
