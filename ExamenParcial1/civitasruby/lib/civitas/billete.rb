# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
# EXAMEN
module Civitas
  class Billete
    def initialize(valor, tipo)
      @valor = valor
      @tipo = tipo
    end

    def get_valor
      @valor
    end

    def to_s
      info = "Valor: #{@valor} + Tipo: #{@tipo}\n"
    end
  end
end
# FIN DE EXAMEN
