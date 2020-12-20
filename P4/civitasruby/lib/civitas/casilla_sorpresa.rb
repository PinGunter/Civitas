# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module Civitas

  class Casilla_sorpresa < Casilla
    
    def initialize(mazo, nombre) # deberiamos pasarle nombre como parametro?
      @mazo = mazo
      @sorpesa = mazo.siguiente
      super(nombre) 
    end
    
    @override
    def to_s
      "#{super}   La proxima sorpresa es: #{@sorpresa.to_s}"
    end
    
    def recibe_jugador(actual, todos)
      if(jugador_correcto(actual, todos))
        @sorpresa = @mazo.siguiente
        super
        @sorpresa.aplicar_a_jugador(actual, todos)
      end
    end
    
  end

end
