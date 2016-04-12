var SocketIO = require("nativescript-socketio");
var viewModule = require("ui/core/view");
var Observable = require("data/observable").Observable;
var ObservableArray = require("data/observable-array").ObservableArray;
var dialogsModule = require("ui/dialogs");
var actionBar = require("ui/action-bar").ActionBar;
var application = require("application");
var view;
var socket;
var name;
var id;
var endName;
var endID;

var list = new ObservableArray([]);

var data = new Observable({
  ChatList: list,
  message: ""
});


exports.loaded = function(v){
  console.log("Chat Room");

  view = v.object;

  name = view.navigationContext.name;
  id = view.navigationContext.id;
  endName = view.navigationContext.endName;
  endID = view.navigationContext.endID;
  socket = view.navigationContext.socket;


  console.log(actionBar.title);
  actionBar.title = name;

  view.bindingContext = data;

  socket.emit("start chat", {name: name,id: id, endID:endID});

  // console.log(view.navigationContext.chatList.length);
  // console.log(view.navigationContext.chatList);
  // if(view.navigationContext.chatList.length > 0)
  //   console.log(view.navigationContext.chatList[0].name);


  empty();
  var msg;
  for(var i=0 ; i<view.navigationContext.chatList.length ; i++){
    console.log("room--from: "+view.navigationContext.chatList[i].name +" message: "+ view.navigationContext.chatList[i].text);
    // msg = view.navigationContext.chatList[i];
    list.push({text: view.navigationContext.chatList[i].name+": "+view.navigationContext.chatList[i].text});
  }


  socket.on('deliver message', function(msg){
      console.log("room--from: "+msg.name+" message: "+msg.message);
      list.push({text: msg.name+": "+msg.message});
  });


};


exports.send = function(){
  if(data.message != ""){
    socket.emit("chat message", {name: name, id: id, endID: endID, message: data.message});

    console.log("room--from: "+name+" message: "+data.message);
    list.push({text: name+": "+data.message});
    data.message = "";
  }
};

var empty = function(){
  while(list.length){
    list.pop();
  }
};
