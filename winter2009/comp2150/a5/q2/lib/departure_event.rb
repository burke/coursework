# file: lib/departure_event.rb

# Models the departure of a person from the store simulation. Abstract.
class DepartureEvent < Event
  include Abstract

  def initialize(time, person)
    super(time)
    @person = person
  end

end
