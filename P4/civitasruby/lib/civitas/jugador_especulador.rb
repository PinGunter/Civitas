#encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module Civitas

  class Jugador_especulador < Jugador
    
    @@max_casas = @@factor_especulador * @@casas_max
    @@max_hoteles = @@factor_especulador * @@hoteles_max
    @@factor_pago_impuesto = 0.5
    
    def initialize(otro, fianza)
      Jugador.copia(otro)
      @fianza = fianza
      actualizar_propiedades(otro)
    end
    
    def actualizar_propiedades(original)
      original.get_propiedades().each do |propiedad|
        propiedad.actualiza_propietario_por_conversion(self)
      end
    end
    
    def to_s
      info = super
      info += "\n ---  JUGADOR ESPECULADOR: PAGA LA MITAD DE IMPUESTOS Y PUEDE PAGAR FIANZA EN LA CÁRCEL --- \n";
      info += "La fianza que paga es de " + @fianza;
      return info;
    end
    
    def get_hoteles_max
      return @@max_hoteles
    end
    
    def get_casas_max
      return @@max_casas
    end
    
    def paga_impuesto(cantidad)
      paga(cantidad*@@factor_pago_impuesto)
    end
    
    def debe_ser_encarcelado
      res = true
      if super
        if puedo_gastar(@fianza)
          paga(@fianza)
          res = false
        end
      end
      return res
    end
    
    
    
    
    
    
    
    
    
  end
end
