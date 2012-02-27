class Tree;alias g instance_variable_get;def initialize*d;@d,=d;end;def insert d;@d?p(:@l,:<,d)||p(:@r,:>,d):@d=d;end;end
class Tree;def p(s,o,d);d.to_s.send(o,@d.to_s)&&(g(s).nil??instance_variable_set(s,Tree.new(d)):g(s).insert(d));end;end
class Tree;def to_s;@l||@r? "<#{@d},<#{@l}>,<#{@r}>>":@d;end;def total;(@d.is_a?(Numeric)?@d:0)+(@l?@l.total: 0)+(@r?@r.total: 0);end;end
