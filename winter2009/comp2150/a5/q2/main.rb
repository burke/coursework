# file: main.rb

$LOAD_PATH << File.join(File.dirname(__FILE__),"lib")

class String
  # Convert a CamelCase string (class name) into an underscorized
  # not_camel_case string.
  def underscorize
    self.gsub(/([A-Z]+)([A-Z][a-z])/,'\1_\2').
      gsub(/([a-z\d])([A-Z])/,'\1_\2').
      downcase
  end 
end

# When a new ClassName is used check ./lib for that class_name. If it exists,
# require it and return it. If not, raise NameError like would happen anyway.
def Object.const_missing(klass)
  file = klass.to_s.underscorize
  begin
    require file
    return Object.const_get(klass)
  rescue LoadError
    raise NameError, "uninitialized constant #{klass}"
  end  
end

# Kick start the simulation
if __FILE__ == $0

  SoopaStore.run("events.txt")
  
end
