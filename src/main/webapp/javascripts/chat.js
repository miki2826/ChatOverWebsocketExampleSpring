function getUser() {
    return sessionStorage.getItem("chat_user_name");
}

// IO
var sock = new SockJS(document.location.protocol + '//' + document.location.host + '/messages');

// Ready
$(function () {
    // Fetch
    var container = $('.container');
    var messages = container.find('.chat');
    var button = container.find('.sendButton');
    var input = container.find('.messageInput').focus();

    // Receive Message
    sock.onmessage = function(event) {
        var messageParsed = JSON.parse(event.data);
        var content = messageParsed.text;
        var user = messageParsed.user;
        var userImage = "http://placehold.it/50/55C1E7/fff&amp;text=" + user.charAt(0);
        var message = $('<li class="left clearfix"><span class="chat-img pull-left">' +
        '<img src="' + userImage + '"  class="img-circle">' +
        '</span>' +
        '<div class="chat-body clearfix">' +
        '<div class="header">' +
        '<strong class="primary-font">' + user + '</strong>' +
        '</div>' +
        '<p>' + content +
        '</p>' +
        '</div>' +
        '</li>');
        messages.append(message);
    };

    // Send Message
    input.on('keypress', function (event) {
        if (event.keyCode === 13) { // enter
            sendMessage();
        }
    });

    button.on('click', function (event) {
        sendMessage();
    });

    function sendMessage() {
        var user = getUser();
        var retMessage;
        retMessage = input.val().trim();

        var message = {
            user: user,
            text: retMessage
        };
        sock.send(JSON.stringify(message));
        input.val(''); // clear input
    }
});