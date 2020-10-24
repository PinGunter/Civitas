#encoding:utf-8
# author: Salva 
#
require_relative "diario"
require_relative "tipo_sorpresa"
require_relative "tablero"
require_relative "mazo_sorpresas"
#require_relative "jugador"

module Civitas
  # Clase Sorpresa
  # Representa las cartas sorpresa que estan dentro del Civitas::Mazo_sorpresas
  class Sorpresa
    # metodo privado para inicializar referencias vacias
    def init
      @valor = -1
      @mazo = nil
      @tablero = nil
    end
    
    #constructor de clase, tiene 4 posibles versiones
    # Sorpresa(tipo, tablero)
    # Sorpresa(tipo, tablero, valor, texto)
    # Sorpresa(tipo, valor, texto)
    # Sorpresa(tipo, mazo)
    def initialize(tipo,*resto)
      init
      @tipo = tipo
      @texto = nil   
      if resto.length == 2
        @valor = resto.at(0)
        @texto = resto.at(1)
      elsif resto.length == 3
        @tablero = resto.at(0)
        @valor = resto.at(1)
        @texto = resto.at(2)
      elsif resto.length == 1 and resto.at(0).instance_of? Mazo_sorpresas
        @mazo = resto.at(0)
      elsif resto.length == 1 and resto.at(0).instance_of? Tablero
        @tablero = resto.at(0)
      end
    end
    
    # determina si la posicion dentro de un array de jugadores es correcto
    # true si es correcto
    # falso si no es correcto
    def jugador_correcto(actual,todos)
      correcto = actual >= 0 and actual < todos.length
      return correcto
    end
    
    # metodo para informar al Civitas::Diario de que un jugador a recibido esta sorpresa
    def informe(actual,todos)
      Diario.instance.ocurre_evento("Jugador: " + todos.at(actual).nombre + " recibe sorpresa " + @nombre)
    end
    
    # metodo para gestionar que le debe pasar al jugador en funcion del tipo de sorpresa que sea
    # es un switch para seleccionar metodos privados
    def aplicar_a_jugador(actual,todos)
      case @tipo
      when Tipo_sorpresas::IR_CARCEL
        aplicar_a_jugador_ir_carcel(actual,todos)
      when Tipo_sorpresas::IR_CASILLA
        aplicar_a_jugador_ir_a_casilla(actual,todos)

      when Tipo_sorpresas::PAGAR_COBRAR
        aplicar_a_jugador_pagar_cobrar(actual,todos)

      when Tipo_sorpresas::SALIR_CARCEL
        aplicar_a_jugador_salir_carcel(actual,todos)

      when Tipo_sorpresas::POR_CASA_HOTEL
        aplicar_a_jugador_por_casa_hotel(actual,todos)

      when Tipo_sorpresas::POR_JUGADOR
        aplicar_a_jugador_por_jugador(actual,todos)

      end
    end
    
    #metodo para cuando la sorpresa manda al jugador a la carcel
    def aplicar_a_jugador_ir_carcel(actual,todos)
      if jugador_correcto(actual,todos)
        informe(actual,todos)
        jugador.encarcelar
      end
    end
    
    #metodo para cuando la sorpresa manda al jugador a una casilla
    def aplicar_a_jugador_ir_a_casilla(actual,todos)
      casilla = -1
      if jugador_correcto(actual,todos)
        informe(actual,todos)
        casilla = todos.at(actual).get_num_casilla_actual
        tirada = @tablero.calcular_tirada(casilla,@valor)
        nueva = @tablero.nueva_posicion(casilla,tirada)
        todos.at(actual).mover_a_casilla(nueva)
        @tablero.get_casilla(nueva).recibe_jugador(actual,todos)
      end
    end
    
    #metodo para cuando el jugador debe cobrar o pagar dinero
    def aplicar_a_jugador_pagar_cobrar(actual,todos)
      if jugador_correcto(actual,todos)
        informe(actual,todos)
        todos.at(actual).modificar_salvo(@valor)
      end
    end
    
    #metodo para pagar/cobrar por casas u hoteles
    def aplicar_a_jugador_por_casa_hotel(actual,todos)
      if jugador_correcto(actual,todos)
        informe(actual,todos)
        todos.at(actual).modificar_saldo(@valor*todos.at(actual).cantidad_casas_hoteles)
      end
    end
    
    #metodo para cuando toca una sorpresa en la que el resto de jugadores deben pagar al que le toca
    def aplicar_a_jugador_por_jugador(actual,todos)
      if jugador_correcto(actual,todos)
        informe(actual,todos)
        a_pagar = Sorpresa.new(Tipo_sorpresas::PAGAR_COBRAR,@valor*-1,"Paga al jugador " + todos.at(actual).nombre + ":" + @valor)
        todos.length.times do |indice|
          if indice != actual
            a_pagar.aplicar_a_jugador(indice, todos)
          end
        end
        recibe_dinero = Sorpresa.new(Tipo_sorpresas::PAGAR_COBRAR,@valor*(todos.length-1),"Recibes #{@valor} del resto de jugadores")
        recibe_dinero.aplicar_a_jugador(actual,todos)
      end
    end
    
    #metodo para obtener el salvoconducto de la carcel en caso de que ningun jugador lo tenga ya
    def aplicar_a_jugador_salir_carcel(actual,todos)
      if jugador_correcto(actual,todos)
        informe(actual,todos)
        alguien_tiene_s_carcel = false
        indice = 0
        # preguntamos al resto de jugadores si tienen la carta que les libra de la carcel
        while indice < todos.length and not alguien_tiene_s_carcel
          if indice != actual
            alguien_tiene_s_carcel = todos.at(indice).tiene_salvoconducto
          end
          indice +=1
        end
        if not alguien_tiene_s_carcel
          todos.at(actual).obtener_salvoconducto
          salir_del_mazo
        end
      end
    end
    
    # metodo para sacar la carta actual(librarte de la carcel) del mazo
    def salir_del_mazo
      if @tipo == Tipo_sopresas::SALIR_CARCEL
        @mazo.inhabilitar_carta_especial(self)
      end
    end
    
    # metodo para devolver la carta de salvoconducto al mazo
    def usada
      if @tipo == Tipo_sorpresas::SALIR_CARCEL
        @mazo.habilitar_carta_especial(self)
      end
    end
       
    def to_s
      @texto
    end
    
    private :init, :informe, :aplicar_a_jugador_ir_a_casilla, :aplicar_a_jugador_ir_carcel, :aplicar_a_jugador_pagar_cobrar, :aplicar_a_jugador_por_casa_hotel, :aplicar_a_jugador_por_jugador, :aplicar_a_jugador_salir_carcel
  end
  
  
end
