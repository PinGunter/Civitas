/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author Salva
 */
public class Casilla {

    //atributo de clase
    private static int carcel;
    //atributos de instancia
    private float importe;
    private String nombre;

    //atributo de referencia
    TipoCasilla tipo;
    MazoSorpresas mazo; //tipo={SORPRESA}
    TituloPropiedad tituloPropiedad; //tipo={CALLE}

    //metodos
    Casilla(String nombre) {
        init();
        this.nombre = nombre;
    }

    Casilla(TituloPropiedad titulo) {
        init();
        tituloPropiedad = titulo;
        tipo = tipo.CALLE;
        nombre = tituloPropiedad.getNombre();
        importe = titulo.getPrecioCompra();
    }

    Casilla(float cantidad, String nombre) {
        init();
        this.nombre = nombre;
        importe = cantidad;
    }

    Casilla(int numCasillaCarcel, String nombre) {
        init();
        this.nombre = nombre;
        carcel = numCasillaCarcel;
    }

    Casilla(MazoSorpresas mazo, String nombre) {
        init();
        this.nombre = nombre;
        this.mazo = mazo;
        this.tipo = tipo.SORPRESA;
    }

    private void init() {
        carcel = -1;
        importe = -1;
        nombre = "";
        tipo = null;
        mazo = null;
        tituloPropiedad = null;

    }

    private void informe(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            Diario.getInstance().ocurreEvento("El jugador " + todos.get(actual).getNombre() + " ha caido en la casilla " + nombre
                    + "\n=========\nInformación de la casilla:\n" + toString()
                    + "\n=========\nInformación del jugador:\n" + todos.get(actual).toString());

        }
    }

    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos) {
        return actual >= 0 && actual < todos.size();
    }

    @Override
    public String toString() {
        String info = "Carcel: " + carcel
                + "\nImporte: " + importe
                + "\nNombre: " + nombre
                + "\nTipo: " + tipo
                + "\nMazoSorpresas: " + mazo
                + "\nTituloPropiedad: " + tituloPropiedad.getNombre();
        return info;
    }

    private void recibeJugador_impuesto(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            todos.get(actual).pagaImpuesto(importe);
        }
    }

    private void recibeJugador_juez(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            todos.get(actual).encarcelar(carcel);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public TituloPropiedad getTitulo() {
        return tituloPropiedad;
    }

    void recibeJugador(int actual, ArrayList<Jugador> todos) {
        switch (tipo) {
            case CALLE:
                this.recibeJugador_calle(actual, todos);
                break;
            case IMPUESTO:
                this.recibeJugador_impuesto(actual, todos);
                break;
            case JUEZ:
                this.recibeJugador_juez(actual, todos);
                break;
            case SORPRESA:
                this.recibeJugador_sorpresa(actual, todos);
                break;
            default:
                informe(actual, todos);
                break;
        }
    }

    private void recibeJugador_calle(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            Jugador jugador = todos.get(actual);
            if (!tituloPropiedad.tienePropietario()) {
                jugador.puedeComprarCasilla();
            } else {
                tituloPropiedad.tramitarAlquiler(jugador);
            }
        }

    }

    private void recibeJugador_sorpresa(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            Sorpresa sorpresa = mazo.siguiente();
            informe(actual, todos);
            sorpresa.aplicarAJugador(actual, todos);
        }
    }
}
