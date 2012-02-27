class Tree
  include Comparable
  
  attr_reader :data

  # Create a new node with one initial data element
  def initialize(data=nil)
    @data = data
  end

  # Spaceship operator. Comparable uses this to generate
  #   <, <=, ==, =>, >, and between?
  def <=>(other)
    @data.to_s <=> other.data.to_s
  end

  # Insert an object into the subtree including and under this Node.
  # First choose whether to insert into the left or right subtree,
  # then either create a new node or insert into the existing node at
  # the head of that subtree.
  def insert(data)
    if !@data
      @data = data
    else
      node = (data.to_s < @data.to_s) ? :@left : :@right
      create_or_insert_node(node, data)
    end
  end

  # Sum all the numerical values in this tree. If this data object is a
  # descendant of Numeric, add @data to the sum, then descend into both subtrees.
  def total
    sum = 0
    sum += @data if (@data.is_a? Numeric)
    sum += [@left, @right].map{|e| e.total rescue 0}.inject(0){|a,v|a+v}
    sum
  end

  # Convert this subtree to a String.
  # Format is: <tt>\<data,left_subtree,right_subtree></tt>.
  # Non-existant Nodes are printed as <tt>\<></tt>.
  def to_s
    subtree = lambda do |tree|
      tree.to_s.empty? ? "<>" : tree
    end
    "<#{@data},#{subtree[@left]},#{subtree[@right]}>"
  end

  private ############################################################
  # Given a variable-as-symbol, insert data into the subtree incl. and under this node.
  def create_or_insert_node(nodename, data)
    if instance_variable_get(nodename).nil?
      instance_variable_set(nodename, Tree.new(data))
    else
      instance_variable_get(nodename).insert(data)
    end
  end
  
end
