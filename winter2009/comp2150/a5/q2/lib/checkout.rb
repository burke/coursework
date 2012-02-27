# file: lib/checkout.rb

# Models a checkout in a simulation of a store. A cashier uses a checkout to
# sell items to customers. A checkout has a line of customers waiting to check out.
class Checkout

  attr_accessor :cashier, :occupied, :customers_served, :total_items
  
  def initialize
    @line = LinkedList.new :sorted => false, :double => true, :behaviour => :queue
    @customers_served = 0
    @total_items = 0
  end

  # If there's somebody in line, fire a CustomerDepartureEvent for them (ie. check out)
  # otherwise, fire a CashierDepartureEvent.
  def start_next_checkout
    unless @line.empty?
      customer = @line.pop
      self.occupied = true
      customer.time_to_checkout = @cashier.time_for_items(customer.num_groceries)+1
      event = CustomerDepartureEvent.new(SoopaStore.current_time+customer.time_to_checkout, customer)
      @cashier.serve(customer)
    else
      event = CashierDepartureEvent.new(SoopaStore.current_time+1, cashier)
    end
    SoopaStore.events.add(event)
  end

  # Customer approaches checkout. They either get served immediately or stand in line.
  def serve_customer(customer)
    if @occupied
      @line.add(customer)
    else
      @occupied = true
      customer.time_to_checkout = @cashier.time_for_items(customer.num_groceries)
      event = CustomerDepartureEvent.new(SoopaStore.current_time+customer.time_to_checkout, customer)
      SoopaStore.events.add(event)
      @cashier.serve(customer)
    end
  end
    
end
