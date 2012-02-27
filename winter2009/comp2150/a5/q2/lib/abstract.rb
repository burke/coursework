# abstract.rb
# Copyright 2009 Burke Libbey <burke@burkelibbey.org> under MIT license
#
# http://gist.github.com/91561
#
# Implements slightly Java-esque abstract classes in ruby,
#   using painfully simple syntax.
# 
# Usage:
#  
# class Foo
#   include Abstract
#   abstract_methods :baz
# end
#
# class Bar < Foo
#   def baz
#   end
# end
#
# Exceptions will be raised if Foo is instantiated, or a subclass of
#   foo is instantiated without all abstract_methods.
#

module Abstract
  def self.included(klass)
    klass.class_eval("@@abstract_class = #{klass}")
    klass.extend ClassMethods
  end

  module ClassMethods

    # Define a list of abstract methods that all concrete descendants of this class must
    # implement. Use like:
    # class Foo
    #   include Abstract
    #   abstract_methods :bar, :baz
    # end
    def abstract_methods(methods)
      class_eval("(@@abstract_methods||=[]) << #{methods.inspect}")
    end

    # Override the constructors for this class and all descendants.
    # Raise an exception if this class is instantiated,
    # Raise an exception if a descendant is instantiated, but does not implement all abstract_methods.
    def new(*args)
      abstract_class   = class_variable_get("@@abstract_class")
      abstract_methods = class_variable_get("@@abstract_methods") rescue []
      if abstract_class == self
        raise TypeError, "can't instantiate abstract class"
      elsif abstract_methods.map{|m| self.method_defined? m}.index(false)
        raise TypeError, "abstract methods for #{abstract_class} not implemented in class #{self}"
      else
        super
      end
    end

  end

end
