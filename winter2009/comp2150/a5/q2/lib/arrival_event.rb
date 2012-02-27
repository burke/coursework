# file: lib/arrival_event.rb

# Models an arrival in a simulation. Abstract.
class ArrivalEvent < Event
  include Abstract
  
  def initialize(time)
    super(time)
  end
  
end
