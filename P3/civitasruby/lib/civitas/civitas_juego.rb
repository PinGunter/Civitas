# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
# @author salva
require_relative "tablero"
require_relative "mazo_sorpresas"
require_relative "gestor_estados"
#require_relative "jugador"
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
        @jugadores << Jugador.new(nombre)
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
      ## añadir casillas
    end
    
    def inicializar_mazo(tablero)
      #añadir cartas sorpresa
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
      return @tablero.get_casilla(get_jugador_actual.num_casilla_actual)
    end
    
    # metodo que proporciona informacion sobre el jugador actual
    def info_jugador_texto
      return @jugadores.at(@indice_jugador_actual).to_s
    end
  end
end
