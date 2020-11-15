# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
# Salva Romero
module Civitas
  class Titulo_propiedad
    attr_reader :nombre, :precio_compra, :alquiler_base, :factor_revalorizacion,
      :hipoteca_base, :precio_edificar, :num_hoteles, :num_casas
    attr_accessor :hipotecada, :propietario

    def initialize(nom, precioComp, alquiler, factor, hipoteca, precioEdif)
      @nombre = nom
      @precio_compra = precioComp
      @alquiler_base = alquiler
      @factor_revalorizacion = factor
      @hipoteca_base = hipoteca
      @precio_edificar = precioEdif
      @hipotecada = false
      @num_hoteles = 0
      @num_casas = 0
      @propietario = nill
    end

    @override
    def to_s()
      "\n-TituloPropiedad: \nNombre: #{@nombre} \nHipotecada: #{@hipotecada}
         \nPrecio compra: #{@precioCompra} \nAlquiler base: #{@alquilerBase}
         \nFactor revalorizacion: #{@factorRevalorizacion} \nHipoteca base: #{@hipotecaBase}
         \nPrecio edificar: #{@precioEdificar} \nNúmero de hoteles: #{@numHoteles}
         \nNúmero de casas: #{@numCasas}\n\n"
    end

    def get_precio_alquiler
      res = @alquiler_base * (1+(@num_casas*0.5)
        + (@num_hoteles*2.5))
      if hipotecado == true or propietario_encarcelado
        res = 0
      end
      return res
    end

    def get_importe_hipoteca
      (@hipoteca_base*(1+(@num_casas*0.5) + (@num_hoteles*2.5)))
    end

    def get_importe_cancelar_hipoteca
      (get_importe_hipoteca * @@factor_intereses_hipoteca)
    end

    def actualiza_propietario_por_conversion(jugador)
      @propietario = jugador
    end

    def cancelar_hipoteca

    end

    def cantidad_casas_hoteles
      (get_num_casas + get_num_hoteles)
    end

    def comprar(jugador)

    end

    def construir_casa(jugador)

    end

    def construir_hotel(jugador)

    end

    def es_este_el_propietario(jugador)
      res = false
      if jugador == get_propietario
        res = true
      end
      return res
    end

    def get_hipotecado
      @hipotecado
    end

    def get_nombre
      @nombre
    end

    def get_casas
      @num_casas
    end

    def num_hoteles
      @num_hoteles
    end

    def get_precio_edificar
      @precio_edificar
    end

    def get_precio_venta
      (get_precio_compra + get_precio_edificar*
        cantidad_casas_hoteles*@factor_revalorizacion)
    end

    def get_propietario
      @propietario
    end

    def hipotecar(jugador)

    end

    def tramitar_alquiler(jugador)
      if tiene_propietario == true and es_este_el_propietario(jugador) == false
        jugador.paga_alquiler(get_precio_alquiler)
        @propietario.recibe(get_precio_alquiler)
      end
    end

    def propietario_encarcelado
      res = false
      if @propietario.encarcelado == true
        res = true
      end
      return res
    end

    def cantidad_casas_hoteles
      (@num_casas + @num_hoteles)
    end

    def derruir_casas(n, jugador)
      res = false
      if es_este_el_propietario(jugador) == true and @num_casas >= n
        @num_casas = n
        res = true
      end
      return res
    end

    def vender(jugador)
      res = false
      if(@hipotecado == false and es_este_el_propietario(jugador) == true)
        jugador.recibe(get_precio_venta())
        derruir_casas(@num_casas,jugador)
        derruir_hoteles(@num_hoteles,jugador)
        actualiza_propietario_por_conversion(nil)
        res = true
      end
      return res
    end


  end
end
