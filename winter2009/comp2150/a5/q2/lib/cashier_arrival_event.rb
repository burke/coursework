# file: lib/cashier_arrival_event.rb

# Models the arrival of a cashier into a simulation of a supermarket.
class CashierArrivalEvent < ArrivalEvent

  def initialize(time, eid)
    super(time)
    @cashier = Cashier.new(time, eid)
  end

  # Event action. Set up camp at the checkout.
  def run
    SoopaStore.checkout.cashier = @cashier
    @cashier.checkout = SoopaStore.checkout
    SoopaStore.cashiers.add @cashier
    puts "Time #{SoopaStore.current_time}  : Cashier #{@cashier.eid} arrives & starts work"
  end
  
end
