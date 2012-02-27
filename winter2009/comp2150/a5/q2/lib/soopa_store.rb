# file lib/soopa_store.rb

# Run a simulation of a supermarket.
class SoopaStore

  def initialize
    raise TypeError "don't instantiate this class."
  end

  # ActiveSupport has a method `cattr_reader`. I wish I could use it here.
  def self.checkout;     @@checkout;     end
  def self.events;       @@events;       end
  def self.customers;    @@customers;    end
  def self.cashiers;     @@cashiers;     end
  def self.current_time; @@current_time; end

  # Start the simulation Read events from the specified file.
  # Read one at a time, processing events until an Arrival is processed.
  def self.run(infile)

    @@checkout  = Checkout.new
    @@events    = LinkedList.new :sorted => true

    @@customers = LinkedList.new :sorted => false
    @@cashiers  = LinkedList.new :sorted => false
      
    File.open(infile,'r') do |file|
      until file.eof?
        line = file.readline.split /\s+/
        event = case line[1]
        when "C" then CustomerArrivalEvent.new( line[0].to_i, line[2].to_i )
        when "E" then CashierArrivalEvent.new(  line[0].to_i, line[2].to_i )
        end
        @@events.add event unless event.nil?
        process_events_until ArrivalEvent
      end
      process_remaining_events
    end

    print_stats

    puts "\nEnd of Processing"
  end

  private
  # Pull events from the event list until we find one that's an instance of Class klass.
  def self.process_events_until(klass)
    begin
      event = @@events.pop
      @@current_time = event.time
      event.run
    end until event.is_a?(klass)
  end

  # Process all remaining events in the event list.
  def self.process_remaining_events
    until @@events.empty?
      event = @@events.pop
      @@current_time = event.time
      event.run
    end
  end

  # Print a nice formatted output of the results of the simulation.
  def self.print_stats
    puts "...All events complete. Final Summary:\n"
    cashiers = Table.new do |table|
      table.headers("ID Number", "Start Time", "Time Worked", "Customers Served", "Items Packed")
      @@cashiers.each do |c|
        table.data(c.eid,c.arrival_time,c.time_in_store,c.customers_served,c.total_items)
      end
    end
    
    customers = Table.new do |table|
      table.headers("ID Number", "Start Time", "Grocery Items", "Waiting Time")
      @@customers.each do |c|
        table.data(c.customer_id,c.arrival_time,c.num_groceries,c.time_spent_waiting)
      end
    end

    checkouts = Table.new do |table|
      table.headers("Time Staffed", "Total Customers", "Total Items")
      table.data(@@cashiers.map{|c|c.time_in_store}.inject{|a,v|a+v},
                 @@checkout.customers_served,@@checkout.total_items)
    end    

    average_wait_time = @@customers.map{|c|c.time_spent_waiting}.inject{|a,v|a+v} /
      (@@customers.count.to_f)
    
    puts "\nCashier Information:\n\n",
    cashiers,
    "\nCustomer Information:\n\n",
    customers,
    "\nCheckout Information:\n\n",
    checkouts,
    "\nCustomers processed in total: #{@@customers.count}",
    "Average waiting time per customer :#{average_wait_time}"
    
  end
  
end
