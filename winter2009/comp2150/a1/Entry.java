// CLASS: Entry
//
// REMARKS: Stores a key and object to use as node data storage
//
// Input: key, data.
//
// Output: None.
//
//-----------------------------------------
class Entry
{
  private int key;
  private Object data;

  public Entry(int key, Object data)
  {
    this.key = key;
    this.data = data;
  }

  public int getKey()
  {
    return this.key;
  }

  public Object getData()
  {
    return this.data;
  }
}
