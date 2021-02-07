import SockJS from 'sockjs-client';
import {send} from '.././Handler/ConnectionHandler';

var Stomp = require('webstomp-client');
var stompClient = null;

export function connectToWebSocket(id,update) {
  var socket = new SockJS('https://localhost:8443/gs-guide-websocket');
  stompClient = Stomp.over(socket);
  stompClient.connect({'id':id}, function (frame) {
      stompClient.subscribe('/user/queue/greetings', function (greeting) {
        
          let body = greeting.body;
          let obj = JSON.parse(body);
          console.log('object '+obj) 
          update(obj);
      });
      sendMessage("hey");
  },
  (error)=>alert(error.code));
}

//send message
export function sendMessage(name) {
  stompClient.send("/app/hello", {},  name);
}

//close web soceket
export function closeSocket() {
  if(stompClient!==null) {
    stompClient.disconnect();
  }
}
