#encoding :utf-8
require_relative "diario"
require_relative "../juego_texto/vista_textual"
require_relative "../juego_texto/controlador"
require_relative "civitas_juego"

module Civitas
  jugadores = ["esther_colero", "dj_panzetita in the remix"]
  vista = Vista_textual.new
  juego = Civitas_juego.new(jugadores)
  controlador = Controlador.new(juego,vista)
  controlador.juega

end
