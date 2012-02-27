# file: lib/event.rb

# Models an event in the store simulation
class Event
  include Abstract
  include Comparable

  abstract_methods :run
  attr_reader :time

  def initialize(time)
    @time = time
  end

  # Comparable uses this to generate <, <=, ==, =>, >, etc.
  def <=>(other)
    time <=> other.time
  end
  
end
