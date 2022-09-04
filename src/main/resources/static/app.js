var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    const socket = new SockJS('http://localhost:8081/task-socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({username: $("#user").val()}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/tasks/pending-actions', function (pendingAction) {
            console.log(pendingAction.body)
            showGreeting(JSON.parse(pendingAction.body).action);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function pushAction() {
    stompClient.send("/card/action", {}, JSON.stringify({ 'role_id': $("#role").val(), 'action': $("#action").val() }));
}

function showGreeting(message) {
    $("#task").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { pushAction(); });
});