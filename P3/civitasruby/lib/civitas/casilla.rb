#encoding:utf-8

# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
# Abel

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
      new(nombre, nil, -1, -1, nil, nil)
    end

    def self.new_titulo_propiedad(titulo)
      new(titulo.get_nombre, titulo, titulo.get_precio_edificar, -1,nil, Tipo_casilla::CALLE)
    end

    def self.new_cantidad_nombre(cantidad, nombre)
      new(nombre, nil, cantidad, -1, nil, nil)
    end

    def self.new_num_casilla_carcel_nombre(num_casilla_carcel, nombre)
      new(nombre, nil, -1, num_casilla_carcel, nil, nil)
    end

    def self.new_mazo_nombre(mazo, nombre)
      new(nombre, nil, -1, -1, mazo, Tipo_casilla::SORPRESA)
    end

    def init
      @carcel = -1
      @importe = -1
      @nombre = ""
      @tipo = nil
      @mazo = nil
      @titulo_propiedad = nil
    end

    def informe(actual, todos)
      if jugador_correcto(actual, todos)
        Diario.instance.ocurre_evento("El jugador #{todos.get(actual).get_nombre}
           ha caido en la casilla #{@nombre}. Informacion de la casilla: #{to_s}
          Informacion del jugador: #{todos.get(actual).to_s}")
      end

      def jugador_correcto(actual, todos)
        actual >= 0 and actual < todos.length
      end

      @override
      def to_s
        "Carcel: #{@carcel}. Importe: #{@importe}. Nombre: #{@nombre}.
        Tipo: #{@tipo}. MazoSorpresas: #{@mazo}. TituloPropiedad: #{@titulo_propiedad.get_nombre}"
      end

      def recibejugador_impuesto(actual, todos)
        if jugador_correcto(actual, todos)
          informe(actual, todos)
          todos.get(actual).paga_impuesto(importe)
        end
      end

      def recibejugador_juez(actual, todos)
        if jugador_correcto(actual, todos)
          informe(actual, todos)
          todos.get(actual).encarcelar(@carcel)
        end
      end

      def get_nombre
        @nombre
      end

      def get_titulo_propiedad
        @titulo_propiedad
      end


    end





  end
end
