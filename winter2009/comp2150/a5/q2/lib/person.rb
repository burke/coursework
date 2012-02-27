# file: lib/person.rb

# Models a person in a simluation
class Person
  include Abstract
  
  attr_reader :arrival_time
  attr_accessor :departure_time
  
  def initialize(arrival_time)
    @arrival_time = arrival_time
  end

  # Total time person spent in the store.
  def time_in_store
    @departure_time - @arrival_time
  end
  
end
