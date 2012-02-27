# file: lib/linked_list.rb

# A fairly general Linked List class.
# Can implement stacks, queues, and more.
# Singly linked lists can be sorted (LinkedList.new :sorted=>true)
class LinkedList
  include Enumerable
  
  # Parse options and set up the list.
  #   :sorted => should this list be maintained in sorted order?
  #   :double => Use previous pointer as well as next, and maintain a tail?
  #   :behaviour => :stack or :queue, ie. FIFO or LIFO
  def initialize(options)
    @sorted = options.delete(:sorted) || false
    @double = options.delete(:double) || false

    @behaviour = options.delete(:behaviour)
    @behaviour = :stack unless [:stack, :queue].include?(@behaviour)

    @head = nil
    @tail = nil
  end

  # Used by Enumerable to generate one metric pantload of methods.
  # I don't think I've used any of them, but they're nice to have.
  def each
    return if @head.nil?
    curr = @head
    until curr.nil?
      yield curr.data
      curr = curr.nxt
    end
  end

  # This was getting a little unwieldy all as one method, so here we just
  # figure out what method to _really_ run.
  def add(item)
    if @double
      @sorted ? double_sorted_add(item) : double_unsorted_add(item)
    else
      @sorted ? sorted_add(item) : unsorted_add(item)
    end
  end

  # These methods could be private, but I've left them public just in case
  # the hypothetical user wants to be tricky and call the wrong insert method on purpose.
  
  # Insert an item into an unsorted singly-linked list
  def unsorted_add(item)
    @head = Node.new(item,@head)
  end

  # insert an item into a sorted singly-linked list
  def sorted_add(item)
    if @head.nil? or (item < @head.data)
      @head = Node.new(item, @head)
    else 
      curr = @head
      until (curr.nxt.nil? or (item < curr.nxt.data)) do
        curr = curr.nxt
      end
      curr.nxt = Node.new(item, curr.nxt)
    end
  end

  # insert an item into an unsorted doubly-linked list
  def double_unsorted_add(item)
    if (@head == nil) and (@tail == nil)
      @head = @tail = Node.new(item,nil,nil)
    elsif @behaviour == :stack
      @head = Node.new(item, @head, nil)
      @head.nxt.prv = @head if @head.nxt
    elsif @behaviour == :queue
      @tail = Node.new(item, nil, @tail)
      @tail.prv.nxt = @tail if @tail.prv
    end
  end

  # this was going to be a pain to write, and isn't needed for the assignment, so...
  def double_sorted_add(item)
    raise NotImplementedError, "Double Sorted Linked Lists are not supported"
  end

  # Return the @head of the list, and remove it.
  def pop
    if @head == @tail
      value = @head.data
      @head,@tail = nil,nil
      return value
    else
      value = @head.data
      @head = @head.nxt
      @head.prv = nil if @head
      return value
    end
  end

  # Are we out of elements?
  def empty?
    !@head
  end

end
