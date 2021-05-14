import SockJS from 'sockjs-client';
import {baseURL} from '../api/API';

var Stomp = require('webstomp-client');
var stompClient = null;

export function connectToWebSocket(id,update) {
  var socket = new SockJS(baseURL+'/gs-guide-websocket');
  stompClient = Stomp.over(socket);
  stompClient.connect({'apiKey':id}, function (frame) {
      stompClient.subscribe('/user/queue/greetings', function (greeting) {
          
          let body = greeting.body;
          let obj = JSON.parse(body);
          update(obj);
      });
  },
  (error)=>alert("diconnected: error code:"+ error.code));
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




