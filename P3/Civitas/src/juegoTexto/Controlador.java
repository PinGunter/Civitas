/**
 * @author Salva
 */
package juegoTexto;

import civitas.CivitasJuego;
import civitas.OperacionesJuego;

public class Controlador {

    private CivitasJuego juego;
    private VistaTextual vista;

    Controlador(CivitasJuego juego, VistaTextual vista) {
        this.juego = juego;
        this.vista = vista;
    }

    void juega() {
        vista.setCivitasJuego(juego);
        while (!juego.finalDelJuego()) {
            vista.actualizarVista();
            vista.pausa();
            OperacionesJuego siguiente = juego.siguientePaso();
            vista.mostrarSiguienteOperacion(siguiente);
            if (siguiente != OperacionesJuego.PASAR_TURNO) {
                vista.mostrarEventos();
            }
            if (!juego.finalDelJuego()) {
                switch (siguiente) {
                    case COMPRAR:
                        Respuestas respuesta = vista.comprar();
                        if (respuesta == Respuestas::SI) {
                            juego.comprar();
                        }
                        juego.siguientePasoCompletado(siguiente);
                        break;
                    case GESTIONAR:
                        vista.gestionar();
                        int gestion = vista.getGestion();
                        int propiedad = vista.getPropiedad();
                        OperacionInmobiliaria operacion = new OperacionInmobiliaria(gestion, propiedad);
                        switch (operacion.getGestion()) {
                            case VENDER:
                                juego.vender(propiedad);
                                break;
                            case HIPOTECAR:
                                juego.hipotecar(propiedad);
                                break;
                            case CANCELAR_HIPOTECA:
                                juego.cancelarHipoteca(propiedad);
                                break;
                            case CONSTRUIR_CASA:
                                juego.construirCasa(propiedad);
                                break;
                            case CONSTRUIR_HOTEL:
                                juego.construirHotel(propiedad);
                                break;
                            case TERMINAR:
                                juego.siguientePasoCompletado(siguiente);
                                break;
                        }
                        break;
                    case SALIR_CARCEL:
                        SalidasCarcel salida = vista.salirCarcel();
                        switch (salida) {
                            case TIRANDO:
                                juego.salirCarcelTirando();
                                break;
                            case PAGANDO:
                                juego.salirCarcelPagando();
                                break;
                        }
                        juego.siguientePasoCompletado(siguiente);
                        break;

                }
            } else {
                juego.mostrarRanking();
            }
        }
    }
}
