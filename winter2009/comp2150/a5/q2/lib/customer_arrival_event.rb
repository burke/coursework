# file: lib/customer_arrival_event.rb

# Models the arrival of a customer into a store simulation
class CustomerArrivalEvent < ArrivalEvent

  def initialize(time, num_groceries)
    super(time)
    @customer = Customer.new(time, num_groceries)
  end

  # Event Action. Just put them in line at the checkout.
  def run
    SoopaStore.customers.add(@customer)
    SoopaStore.checkout.serve_customer(@customer)
    puts "Time #{SoopaStore.current_time}  : Customer  #{@customer.customer_id} "\
      "arrives (#{@customer.num_groceries} items)"
  end
  
end
