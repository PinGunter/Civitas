#encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative "tipo_casilla"
module Civitas
  class Casilla

    def initialize(nombre, titulo, cantidad, num_casilla_carcel, mazo, tipo)
      init
      @nombre = nombre
      @titulo = titulo
      @cantidad = cantidad
      @num_casilla_carcel = num_casilla_carcel
      @mazo = mazo
      @tipo = tipo
    end

    def self.new_nombre(nombre)
      Casilla.new(nombre, nil, -1, -1, nil, Tipo_casilla::DESCANSO)
    end

    def self.new_titulo_propiedad(titulo)
      Casilla.new(titulo.get_nombre, titulo, titulo.get_precio_compra, -1,nil, Tipo_casilla::CALLE)
    end

    def self.new_cantidad_nombre(cantidad, nombre)
      Casilla.new(nombre, nil, cantidad, -1, nil, nil)
    end

    def self.new_num_casilla_carcel_nombre(num_casilla_carcel, nombre)
      Casilla.new(nombre, nil, -1, num_casilla_carcel, nil, Tipo_casilla::JUEZ)
    end

    def self.new_mazo_nombre(mazo, nombre)
      Casilla.new(nombre, nil, -1, -1, mazo, Tipo_casilla::SORPRESA)
    end

    def init
      @importe = -1
      @nombre = ""
      @tipo = nil
      @mazo = nil
      @titulo_propiedad = nil
    end

    def recibe_jugador(iactual, todos)
      case tipo
      when CALLE
        recibeJugador_calle(iactual, todos) # hacer
      when IMPUESTO
        recibeJugador_impuesto(iactual, todos)
      when JUEZ
        recibeJugador_juez(iactual, todos)
      when SORPRESA
        recibeJugador_sorpresa(iactual, todos) # hacer
      else
        informe(iactual, todos)
      end
    end

    def recibeJugador_calle(iactual, todos)
      if (jugador_correcto(iactual, todos))
        informe(iactual, todos)
        jugador = todos.get(actual)
        if(!@titulo.tiene_propietario)
          jugador.puede_comprar_casilla
        else
          @titulo.tramitar_alquiler(jugador)
        end

      end
    end

    def recibeJugador_sorpresa(iactual, todos)
      if(jugador_correcto(iactual, todos))
        sorpresa = @mazo.siguiente
      end
      if(jugador_correcto(iactual, todos))
        informe(iactual, todos)
      end
      if(jugador_correcto(iactual, todos))
        sorpresa.aplicar_a_jugador(iactual, todos)
      end
    end

    def informe(actual, todos)
      if jugador_correcto(actual, todos)
        Diario.instance.ocurre_evento("El jugador #{todos.get(actual).get_nombre}
           ha caido en la casilla #{@nombre}. Informacion de la casilla: #{to_s}
          Informacion del jugador: #{todos.get(actual).to_s}")
      end
    end

    def jugador_correcto(actual, todos)
      actual >= 0 and actual < todos.length
    end

    @override
    def to_s
      info = "Nombre: #{@nombre}"
      if @tipo == Tipo_casilla::CALLE
        info += "\nPrecio: " + @importe
        propietario = @titulo_propiedad.get_propietario
        if propietario != nil
            info += "\nPropietario: " + propietario.get_nombre
        else
            info += "\nLa casilla no tiene dueño"
        end

      end

    end

    def recibeJugador_impuesto(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        todos.get(actual).paga_impuesto(importe)
      end
    end

    def recibeJugador_juez(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        todos.get(actual).encarcelar(@carcel)
      end
    end

    def get_nombre
      @nombre
    end

    def get_titulo
      @titulo_propiedad
    end

    private :informe, :init, :recibeJugador_calle, :recibeJugador_juez, :recibeJugador_sorpresa, :recibeJugador_impuesto

  end




end
