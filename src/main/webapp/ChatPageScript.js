$(document).ready(function() {
    // Function to update chat messages
    function updateChat() {
        $.ajax({
            url: "message?friendId=${friendId}",
            type: "GET",
            success: function(data) {
                $("#chatMessages").html(data); // Update chat messages
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log("Error: " + textStatus);
            }
        });
    }

    // Update chat messages every 2 seconds
    setInterval(updateChat, 500);

    // Function to send new message
    $("#sendMessage").click(function() {
        var message = $("#messageInput").val().trim();
        if (message !== "") {
            $.post("message?friendId=${friendId}", { message: message });
            $("#messageInput").val(""); // Clear input field after sending message
        }
    });
});