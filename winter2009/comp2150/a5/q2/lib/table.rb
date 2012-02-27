# file: lib/table.rb

# Tricky little class to do the nice output formatting shown in the sample output.
class Table

  def initialize
    @widths = [] # Column widths
    @data = []   # coulmn data.
    yield self if block_given? # "make me a DSL."
  end

  #### DSL methods ######

  # Add headers to the table. We'll calculate the initial column
  # widths based on the longest word in each header.
  # This makes some stupid assumptions, but works fine in context.
  def headers(*headers)
    @headers = headers
    headers.each do |header|
      @widths << ( header.split(/\s+/).map{|e|e.size}.max + 2)
    end
  end

  # Add a row of data to the table. If some outlandishly large value is encountered,
  # readjust column width to fit it.
  def data(*columns)
    @data << columns
    columns.each_with_index do |column,i|
      @widths[i] = [column.to_s.size+2, @widths[i]].max
    end
  end

  #### END DSL methods ######

  # Print the table. First headers, then a border, then each data row.
  def to_s
    ret = ""
    ret << format_headers
    ret << divider
    @data.each {|row| ret << format_row(row)}
    ret
  end

  private
  # Here we allot two rows for headers, breaking on word boundaries.
  # This is a fragile system, but it works for the assignment.
  def format_headers
    row_1 = []
    row_2 = []

    @headers.each_with_index do |header,i|
      if header.size > @widths[i]
        row_1[i] = header.split[0]
        row_2[i] = header.split[1]
      else
        row_1[i] = header
        row_2[i] = ""
      end
    end

    ret = ""
    ret << @widths.map{|el|"%-#{el}s"}.join('') % row_1
    ret << "\n"
    ret << @widths.map{|el|"%-#{el}s"}.join('') % row_2
    ret << "\n"
    ret 
  end

  # Align each data item to column boundaries. Note that String % Array
  # is the same as printf(string, arr[0],arr[1],...arr[n]); in C.
  def format_row(row)
    @widths.map{|el|"%-#{el}d"}.join('') % row + "\n"
  end

  # Print a number of "-"s equal to the sum of column widths. A border.
  def divider
    "-" * @widths.inject{|a,v|a+v} + "\n"
  end
  
end
