$(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

var jsonForCreate = "{\"External_ID__c\" : \"2312121\", \"Request_Body__c\" : \"{\'id\':\'123456789\',\'payload\':[{\'id\':1,\'Command\':\'addUser\',\'data\':{\'AccountIds\':\'585212415\',\'Type\':\'employee\',\'firstName\':\'John\',\'lastName\':\'Smith\',\'email\':\'JohnSmith@pics.com\',\'UserId\':\'123456789\',\'locale\':\'en_US\'}},{\'id\':2,\'Command\':\'addUser\',\'data\':{\'AccountIds\':\'585212415\',\'type\':\'employee\',\'firstName\':\'Steve\',\'lastName\':\'Lee\',\'email\':\'SteveLee@pics.com\',\'UserId\':\'923456789\',\'locale\':\'en_US\'}},{\'id\':3,\'Command\':\'addUser\',\'data\':{\'AccountIds\':\'213221212\',\'type\':\'employee\',\'firstName\':\'Tony\',\'lastName\':\'Dong\',\'email\':\'TonyDong@pics.com\',\'UserId\':\'2323322323\',\'locale\':\'en_US\'}},{\'id\':4,\'Command\':\'getLearningObjects\'},{\'id\':5,\'Command\':\'getAssignments\'}]}\"}";
var jsonForUpdate = "{\"Status__c\" : \"Sent To EG\"}";

$('#request_json').val(jsonForCreate);

$('#btnPutData').click(function () {
    $.ajax({
        url: '/createitem',
        type: 'POST',
        data: $('#request_json').val(),
        success: function (data) {
            data = data.replace(/</g, "&lt;");
            data = data.replace(/>/g, "&gt;");
            if (data == "") data = "*nothing to return*";
            $('#placeholder').append("<div class=\"alert alert-success\"><strong>Response: </strong> " + data + "</div>");
            $("html, body").animate({scrollTop: $(document).height()}, 1000);
        },
        error: function (data) {
            $('#placeholder').append("<div class=\"alert alert-danger\"><strong>Response: </strong>" + data.responseText + "</div>");
            $("html, body").animate({scrollTop: $(document).height()}, 1000);
        }
    });
});

$('#btnGetData').click(function () {
    $.ajax({
        url: '/getdata',
        type: 'GET',
        success: function (data) {
            data = data.replace(/</g, "&lt;");
            data = data.replace(/>/g, "&gt;");
            if (data == "") data = "*nothing to return*";
            $('#placeholder').append("<div class=\"alert alert-success\"><strong>Response: </strong> " + data + "</div>");
            $("html, body").animate({scrollTop: $(document).height()}, 1000);
        },
        error: function (data) {
            $('#placeholder').append("<div class=\"alert alert-danger\"><strong>Response: </strong>" + data.responseText + "</div>");
            $("html, body").animate({scrollTop: $(document).height()}, 1000);
        }
    });
});

$('#btnUpdData').click(function () {
    $.ajax({
        url: '/updatedata',
        type: 'POST',
        data: $('#request_json').val(),
        success: function (data) {
            data = data.replace(/</g, "&lt;");
            data = data.replace(/>/g, "&gt;");
            if (data == "") data = "*nothing to return*";
            $('#placeholder').append("<div class=\"alert alert-success\"><strong>Response: </strong> " + data + "</div>");
            $("html, body").animate({scrollTop: $(document).height()}, 1000);
        },
        error: function (data) {
            $('#placeholder').append("<div class=\"alert alert-danger\"><strong>Response: </strong>" + data.responseText + "</div>");
            $("html, body").animate({scrollTop: $(document).height()}, 1000);
        }
    });
});

$('#btnDelData').click(function () {
    $.ajax({
        url: '/deletedata',
        type: 'POST',
        success: function (data) {
            data = data.replace(/</g, "&lt;");
            data = data.replace(/>/g, "&gt;");
            if (data == "") data = "*nothing to return*";
            $('#placeholder').append("<div class=\"alert alert-success\"><strong>Response: </strong> " + data + "</div>");
            $("html, body").animate({scrollTop: $(document).height()}, 1000);
        },
        error: function (data) {
            $('#placeholder').append("<div class=\"alert alert-danger\"><strong>Response: </strong>" + data.responseText + "</div>");
            $("html, body").animate({scrollTop: $(document).height()}, 1000);
        }
    });
});

$('#clrLog').click(function () {
    $('#placeholder').text("");
});

$('#setCreateJson').click(function () {
    $('#request_json').val(jsonForCreate);
});

$('#setUpdateJson').click(function () {
    $('#request_json').val(jsonForUpdate);
});

$('#clrJson').click(function () {
    $('#request_json').val("");
});
