#encoding:utf-8

# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "diario"
module Civitas
  class Jugador
    @@casas_max = 4
    @@casas_por_hotel = 4
    @@hoteles_max = 4
    @@paso_por_salida = 1000
    @@precio_libertad = 200
    @@saldo_inicial = 750

    def initialize(encarcelado,nombre,num_casilla_actual,puede_comprar,saldo ,salvoconducto,propiedades)
      @nombre = nombre
      @encarcelado = encarcelado
      @num_casilla_actual = num_casilla_actual
      @puede_comprar = puede_comprar
      @saldo = saldo
      @salvoconducto = salvoconducto #sorpresa
      @propiedades = propiedades # array de titulopropiedad
    end

    def self.jugador_1(n)
      #@nombre = nombre
      #@puede_comprar = false
      #@saldo = -1
      #@encarcelado = false
      #@num_casilla_actual = -1
      #@propiedades = Array.new
      #@salvo_conductor = nil
      self.new(false,n,-1,false,-1,nil,[])
    end

    def self.jugador_2(otro)
      new(otro.encarcelado, otro.nombre, otro.num_casilla_actual, otro.puede_comprar,
        otro.saldo, otro.salvoconducto, otro.propiedades)
    end

    def debe_ser_encarcelado
      if @encarcelado == true
        res = false
      elsif tiene_salvoconducto == true
        perder_salvoconducto
        res = false
        Diario.instance.ocurre_evento("#{@nombre} utiliza carta salvoconducto y no va a la carcel")
      else
        res = true
      end
      return res
    end

    def encarcelar(num_casilla_carcel)
      if debe_ser_encarcelado == true
        mover_a_casilla(num_casilla_carcel)
        @encarcelado = true
        Diario.instance.ocurre_evento("#{@nombre}  va a la cárcel")
      else
        Diario.instance.ocurre_evento("#{@nombre}  no va a la cárcel")
      end
    end

    def obtener_salvoconducto(sorpresa)
      if @encarcelado == true
        res = false
      else
        @salvoconducto = sorpresa
        @devolver = true
      end
      return res
    end

    def perder_salvoconducto
      @salvoconducto.usada
      @salvoconducto = nill
    end

    def tiene_salvoconducto
      res = false
      if @salvoconducto != nill
        res = true
      end
      return res
    end

    def puede_comprar_casilla
      @puede_comprar = true
      if @encarcelado == true
        @puede_comprar = false
      end
      return @puede_comprar
    end

    def paga(cantidad)
      (modificar_saldo(cantidad*-1))
    end

    def paga_impuesto(cantidad)
      if @encarcelado == true
        res = false
      else
        res = paga(cantidad)
      end
      return res
    end

    def paga_alquiler(cantidad)
      (paga_impuesto(cantidad))
    end

    def recibe(cantidad)
      if @encarcelado == true
        res = false
      else
        res = modificar_saldo(cantidad)
      end
      return res
    end

    def modificar_saldo(cantidad)
      @saldo += cantidad
      Diario.instance.ocurre_evento("Se incrementa el saldo del jugador #{@nombre}")
      return true
    end

    def mover_a_casilla(num_casilla)
      if @encarcelado == true
        res = false
      else
        @num_casilla = num_casilla
        Diario.instance.ocurre_evento("#{@nombre} se mueve a casilla: #{@num_casilla}")
        res = true
        @puede_comprar = false
      end
      return res
    end

    def puedo_gastar(precio)
      if @encarcelado == true
        res = false
      elsif @saldo >= precio
        res = true
      end
      return res
    end

    def vender(ip)
      if @encarcelado == true
        res = false
      else
        if (existe_la_propiedad(ip) == true and @propiedades.at(ip).hipotecado() == false)
          propiedad == @propiedades.at(ip)
          devolver = propiedad.vender(self)
          Diario.instance.ocurre_evento("#{@nombre} vende la casilla #{@propiedades.at(ip).nombre()}")
          propiedades.delete_at(ip)
        end
      end
      return res
    end

    def tiene_algo_que_gestionar
      res = false
      if @propiedades.lenght > 0
        res = true
      end
      return res
    end

    def salir_carcel_pagando
      res = false
      if @saldo >= @@precio_libertad
        res = true
      end
      return res
    end

    def salir_carcel_tirando
      res = false
      if Dado.instance.salgo_de_la_carcel == true
        res = true
        @encarcelado = false
        Diario.instance.ocurre_evento("#{@nombre} sale de la carcel tirando")
      end
      return res
    end

    def pasa_por_salida
      modificar_saldo(@@paso_por_salida)
      Diario.instance.ocurre_evento("#{@nombre} pasa por salida")
      return true
    end

    def compare_to(otro)
      res = @saldo <=> otro.get_saldo
    end

    def cantidad_casas_hoteles
      total = 0
      for i in (0..@propiedades.lenght-1)
        total+=@propiedades.at(i).num_casas
        total+=@propiedades.at(i).num_hoteles
      end
      return total
    end

    def en_bancarrota
      res = false
      if saldo <= 0
        res = true
      end
      return res
    end

    def existe_la_propiedad(ip)
      res = false
      for i in(0..@propiedades.length-1)
        if @propiedades.at(ip) == ip
          res = true
        end
      end
      return res
    end

    def get_casas_max
      @@casas_max
    end

    def get_casas_por_hotel
      @@casas_por_hotel
    end

    def get_hoteles_max
      @@hoteles_max
    end

    def get_nombre
      @nombre
    end

    def get_num_casilla_actual
      @num_casilla_actual
    end

    def get_precio_libertad
      @@precio_libertad
    end

    def get_premio_por_salida
      @@paso_por_salida
    end

    def get_propiedades
      @propiedades
    end

    def get_puede_comprar
      @puede_comprar
    end

    def get_saldo
      @saldo
    end

    def get_is_encarcelado
      @encarcelado
    end

    def puedo_edificar_casa(propiedad)
      if @saldo >= propiedad.get_precio_edificar && propiedad.get_num_casas < casas_max
        return true
      end
      return false
    end

    def puedo_edificar_hotel(propiedad)
      if @saldo >= propiedad.get_precio_edificar && propiedad.get_num_casas < hoteles_max
        return true
      end
      return false
    end

    @override
    def to_s
      "Nombre: #{@nombre}
      Encarcelado: #{@encarcelado}
      Puede comprar:#{@puede_comprar}
      Numero casilla actual: #{@num_casilla_actual}
      Saldo: #{@saldo}
      Numero de propiedades: #{@propiedades.length}
      Propiedades #{@propiedades}"
    end

  end
end
