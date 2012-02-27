# file: lib/customer.rb

# Models a customer in a store simulation. Customers have some number of items,
# and want to check out.
class Customer < Person

  attr_reader :num_groceries, :customer_id
  attr_accessor :time_to_checkout
  
  @@next_customer_id = 45 # This is incremented each time a customer is added.
  
  def initialize(time, num_groceries)
    super(time)
    @customer_id = @@next_customer_id
    @@next_customer_id += 1
    @num_groceries = num_groceries
  end

  # The total time this customer has spent waiting in line.
  def time_spent_waiting
    time_in_store - time_to_checkout
  end
  
end
