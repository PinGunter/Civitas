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

        inicializarMazoSorpresas(); // hacer
        inicializarTablero(); // hacer

        //estado = EstadosJuego.INICIO_TURNO;

    }

    void inicializarTablero (/*MazoSorpresas mazo*/){
        tablero = new Tablero(casillaCarcel);
        
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Huerta Los Patos", 300, 10, (float) 0.27, 300, 300)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Rodahuevo", 300, 20, (float) 0.53, 300, 300)));    
        // añadir suerte
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Recogidas", 600, 40, (float) 0.51, 600, 600)));
        // carcel (ya añadida)
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Avenida de la Constitución", 700, 500, (float) 0.11, 700, 700)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Huerta Jorobado", 800, 60, (float) 0.6, 800, 800))); 
        // suerte
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Hierbabuena", 1000, 80, (float) 0.08, 1000, 1000))); 
        tablero.añadeCasilla(new Casilla("Parking Gratuito")); // DESCANSO
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Estación de Autobuses",1200, 100, (float) 0.75, 1200, 1200))); 
        // suerte
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Periodista Daniel Saucedo Aranda", 1300, 110, (float) 0.59, 1300, 1300))); 
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Periodista Rafael Gómez", 1400, 120, (float) 0.9, 1400, 1400)));        
        tablero.añadeJuez(); // JUEZ      
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Real de la Alhambra", 1500, 130, (float) 0.18, 1500, 1500)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Periodista Rafael Gómez", 1540, 140, (float) 0.77, 1540, 1540)));    
        tablero.añadeCasilla(new Casilla(1000, "Impuesto")); // IMPUESTO
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Periodista Rafael Gómez", 2000, 200, (float) 0.64, 2000, 2000)));           
    }

    void inicializarMazoSorpresas() { // AÑADIR
        mazo = new MazoSorpresas();
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
    
    // Debemos hacerlo publico para usarlo en VistaTextual
    public boolean finalDelJuego() {
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
    //se cambia visibilidad de paquete->publica para poder usarse en vista_textual
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
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int numCasillaActual = jugadorActual.getNumCasillaActual();
        Casilla casilla = tablero.getCasilla(numCasillaActual);
        TituloPropiedad titulo = casilla.getTitulo(); // getTitulo es getTituloPropiedad ??? 
        boolean res = jugadorActual.comprar(titulo);
        
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
