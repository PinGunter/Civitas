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

    public CivitasJuego(ArrayList<Jugador> nombres) {
        jugadores = new ArrayList<>();
        for (int i = 0; i < nombres.size(); i++) {
            Jugador j = new Jugador(nombres.get(i));
            jugadores.add(j);
        }

        gestorEstados = new GestorEstados();
        gestorEstados.estadoInicial();
        estado = EstadosJuego.INICIO_TURNO;

        indiceJugadorActual = Dado.getInstance().quienEmpieza(jugadores.size());

        inicializarMazoSorpresas(this.tablero); // hacer
        inicializarTablero(this.mazo); // hacer

    }

    private void inicializarTablero (MazoSorpresas mazo){
        tablero = new Tablero(casillaCarcel);

        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Huerta Los Patos", 300, 10,  0.27f, 300, 300)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Rodahuevo", 300, 20,  0.53f, 300, 300)));
        tablero.añadeCasilla(new Casilla(mazo, "SUERTE"));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Recogidas", 600, 40, 0.51f, 600, 600)));
        // carcel (ya añadida)
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Avenida de la Constitución", 700, 50, 0.11f, 700, 700)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Huerta Jorobado", 800, 60, 0.6f, 800, 800)));
        tablero.añadeCasilla(new Casilla(mazo, "SUERTE"));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Hierbabuena", 1000, 80, 0.08f, 1000, 1000)));
        tablero.añadeCasilla(new Casilla("Parking Gratuito")); // DESCANSO
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Estación de Autobuses",1200, 100, 0.75f, 1200, 1200)));
        tablero.añadeCasilla(new Casilla(mazo, "SUERTE"));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Periodista Daniel Saucedo Aranda", 1300, 110, 0.59f, 1300, 1300)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Periodista Rafael Gómez", 1400, 120, 0.9f, 1400, 1400)));
        tablero.añadeJuez(); // JUEZ
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Real de la Alhambra", 1500, 130, 0.18f, 1500, 1500)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Pedro Antonio de Alarcón", 1540, 140, 0.77f, 1540, 1540)));
        tablero.añadeCasilla(new Casilla(1000, "Impuesto")); // IMPUESTO
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Marqués de Larios", 2000, 200, 0.64f, 2000, 2000)));
    }

    private void inicializarMazoSorpresas(Tablero t) { // AÑADIR
        mazo = new MazoSorpresas();
        mazo.alMazo(new Sorpresa(TipoSorpresa.PAGARCOBRAR, -75, "Multa por exceso de velocidad. Paga 75" ));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PORCASAHOTEL, -300, "La nueva PS5 ocupa demasiado espacio, debes hacer reformas. Paga 300 por cada casa u hotel" ));
        mazo.alMazo(new Sorpresa(TipoSorpresa.IRCASILLA, 19, "Es la feria de Málaga y no te la puedes perder. Avanza hasta Calle Marqués de Larios" ));
        mazo.alMazo(new Sorpresa(TipoSorpresa.IRCARCEL, t));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PORJUGADOR, 250, "Vas a cenar con tus amigos pero se les olvida la cartera. Cada jugador te paga 250" ));
        mazo.alMazo(new Sorpresa(TipoSorpresa.SALIRCARCEL, t));
        mazo.alMazo(new Sorpresa(TipoSorpresa.IRCASILLA, 13, "Suspendes el examen de PDOO y tienes que ir a revisión. Ve a la ETSIIT"  ));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PORCASAHOTEL, 350, "Hay un terremoto y el seguro te paga 350 por cada casa y hotel "));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PAGARCOBRAR, 350, "Te conviertes en tu propio jefe y ganas 600" ));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PORJUGADOR, 350, "Vas de fiesta con tus amigos sin medidas de seguridad y ahora debes pagarle la PCR"));
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
