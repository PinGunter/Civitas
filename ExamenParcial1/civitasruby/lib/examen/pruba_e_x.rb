# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
# EXAMEN
require_relative "../civitas/civitas_juego"
module Civitas
  jugador1 = Jugador.new_nombre("Uno")
  jugador2 = Jugador.new_nombre("DOS")
  juego = Civitas_juego.new([jugador1,jugador2])
  puts Civitas::Banquero.get_instancia.get_billetes
end

#FIN DE EXAMEN
