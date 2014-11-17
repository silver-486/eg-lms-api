<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@page session="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <title>Spring4 MVC -HelloWorld</title>
</head>
<body>
${exception}
<div class="container">
    <h1>Hello : ${name}</h1>

    <div>
        <a class="btn btn-danger" href="/j_spring_security_logout">Logout</a>
        <a class="btn btn-primary" href="${link}">go to salesforce..</a>
    </div>
    <br/>

    <div>
        <table class="table">
            <tr>
                <td style="width: 500px">
                    <textarea rows="20" cols="80" id="request_json"></textarea>
                    <p>tip: remain empty field and it will send valid json sample</p></td>
                <br/>

                <td style="vertical-align: bottom; width: 100px">
                    <input class="btn btn-primary" type="button" id="btnGetData" value="Get Data"></td>
                <td style="vertical-align: top; width: 400px;">
                    <div id="placeholder"></div>
                </td>
        </table>
    </div>
</div>

</body>
</html>

<script>
    $('#request_json').val("{\"id\":\"123456789\",\"payload\":[{\"id\":1,\"cmd\":\"addUser\",\"data\":{\"clientId\":\"585212415\",\"userType\":\"employee\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"email\":\"JohnSmith@pics.com\",\"employeeId\":\"123456789\",\"locale\":\"en-us\"}},{\"id\":2,\"cmd\":\"addUser\",\"data\":{\"clientId\":\"585212415\",\"userType\":\"employee\",\"firstName\":\"Steve\",\"lastName\":\"Lee\",\"email\":\"SteveLee@pics.com\",\"employeeId\":\"923456789\",\"locale\":\"en-us\"}},{\"id\":3,\"cmd\":\"addUser\",\"data\":{\"clientId\":\"213221212\",\"userType\":\"employee\",\"firstName\":\"Tony\",\"lastName\":\"Dong\",\"email\":\"TonyDong@pics.com\",\"employeeId\":\"2323322323\",\"locale\":\"en-us\"}},{\"id\":4,\"cmd\":\"getLearningObjects\"},{\"id\":5,\"cmd\":\"getAssignments\"}]}");

    $('#btnGetData').click(function () {
        $.ajax({
            url: '/getdata',
            type: 'POST',
            data: $('#request_json').val(),
            success: function (data) {
                $('#placeholder').append("<samp>Response: " + data + "</samp><br/>");
            },
            error: function (data) {
                alert("error");
            }
        });
    });

</script>