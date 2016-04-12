var SocketIO = require("nativescript-socketio");
var viewModule = require("ui/core/view");
var frameModule = require('ui/frame');
var Observable = require("data/observable").Observable;
var ObservableArray = require("data/observable-array").ObservableArray;
var dialogsModule = require("ui/dialogs");
var application = require("application");
var view;
var socket;
var name;
var id;
var home;

var chatsList = {};

var list = new ObservableArray([]);
var friends = [];

var data = new Observable({
  friendsList: list
});

exports.loaded = function(v){
    console.log("Home View");

    view = v.object;

    socket = view.navigationContext.socket;
    name = view.navigationContext.name;
    id = view.navigationContext.id;

    home = true;

    socket.on("update user list", function(msg){
      empty();
      load(msg);
    });

    view.bindingContext = data;

    socket.on("deliver message", function(msg){
      console.log("home--from: "+msg.name+" message: "+msg.message);
      chatsList[msg.id].push({name: msg.name, text: msg.message});
      console.log("chatList: "+chatsList[msg.id]);
    });

    if (application.android) {
      application.android.on(application.AndroidApplication.activityBackPressedEvent, function (args) {
           console.log("Event: " + args.eventName + ", Activity: " + args.activity);
           if(home){
            socket.disconnect();
            console.log("disconnected"); 
           }

       });
    }
};

exports.listItemOnClick = function(item){
  home = false;
  console.log("List Item click");
  console.log("chatList: "+chatsList[friends[item.index].id]);
  var topmost = frameModule.topmost();
  var navigationEntry = {
    moduleName: "views/room/room",
    context: {
              socket: socket,
              name: name,
              id: id,
              endName: friends[item.index].name,
              endID: friends[item.index].id,
              chatList: chatsList[friends[item.index].id]
             },
    animated: true
  };
  topmost.navigate(navigationEntry);
  console.log(item.index);
};


var load = function(msg){
  for(var i=0 ; i<msg.length ; i++){
    if(msg[i].id != id){
      friends.push({name: msg[i].name, id: msg[i].id})
      list.push({name: msg[i].name});
      if(chatsList[msg[i].id] === undefined){
        chatsList[msg[i].id] = [];
      }
    }
  }
};

var empty = function(){
  // list.length=0;
  // friends.length=0;
  // list.splice(0, list.length);
  // friends.splice(0, friends.length);
  while(list.length){
    list.pop();
    friends.pop();
  }
};
