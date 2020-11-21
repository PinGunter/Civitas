#encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.


require_relative "../civitas/civitas_juego"
require_relative "../civitas/operaciones_juego"
require_relative "../civitas/operacion_inmobiliaria"


module Civitas
  class Controlador
    def initialize(c, v)
      @juego = c
      @vista = v
    end

    def juega
      @vista.set_civitas_juego(@juego)
      while(!@juego.final_de_juego)
        @vista.actualizar_vista
        @vista.pausa

        op = @juego.siguiente_paso
        @vista.mostrar_siguiente_operacion(op)

        if(op != operaciones_juego::PASAR_TURNO)
          @vista.mostrar_eventos
        end

        if(!@juego.final_del_juego)

          case op
          when operaciones_juego::COMPRAR
            if(@vista.comprar == respuestas_::SI)
              @juego.comprar
            end
            @juego.siguiente_paso_completado(op)

          when Civitas::OperacionesJuego::GESTIONAR
            @vista.gestionar
            gest = gestiones_inmobiliarias::Lista_gestiones_inmobiliarias[@vista.get_gestion]
            ip = @vista.get_propiedad

            operacion_inm = operacion_inmobiliaria.new(ip,gest)

            case operacion_inm.gestion
            when Civitas::gestiones_inmobiliarias::VENDER    #me lo asocia con el método vender de juego en vez de con el enum
              @juego.vender(ip)
            when Civitas::gestiones_inmobiliarias::HIPOTECAR
              @juego.hipotecar(ip)
            when Civitas::gestiones_inmobiliarias::CANCELAR_HIPOTECA
              @juego.cancelar_hipoteca(ip)
            when Civitas::gestiones_inmobiliarias::CONSTRUIR_CASA
              @juego.construir_casa(ip)
            when Civitas::gestiones_inmobiliarias::CONSTRUIR_HOTEL
              @juego.construir_hotel(ip)
            when Civitas::gestiones_inmobiliarias::TERMINAR
              @juego.siguiente_paso_completado(operacion)
            end

          when operaciones_juego::SALIR_CARCEL
            if(@vista.salir_carcel == salidas_carcel::PAGANDO)
              @juego.salir_carcel_pagando
            else
              @juego.salir_carcel_tirando
            end
            @juego.siguiente_paso_completado(op)
          end

        else
          @juego.mostrar_ranking
        end
      end
    end




  end
end
