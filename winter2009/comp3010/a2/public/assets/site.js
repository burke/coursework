// Create a namespace for our application.
CK = {};

// The cloudkit interface
CK.db = $.cloudkit;

// The current user.
CK.username="";

// Initalize the client-side datastore.
CK.db.boot({
  success: function() {
    CK.Events = CK.db.collection('events');
    CK.Users = CK.db.collection('users');
    CK.Events.orderBy("[date,title,description,user]");
    CK.populate_events_list();
    CK.populate_users_list();
  }
});

CK.list_item_for_event = function(event) {
  return "<a href='#' class='event' id='"+event.___cloudkit_local_id___+"'><li>"+event.title+"</li></a>";
};

// For each Event in the datastore, create an item in the Event List.
CK.populate_events_list = function() {
  $('#events').html("");
  $.each(CK.Events.find({"user":CK.username}), function(i,ev) {
    var event = CK.Events.first(ev);
    $('#events').append(CK.list_item_for_event(event));
  });
};

CK.option_for_user = function(user) {
  return "<option value='"+user.name+"'>"+user.name+"</option>";
};

CK.populate_users_list = function() {
  $('#users-select').html("");
  $('#users-select').append("<option value=''>--Select User--</option>");
  $.each(CK.Users.find(), function(i) {
    var user = CK.Users.first(i);
    $('#users-select').append(CK.option_for_user(user));
  });
};

CK.format_date = function(date) {
  return $.datepicker.formatDate("MM d, yy", $.datepicker.parseDate("@",date));
};

// Given a CloudKit unique ID referring to an Event, return a formatted
//   HTML string to draw the Main Pane content for that Event.
CK.main_pane_for_event_ckid = function(ckid) {
  var event = CK.Events.get(CK.Events.find({___cloudkit_local_id___:ckid})[0])[0];
  var ret = "<div id='main_pane' class='"+ckid+"'>";
  ret    +=   "<div class='title'>"+event.title+"</div>";
  ret    +=   "<div class='date'>"+CK.format_date(event.date)+"</div>";
  ret    +=   "<div class='description'>"+event.description+"</div>";
  ret    +=   "<button class='delete'>Delete Event</button>";
  ret    += "</div>";
  return ret;
};

// Run when the DOM is ready. Wire up UI events.
$(document).ready(function() {

  // Event List elements should cause the Main Pane to display more information
  //   about that event.
  $('.event').live('click', function() {
    $('#main_pane-container').show();
    $('#new-event').hide();
    $('.event').removeClass('active');
    $(this).addClass('active');
    $('#main_pane-container').html(CK.main_pane_for_event_ckid($(this).attr('id')));
  });

  // Activate the JavaScript DatePicker
  $('#create-date').datepicker({dateFormat: "MM d, yy"});

  // When the #new-event-link is clicked, the form to create a new event should be shown.
  $('#new-event-link').click(function(){
    $('#main_pane-container').hide();
    $('#new-event').show();
    $('.event').removeClass('active');
  });

  $('#users-select').change(function() {
    CK.username = $('#users-select').val();
    CK.populate_events_list();
  });

  // Hijack form submission to create new event
  $('form#create-event').submit(function() {
    if (CK.username == "") {
      alert("Pick a user first!");
      return false;
    }
    var title = $('#create-title').val();
    var desc  = $('#create-description').val();
    var date  = $('#create-date').val();
    date = $.datepicker.formatDate("@",$.datepicker.parseDate("MM d, yy", date));
      CK.Events.insert({title:title,description:desc,date:date,user:CK.username}, {
        success: function() {
          CK.populate_events_list();
        }
      }
    );
    $('#main_pane-container').show();
    $('#new-event').hide();
    $('#main_pane-container').html("<h2>Event Created!</h2>");
    return false;
  });

  // Clicking the button to delete an event should delete it.
  $('button.delete').live('click',function() {
    var ckid = $(this).parent().attr('class');
    CK.Events.remove({___cloudkit_local_id___:ckid});
    $('#'+ckid).remove();
    $('#main_pane-container').html("");
    return false;
  });
});
