# file: lib/cashier_departure_event.rb

#Models the departure of a cashier from a store simulation
class CashierDepartureEvent < DepartureEvent

  def initialize(time, cashier)
    super(time, cashier)
    @cashier = cashier
  end

  # Event action. Remove self from the checkout, and record the time.
  def run
    SoopaStore.checkout.cashier = nil
    @cashier.checkout           = nil
    @cashier.departure_time = SoopaStore.current_time
    puts "Time #{SoopaStore.current_time}  : Cashier #{@cashier.eid} "\
    "leaves checkout (worked #{@cashier.time_in_store} time units)"
  end
  
end
