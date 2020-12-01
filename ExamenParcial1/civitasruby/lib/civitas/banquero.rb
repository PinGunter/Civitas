# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

#EXAMEN
module Civitas
  class Banquero
    @@instancia = self

    def initialize(billetes=nil)
      @billetes = billetes
      if billetes == nil
        @billetes  = Array.new
      end
    end

    def self.get_instancia
      @@instancia
    end

    def self.get_billetes
      @billetes
    end

    def self.aniade_billetes(nuevo)
      @billetes << nuevo
    end

    def self.to_s
      info = ""
      @billetes.each do |billete|
        info += billete.to_s
      end
      info
    end
    private :initialize
  end
end
#FIN DE EXAMEN