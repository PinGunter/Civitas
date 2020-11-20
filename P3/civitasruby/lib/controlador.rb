# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

#encoding:utf-8

require_relative 'civitas_juego'
require_relative ''

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
        @vista.siguiente_operacion()
      end
    end
    
    
    
    
  end
end
