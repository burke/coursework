class Tree
  def initialize*d;@d,=d;end
  def to_s;@l||@r? "<#{@d},<#{@l}>,<#{@r}>>":@d;end
  def total;(@d.is_a?(Numeric)?@d:0)+(@l?@l.total: 0)+(@r?@r.total: 0);end
  def insert d
    alias g instance_variable_get
    p=lambda{|s,o|d.to_s.send(o,@d.to_s)&&
      (g(s).nil??instance_variable_set(s,Tree.new(d)):g(s).insert(d))}
    @d?p[:@l,:<]||p[:@r,:>]:@d=d
  end
end

# (Just kidding; don't mark this part.)
