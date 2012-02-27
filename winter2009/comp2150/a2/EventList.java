// CLASS: EventList
//
// REMARKS: Maintains a sorted linked list of upcoming events in an
//   event-based simulation.
//
// Input: None
//
// Output: None
//
//-----------------------------------------
public class EventList {

  private Node first;
  
  //------------------------------------------------------
  // EventList
  //
  // PURPOSE:    Constructor. Does nothing.
  //------------------------------------------------------
  public EventList() {
  }

  //------------------------------------------------------
  // setFirst
  //
  // PURPOSE:    defines the next event that will occur, ie. the first node.
  // PARAMETERS:
  //     node: the node to set the first element to.
  //------------------------------------------------------
  private void setFirst(Node node) {
    this.first = node;
  }

  //------------------------------------------------------
  // getFirstEvent
  //
  // PURPOSE:    returns the first Event in the list, ie. the next one to occur.
  // EXTERNAL REFERENCES:  requires Event
  //------------------------------------------------------
  public Event getFirstEvent() {
    return this.first.getEvent();
  }
  
  //------------------------------------------------------
  // addEvent
  //
  // PURPOSE:
  //     inserts an Event into the (sorted) list, to occur at the appropriate time.
  // PARAMETERS:  event: the event to insert into the list
  // EXTERNAL REFERENCES:  require Event, Customer, StartCheckoutEvent
  //------------------------------------------------------
  public void addEvent(Event event) {
    int time = event.getTime();
    Node curr, prev;

    Customer cust;
    StartCheckoutEvent evt;

    int id = event.getActor().getId();
    if (this.first == null) {
      this.setFirst(new Node(event,null));
    } else {
      // If this event occurs prior to the first element in the list...
      if (time < first.getTime() || (time == first.getTime() && id < first.getId())) {
        first = new Node(event, first);
      } else {
        prev = first;
        curr = first.getNext();

        // iterate through the list.
        // If we reach a time that is greater than the `time`,
        // Insert the event at this position.
        int i=0;
        while (prev != null) {
          if (curr == null) {
            this.insertAfter(event,prev);
            break;
          }
          if (time < curr.getTime()) {
            // We've found the insertion point.
            this.insertAfter(event,prev);
            break;
          } else if (time == curr.getTime()) {
            // If two events occur at the same time, sort by the ID of the Person.
            if (id < curr.getId()) {
              this.insertAfter(event,prev);
              break;
            }
          } else if (curr.getNext() == null) {
            // End of list. This event happens last.
            this.insertAfter(event,curr);
            break;
          } 
          // Increment position in list.
          prev = curr;
          curr = curr.getNext();
          ++i;
        }
      }
    }
  }


  //------------------------------------------------------
  // size
  //
  // PURPOSE:    returns the current size of the list of events: that is,
  //             the number of events currently waiting to be processed.
  //------------------------------------------------------
  public int size() {
    Node curr;
    int size=0;
    for (curr=first; curr!=null; curr=curr.getNext()) {
      ++size;
    }
    return size;
  }
  
  //------------------------------------------------------
  // insertAfter
  //
  // PURPOSE:
  //     Given a node in the list and a new event, create a new Node encapsulating
  //     the event and insert it between the given node and its successor.
  // PARAMETERS:
  //     event: the new event to be inserted
  //     prev: the node to insert after
  // EXTERNAL REFERENCES: requires Event
  //------------------------------------------------------
  private void insertAfter(Event event, Node prev) {
    Node newNode = new Node(event, prev.getNext());
    prev.setNext(newNode);
  }
  
  //------------------------------------------------------
  // popEvent
  //
  // PURPOSE:    remove an Event from the front of list and return it
  // EXTERNAL REFERENCES:  require Event
  //------------------------------------------------------
  public Event popEvent() {
    Event ret;
    if (!this.hasMoreEvents()) {
      ret = null;
    } else {
      ret   = first.getEvent();
      first = first.getNext();
    }
    return ret;
  }

  //------------------------------------------------------
  // hasMoreEvents
  //
  // PURPOSE:    returns a boolean indicating whether or not the list is empty
  //------------------------------------------------------
  public boolean hasMoreEvents() {
    return (first != null);
  }
  

  // CLASS: Node
  //
  // REMARKS: Encapsulates a single Event in a linked list of events
  //
  // Input: None
  //
  // Output: None
  //
  //-----------------------------------------
  private class Node {
    private Event event;
    private Node next;
    //------------------------------------------------------
    // Node
    //
    // PURPOSE:    Constructor. Build a new Node.
    // PARAMETERS:
    //     event: The event to store.
    //     next: the following node, or null if this is the last node in the list.
    // EXTERNAL REFERENCES:  requires Event
    //------------------------------------------------------
    public Node(Event event, Node next) {
      this.event = event;
      this.next = next;
    }
    //------------------------------------------------------
    // getNext
    //
    // PURPOSE:    returns the next node in the list.
    //------------------------------------------------------
    public Node getNext() {
      return this.next;
    }
    //------------------------------------------------------
    // setNext
    //
    // PURPOSE:    sets this node's successor
    // PARAMETERS:
    //     next: the Node to point to.
    //------------------------------------------------------
    public void setNext(Node next) {
      this.next = next;
    }
    //------------------------------------------------------
    // getEvent
    //
    // PURPOSE:    return the event encapsulated by this Node
    // EXTERNAL REFERENCES:  requires Event
    //------------------------------------------------------
    public Event getEvent() {
      return this.event;
    }
    
    //------------------------------------------------------
    // getId
    //
    // PURPOSE:    returns the ID of the Person involved in this event
    // EXTERNAL REFERENCES:  requires Person, Event
    //------------------------------------------------------
    public int getId() {
      return this.event.getActor().getId();
    }
    
    //------------------------------------------------------
    // getTime
    //
    // PURPOSE:    returns the time at which this event will occur.
    // EXTERNAL REFERENCES:  requires Event
    //------------------------------------------------------
    public int getTime() {
      return this.event.getTime();
    }
    
  }
}