#encoding:utf-8
# author: Salva 
#
require_relative "diario"
require_relative "tipo_sorpresa"
require_relative "tablero"
require_relative "mazo_sorpresas"

module Civitas
  # Clase Sorpresa
  # Representa las cartas sorpresa que estan dentro del Civitas::MazoSorpresas
  class Sorpresa
    
    def init
      @valor = -1
      @mazo = nil
      @tablero = nil
    end
    
    def initialize(tipo,*resto)
      init
      @tipo = tipo
      @texto = nil   
      if resto.length == 2
        @valor = resto.at(0)
        @texto = resto.at(1)
      elsif resto.length == 3
        @tablero = resto.at(0)
        @valor = resto.at(1)
        @texto = resto.at(2)
      elsif resto.length == 1 and resto.at(0).instance_of? MazoSorpresas
        @mazo = resto.at(0)
      elsif resto.length == 1 and resto.at(0).instance_of? Tablero
        @tablero = resto.at(0)
      end
    end
    
    def jugador_correcto(actual,todos)
      correcto = actual >= 0 and actual < todos.length
      return correcto
    end
    
    def informe(actual,todos)
      Diario.instance.ocurre_evento("Jugador: " + todos.at(actual).nombre + " recibe sorpresa " + @nombre)
    end
    
    def aplicar_a_jugador(actual,todos)
      case @tipo
      when Tipo_sorpresas::IR_CARCEL
        aplicar_a_jugador_ir_carcel(actual,todos)
      when Tipo_sorpresas::IR_CASILLA
        aplicar_a_jugador_ir_a_casilla(actual,todos)

      when Tipo_sorpresas::PAGAR_COBRAR
        aplicar_a_jugador_pagar_cobrar(actual,todos)

      when Tipo_sorpresas::SALIR_CARCEL
        aplicar_a_jugador_salir_carcel(actual,todos)

      when Tipo_sorpresas::POR_CASA_HOTEL
        aplicar_a_jugador_por_casa_hotel(actual,todos)

      when Tipo_sorpresas::POR_JUGADOR
        aplicar_a_jugador_por_jugador(actual,todos)

      end
    end
    
  end
  
  
end
