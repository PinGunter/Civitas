#encoding:utf-8

# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
# @author salva
require_relative "dado"
require_relative "tablero"
require_relative "mazo_sorpresas"
require_relative "gestor_estados"
require_relative "sorpresa"
require_relative "jugador"
require_relative "titulo_propiedad"
module Civitas
  #clase Civitas::Civitas_juego que representa la partida de civitas
  class Civitas_juego
    @@num_jugadores = 4
    @@casilla_carcel = 5
    @@num_casillas = 20
    # constructor de la clase
    # añade los jugadores pasados por parametro, decide quien empieza y inicializa el mazo y el tablero
    def initialize(nombres)
      @jugadores = Array.new
      nombres.each do |nombre|
        @jugadores << Jugador.jugador_1(nombre)
      end

      @gestor_estados = Gestor_estados.new
      @estado = @gestor_estados.estado_inicial
      @indice_jugador_actual = Dado.instance.quien_empieza(nombres.length)
      @mazo = nil
      @tablero = nil
      inicializar_tablero(@mazo)
      inicializar_mazo(@tablero)
    end

    # metodo para inicialiar el tablero
    def inicializar_tablero(mazo)
      @tablero = Tablero.new(@@casilla_carcel)
      @tablero.añade_casilla(Casilla.new_titulo_propiedad(Titulo_propiedad.new("Huerta Los Patos", 300, 10, 0.27, 300, 300)))
      @tablero.añade_casilla(Casilla.new_titulo_propiedad(Titulo_propiedad.new("Calle Rodahuevo", 300, 20, 0.53, 300, 300)))
      # añadir suerte
      # falta datos de Abel -> @tablero.añade_casilla(Casilla.new_titulo_propiedad(Titulo_propiedad.new("Huerta Los Patos", 600, 40, 0.51, 600, 600)))
      @tablero.añade_casilla(Casilla.new_titulo_propiedad(Titulo_propiedad.new("Huerta Jorobado", 800, 60, 0.6, 800, 800)))
      # añadir suerte
      @tablero.añade_casilla(Casilla.new_titulo_propiedad(Titulo_propiedad.new("Calle Hierbabuena", 1000, 80, 0.08, 1000, 1000)))
      @tablero.añade_casilla(Casilla.new_nombre("Parking Gratuito"))
      # falta datos de Abel -> @tablero.añade_casilla(Casilla.new_titulo_propiedad(Titulo_propiedad.new("Huerta Los Patos", 1200, 100, 0.75, 1200, 1200)))
      # añadir suerte
      # falta datos de Abel -> @tablero.añade_casilla(Casilla.new_titulo_propiedad(Titulo_propiedad.new("Huerta Los Patos", 1300, 110, 0.59, 1300, 1300)))
      # falta datos de Abel ->  @tablero.añade_casilla(Casilla.new_titulo_propiedad(Titulo_propiedad.new("Huerta Los Patos", 1400, 120, 0.90, 1400, 1400)))
      @tablero.añade_juez
      # falta datos de Abel -> @tablero.añade_casilla(Casilla.new_titulo_propiedad(Titulo_propiedad.new("Huerta Los Patos", 150, 130, 0.18, 1500, 1500)))
      @tablero.añade_casilla(Casilla.new_titulo_propiedad(Titulo_propiedad.new("Calle Pedro Antonio de Alarcón", 1540, 140, 0.77, 1540, 1540)))
      @tablero.añade_casilla(Casilla.new_cantidad_nombre(1000, "Impuesto"))
      @tablero.añade_casilla(Casilla.new_titulo_propiedad(Titulo_propiedad.new("Calle Marqués de Larios", 2000, 200, 0.64, 2000, 2000)))
    end

    def inicializar_mazo(tablero)
      @mazo = Mazo_sorpresas.new
      @mazo.al_mazo(Sorpresa.new_valor_texto(Tipo_sorpresa::PAGAR_COBRAR, -75, "Multa por exceso de velocidad. Paga 75"))
      @mazo.al_mazo(Sorpresa.new_valor_texto(Tipo_sorpresa::POR_CASA_HOTEL, -300, "La nueva PS5 ocupa demasiado espacio, debes hacer reformas. Paga 300 por cada casa u hotel"))
      @mazo.al_mazo(Sorpresa.new_valor_texto(Tipo_sorpresa::IR_CASILLA, 19, "Es la feria de Málaga y no te la puedes perder. Avanza hasta Calle Marqués de Larios"))
      @mazo.al_mazo(Sorpresa.new_tablero(Tipo_sorpresa::IR_CARCEL, @tablero))  # se hace con new_mazo?
      @mazo.al_mazo(Sorpresa.new_valor_texto(Tipo_sorpresa::POR_JUGADOR, -250, "Vas a cenar con tus amigos pero se les olvida la cartera. Paga 250 a cada jugador"))
      @mazo.al_mazo(Sorpresa.new_tablero(Tipo_sorpresa::SALIR_CARCEL, @tablero)) # se hace con new_mazo?
      #      @mazo.al_mazo(Sorpresa.new_valor_texto(Tipo_sorpresa::IR_CASILLA, valor, texto))
      #      @mazo.al_mazo(Sorpresa.new_valor_texto(Tipo_sorpresa::POR_CASA_HOTEL, valor, texto))
      #      @mazo.al_mazo(Sorpresa.new_valor_texto(Tipo_sorpresa::PAGAR_COBRAR, valor, texto))
      #      @mazo.al_mazo(Sorpresa.new_valor_texto(Tipo_sorpresa::POR_JUGADOR, valor, texto))

    end

    # metodo para añadir el dinero al jugador segun el numero de veces que pasa por salida
    def contabilizar_pasos_por_salida(jugador_actual)
      while @tablero.get_por_salida > 0
        jugador.get_por_salida
      end
    end

    # metodo para pasar al siguiente jugador
    def pasar_turno
      @indice_jugador_actual = (@indice_jugador_actual + 1) % @jugadores.length
    end

    # metodo para obtener el siguiente estado del juego
    def siguiente_paso_completado(operacion)
      @estado = @gestor_estados.siguiente_estado(@jugadores.at(@indice_jugador_actual), @estado, operacion)
    end

    # metodo que delega la operacion de construir casas
    def construir_casa(ip)
      @jugadores.at(@ondice_jugador_actual).construir_casa(ip)
    end

    # metodo que delega la operacion de construir hoteles
    def construir_hotel(ip)
      @jugadores.at(@indice_jugador_actual).construir_hotel(ip)
    end

    # metodo que delega la operacion de vender
    def vender(ip)
      @jugadores.at(@indice_jugador_actual).vender(ip)
    end

    #metodo que delega la operacion de hipotecar

    def hipotecar(ip)
      @jugadores.at(@indice_jugador_actual).hipotecar(ip)
    end

    # metodo que delega la operacion de cancelar hipoteca
    def cancelar_hipoteca(ip)
      @jugadores.at(@indice_jugador_actual).cancelar_hipoteca(ip)
    end

    # metodo que delega la operacion de salir de la carcel pagando
    def salir_carcel_pagando
      @jugadores.at(@indice_jugador_actual).salir_carcel_tirando
    end

    # metodo que delega la operacion de salir de la carcel tirando
    def salir_carcel_tirando
      @jugadores.at(@indice_jugador_actual).salir_carcel_pagando
    end

    # metodo que devuelve:
    # true si la partida debe acabar (alguien ha entrado en bancarrota)
    # false si todos los jugadores tiene dinero
    def final_del_juego
      bancarrota = false
      indice = 0
      while indice < @jugadores.length and not bancarrota
        bancarrota = @jugadores.at(indice).en_bancarrota
        indice += 1
      end
      return bancarrota
    end

    # metodo que produce la lista ordenada de jugadores en funcion de su saldo
    def ranking
      orden = @jugadores.sort { |a,b| a <=> b  }
      return orden
    end

    #metodo que devuelve el jugador actual
    def get_jugador_actual
      return @jugadores.at(@indice_jugador_actual)
    end

    #metodo que devuelve la casilla actual
    def get_casilla_actual
      puts get_jugador_actual.get_num_casilla_actual
      @tablero.get_casilla(get_jugador_actual.get_num_casilla_actual)
    end

    # metodo que proporciona informacion sobre el jugador actual
    def info_jugador_texto
      return @jugadores.at(@indice_jugador_actual).to_s
    end

    def avanza_jugador
      jugador_actual = @jugadores.at(@indice_jugador_actual)
      posicion_actual = jugador_actual.get_num_casilla_actual
      tirada = Dado.instance.tirar
      posicion_nueva = @tablero.nueva_posicion(posicion_actual, tirada)
      casilla = @tablero.get_casilla(posicion_nueva)
      contabilizar_pasos_por_salida(jugador_actual)
      jugador_actual.mover_a_casilla(posicion_nueva)
      casilla.recibe_jugador(@indice_jugador_actual, @jugadores)
      contabilizar_pasos_por_salida(jugador_actual)

    end

  end
end
