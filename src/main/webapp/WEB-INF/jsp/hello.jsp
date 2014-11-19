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
<style>
    button {
        margin-top: 5px;
        margin-bottom: 5px;
    }
</style>
${exception}
<div class="container">
    <h1>Current UserID : ${name}</h1>

    <div>
        <a class="btn btn-danger" href="/j_spring_security_logout">Logout</a>
        <a class="btn btn-info" href="/authAndGotoSF">SSO using SAML (for users)</a>
        <a class="btn btn-info" href="https://login.salesforce.com/services/oauth2/authorize?response_type=code&client_id=3MVG9xOCXq4ID1uEi1XVpEYYXpmWnCSZjW1r4TfXqoxTa1WHn84b.UcX8wNCA34xunCGqFzrCWTFzGRxXX1.J
&redirect_uri=https%3A%2F%2Fpolar-river-8354.herokuapp.com%2Fcallback">Authorize with OAuth (for API)</a>
    </div>
    <br/>

    <div>
        <table class="table">
            <tr>
                <td style="width: 500px">
                    <textarea rows="20" cols="80" id="request_json"></textarea>
                    <button class="btn btn-xs btn-success" id="setCreateJson">JSON preset for create</button>
                    <button class="btn btn-xs btn-success" id="setUpdateJson">JSON preset for update</button>
                    <button class="btn btn-xs btn-danger" id="clrJson">Clear</button>
                    <p style="font-style: italic;">tip: remain empty field and it will send valid json sample</p>
                </td>
                <br/>

                <td style="vertical-align: bottom; width:100px">
                    <div style="display: block; width: 100px;">
                        <button class="btn btn-block btn-default" id="clrLog">Clear log</button>
                        <button class="btn btn-primary btn-block" type="button" id="btnPutData">Create</button>
                        <button class="btn btn-primary btn-block" type="button" id="btnGetData">Retrieve</button>
                        <button class="btn btn-primary btn-block" type="button" id="btnUpdData">Update</button>
                        <button class="btn btn-primary btn-block" type="button" id="btnDelData">Delete</button>
                    </div>
                </td>
                <td style="vertical-align: top; width: 700px;">
                    <div style="width: 700px; word-wrap: break-word;" id="placeholder"></div>
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
                if (data == "" ) data="*nothing to return*";
                $('#placeholder').append("<div class=\"alert alert-success\"><strong>Response: </strong> " + data + "</div>");
                $("html, body").animate({scrollTop: $(document).height()}, 1000);
            },
            error: function (data) {
                $('#placeholder').append("<div class=\"alert alert-danger\"><strong>Response: </strong>Error</div>");
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
                if (data == "" ) data="*nothing to return*";
                $('#placeholder').append("<div class=\"alert alert-success\"><strong>Response: </strong> " + data + "</div>");
                $("html, body").animate({scrollTop: $(document).height()}, 1000);
            },
            error: function (data) {
                $('#placeholder').append("<div class=\"alert alert-danger\"><strong>Response: </strong>Error</div>");
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
                if (data == "" ) data="*nothing to return*";
                $('#placeholder').append("<div class=\"alert alert-success\"><strong>Response: </strong> " + data + "</div>");
                $("html, body").animate({scrollTop: $(document).height()}, 1000);
            },
            error: function (data) {
                $('#placeholder').append("<div class=\"alert alert-danger\"><strong>Response: </strong>Error</div>");
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
                if (data == "" ) data="*nothing to return*";
                $('#placeholder').append("<div class=\"alert alert-success\"><strong>Response: </strong> " + data + "</div>");
                $("html, body").animate({scrollTop: $(document).height()}, 1000);
            },
            error: function (data) {
                $('#placeholder').append("<div class=\"alert alert-danger\"><strong>Response: </strong>Error</div>");
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

</script>