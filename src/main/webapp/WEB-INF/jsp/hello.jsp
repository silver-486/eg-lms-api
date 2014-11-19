<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@page session="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <title>Spring4 MVC -HelloWorld</title>
</head>
<body>
${exception}
<div class="container">
    <h1>Current UserID : ${name}</h1>

    <div>
        <a class="btn btn-danger" href="/j_spring_security_logout">Logout</a>
        <a class="btn btn-primary" href="/authAndGotoSF">go to salesforce..</a>
    </div>
    <br/>

    <div>
        <table class="table">
            <tr>
                <td style="width: 500px">
                    <textarea rows="20" cols="80" id="request_json"></textarea>

                    <p>tip: remain empty field and it will send valid json sample</p></td>
                <br/>

                <td style="vertical-align: bottom; width:180px">
                    <div style="display: inline-block">
                        <button class="btn btn-primary" type="button" id="btnPutData" >Put Data</button>
                        <button class="btn btn-primary" type="button" id="btnGetData" >Get Data</button>
                    </div>
                </td>
                <td style="vertical-align: top; width: 400px;">
                    <div style=" width: 390px;"><button class="btn btn-block btn-default" id="clrLog">Clear</button></div>
                    <div style="width: 390px; word-wrap: break-word;" id="placeholder"></div>
                </td>
        </table>
    </div>
</div>

</body>
</html>

<script>
    $(function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    });

    $('#request_json').val("{\"External_ID__c\" : \"2312121\", \"Request_Body__c\" : \"{\'id\':\'123456789\',\'payload\':[{\'id\':1,\'Command\':\'addUser\',\'data\':{\'AccountIds\':\'585212415\',\'Type\':\'employee\',\'firstName\':\'John\',\'lastName\':\'Smith\',\'email\':\'JohnSmith@pics.com\',\'UserId\':\'123456789\',\'locale\':\'en_US\'}},{\'id\':2,\'Command\':\'addUser\',\'data\':{\'AccountIds\':\'585212415\',\'type\':\'employee\',\'firstName\':\'Steve\',\'lastName\':\'Lee\',\'email\':\'SteveLee@pics.com\',\'UserId\':\'923456789\',\'locale\':\'en_US\'}},{\'id\':3,\'Command\':\'addUser\',\'data\':{\'AccountIds\':\'213221212\',\'type\':\'employee\',\'firstName\':\'Tony\',\'lastName\':\'Dong\',\'email\':\'TonyDong@pics.com\',\'UserId\':\'2323322323\',\'locale\':\'en_US\'}},{\'id\':4,\'Command\':\'getLearningObjects\'},{\'id\':5,\'Command\':\'getAssignments\'}]}\"}");

    $('#btnPutData').click(function () {
        $.ajax({
            url: '/createitem',
            type: 'POST',
            data: $('#request_json').val(),
            success: function (data) {
                data = data.replace(/</g, "&lt;");
                data = data.replace(/>/g, "&gt;");
                $('#placeholder').append("<div class=\"alert alert-success\"><strong>Response:</strong> " + data + "</div>");
            },
            error: function (data) {
                $('#placeholder').append("<div class=\"alert alert-danger\"><strong>Response:</strong>Error</div>");
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
                $('#placeholder').append("<div class=\"alert alert-success\"><strong>Response:</strong> " + data + "</div>");
            },
            error: function (data) {
                $('#placeholder').append("<div class=\"alert alert-danger\"><strong>Response:</strong>Error</div>");
            }
        });
    });

    $('#clrLog').click(function () {
        $('#placeholder').text("");
    });

</script>