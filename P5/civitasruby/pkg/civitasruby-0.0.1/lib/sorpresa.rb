# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative "diario"
module Civitas
  class Sorpresa
    attr_reader :nombre
    def initialize(n)
      @nombre = n
    end
  end
end
