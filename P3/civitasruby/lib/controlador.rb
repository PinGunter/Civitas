# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

#encoding:utf-8

require_relative 'civitas_juego'
require_relative 'operaciones_juego'
require_relative "operacion_inmobiliaria"

module Juego_texto
  class Controlador
    def initialize(c, v)
      @juego = c
      @vista = v
    end
    
    def juega
      @vista.set_civitas_juego(@juego)
      while(@juego.final_de_juego)
        @vista.actualizar_vista
        @vista.pausa
        
        op = @juego.siguiente_paso
        @vista.mostrar_siguiente_operacion(op)
        
        if(op != operaciones_juego::PASAR_TURNO)
          @vista.mostrar_eventos
        end
        
        if(@juego.final_del_juego)
          # tocar cossas del ranking
        
        else
          case op
          when operaciones_juego::COMPRAR
            if(@vista.comprar == respuestas_::SI)
              @juego.comprar 
            end
            @juego.siguiente_paso_completado(op)
          when operaciones_juego::GESTIONAR
            @vista.gestionar
            #
            #
            #
          when operaciones_juego::SALIR_CARCEL
            if(@vista.salir_carcel == salidas_carcel::PAGANDO)
              @juego.salir_carcel_pagando
            else
              @juego.salir_carcel_tirando
            end
            @juego.siguiente_paso_completado(op)
          end
        end
      end
    end
    
    
    
    
  end
end
