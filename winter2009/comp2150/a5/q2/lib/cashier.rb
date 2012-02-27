# file: lib/cashier.rb

# Models a cashier in a store simulation. 
class Cashier < Person

  attr_accessor :checkout
  attr_reader :eid, :customers_served, :total_items

  # update stat trackers on this cashier and the associated checkout
  # to accurately track customers and grocery items packed.
  def serve(customer)
    @customers_served += 1
    @checkout.customers_served += 1
    @total_items += customer.num_groceries
    @checkout.total_items += customer.num_groceries
  end
  
  def initialize(arrival_time, eid)
    super(arrival_time)
    @speed = 4
    @eid = eid
    @customers_served = 0
    @total_items = 0
  end

  # Calculate the number of time units it will take this
  # cashier to pack `items` grocery items.
  def time_for_items(items)
    (items/(@speed.to_f)).ceil
  end
  
end
