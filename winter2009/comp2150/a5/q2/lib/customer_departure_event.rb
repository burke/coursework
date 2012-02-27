# file: lib/customer_departure_event.rb

# Models the departure of a customer from the store simulation.
class CustomerDepartureEvent < DepartureEvent

  def initialize(time, customer)
    super(time, customer)
    @customer = customer
  end

  # Event action. Remove customer from checkout, and tell checkout to advance next
  # customer in line. Record departure time for stats.
  def run
    SoopaStore.checkout.occupied = false
    SoopaStore.checkout.start_next_checkout
    @customer.departure_time = SoopaStore.current_time
    puts "Time #{SoopaStore.current_time}  : Customer  #{@customer.customer_id} "\
    "departs (spent #{@customer.time_spent_waiting} units waiting)"
  end
  
end
