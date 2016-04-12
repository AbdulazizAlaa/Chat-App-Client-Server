var SocketIO = require('nativescript-socketio');
var frameModule = require('ui/frame');
var viewModule = require("ui/core/view");
var Observable = require("data/observable").Observable;
var dialogsModule = require("ui/dialogs");
var view;
var socket;

var application = require("application");

var user = new Observable({
  username: "",
  status: ""
});

exports.loaded = function(v){
  console.log("Login view");

  view = v.object;

  view.bindingContext = user;
};


exports.signin = function(){
  if(user.username != ""){

    viewModule.getViewById(view, 'signinB').isEnabled = false;

    socket = new SocketIO("https://nodechat-korayementality-1.c9users.io/", {});

    socket.emit('name chosen', {name: user.username});

    socket.on('confirm name', function(msg){
      // alert("Signing in "+ user.username);
      viewModule.getViewById(view, 'signinB').isEnabled = true;

      if(view.android) {
        var Toast = android.widget.Toast;
        Toast.makeText(application.android.context, "Joined "+msg.name+" <3", Toast.LENGTH_LONG).show();
      }

      var topmost = frameModule.topmost();
      var navigationEntry = {
        moduleName: "views/home/home",
        context: {socket: socket, name: msg.name, id: msg.id},
        animated: true
      };
      topmost.navigate(navigationEntry);

      user.username = "";

    });


  }
};
