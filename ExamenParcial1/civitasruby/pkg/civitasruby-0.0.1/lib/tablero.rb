#encoding :utf-8
require_relative "casilla"
module Civitas
  class Tablero
    attr_reader :num_casilla_carcel
    
    def initialize(indice)
      if indice >= 1
        @num_casilla_carcel = indice
      else
        @num_casilla_carcel = 1
      end
      
      @casillas = Array.new
      @casillas << Casilla.new("Salida")
      @por_salida = 0
      @tiene_juez = false
      
    end    
    #metodo privado para saber si el tablero es correcto. parametro opcional num_casilla para comprobar tambien una posicion del tablero
    def correcto(num_casilla=0)
      return (@casillas.length > @num_casilla_carcel and @tiene_juez and num_casilla >= 0 and num_casilla < @casillas.length)
    end
    
    def get_por_salida
      if @por_salida > 0
        @por_salida -= 1
        return @por_salida+1
      else
        return @por_salida
      end
    end
    
    def añade_casilla(casilla)
      if @casillas.length == @num_casilla_carcel
        @casillas << Casilla.new("Cárcel")
      end
      @casillas << casilla
      if @casillas.length == @num_casilla_carcel
        @casillas << Casilla.new("Cárcel")
      end
    end
    
    def añade_juez
      if not @tiene_juez
        @casillas << Casilla.new("Juez")
        @tiene_juez = true
      end
    end
    
    def get_casilla(num_casilla)
      if correcto(num_casilla)
        return @casillas.at(num_casilla);
      else
        return nil
      end
    end
    
    def nueva_posicion(actual, tirada)
      if correcto
        nueva = actual + tirada
        if nueva > @casillas.length
          @por_salida += 1
          nueva = nueva % @casillas.length
        end
        return nueva
      else
        return -1
      end
    end
    
    def calcular_tirada(origen, destino)
      tirada = destino - origen
      if tirada < 0
        tirada += @casillas.length
      end
      return tirada
    end
    
    
    private :correcto
  end
end
