<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="LoginBean" scope="page" class="BusinessFlow.LoginBean" />
<jsp:setProperty name="LoginBean" property="*" />
<%
    String status;
    status = request.getParameter("login");
    
    
    if (status == null) {
        status = "view";
    }
    System.out.println(status);
    boolean loggedon = false;
    Cookie[] cookies;
    cookies = request.getCookies();
    if (!status.equals("login")&&!status.equals("logout")) {
        loggedon = LoginBean.checkUser(cookies,"view");
    } else {
        if (status.equals("login")) {
            loggedon = LoginBean.checkUser(cookies,"login");
            if (loggedon) {
                cookies = LoginBean.getCookies();
                for (int i = 0; i < cookies.length; i++) {
                    response.addCookie(cookies[i]);
                }
            }
        }
        if (status.equals("logout")) {

            cookies = LoginBean.logout(cookies);
            for (int i = 0; i < cookies.length; i++) {
                response.addCookie(cookies[i]);
            }
            loggedon = false;
        }
    }

    if (LoginBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (LoginBean.getResult() == -2) {
    }
    if (loggedon) {
       
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Not Allowed</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>

        <!-- SB Admin CSS - Include with every page -->
        <link href="css/sb-admin.css" rel="stylesheet">

        <script language="JavaScript" src="https://code.jquery.com/jquery-1.11.1.min.js" type="text/javascript"></script>
        <script language="JavaScript" src="https://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js" type="text/javascript"></script>
        <script language="JavaScript" src="https://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/3/dataTables.bootstrap.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
        <!-- Hight Charts -->
        <script src="https://code.highcharts.com/highcharts.js"></script>
        <script src="https://code.highcharts.com/modules/exporting.js"></script>
        <script src="https://code.highcharts.com/highcharts-3d.js"></script>
        <script src="https://code.highcharts.com/modules/drilldown.js"></script>
        <script src="https://code.highcharts.com/modules/data.js"></script>




    </head>
    <body>
        <jsp:include page="Top.jsp" />
        <div class="jumbotron">
  <h1>Non hai i privilegi !</h1>
  <p>Pirtroppo non puoi accedere a questa area di amministrazione perche i tuoi privileg  non sono sufficienti, 
      è riservata agli amministratori!</p>
  
</div>
       

   


        <jsp:include page="Bottom.jsp" />          
        <%} else {


        %>
        <!DOCTYPE html>
    <html>

        <head>

            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">

            <title>Start Bootstrap - SB Admin Version 2.0 Demo</title>

            <!-- Core CSS - Include with every page -->
            <link href="css/bootstrap.min.css" rel="stylesheet">
            <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

            <!-- SB Admin CSS - Include with every page -->
            <link href="css/sb-admin.css" rel="stylesheet">
            <script language="javascript">

                function isEmpty(value) {

                    if (value == null || value.length == 0)
                        return true;
                    for (var count = 0; count < value.length; count++) {
                        if (value.charAt(count) != " ")
                            return false;
                    }
                    return true;
                }

                function submitLogon() {

                    if (isEmpty(login.username.value)) {
                        alert("Inserire uno username.");
                        return;
                    }

                    if (isEmpty(login.passwd.value)) {
                        alert("Inserire una password.");
                        return;
                    }

                    login.submit();
                }
                function setVisible(status, loggedon) {
                    if (status !== "login" && loggedon != true)
                    {
                        document.getElementById("loginError").style = "display:none";
                    }
                }

            </script>
            <script language="JavaScript" src="https://code.jquery.com/jquery-1.11.1.min.js" type="text/javascript"></script>
            <script language="JavaScript" src="https://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js" type="text/javascript"></script>
            <script language="JavaScript" src="https://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/3/dataTables.bootstrap.js" type="text/javascript"></script>
            <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">


        </head>

        <body onload="setVisible('<%=status%>', '<%=loggedon%>');">

            <div class="container">
                <div class="row">
                    <div class="col-md-4 col-md-offset-4">
                        <div class="login-panel panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">Please Sign In</h3>
                            </div>
                            <div class="panel-body">
                                <p id="loginError" class="text-danger">Login fallito, username o password errati</p>
                                <form role="form" action="index.jsp" name="login" method="post" >
                                    <fieldset>
                                        <div class="form-group">
                                            <input class="form-control" placeholder="Username" name="username" type="text" autofocus>
                                        </div>
                                        <input type="hidden" name="login" value="login">
                                        <div class="form-group">
                                            <input class="form-control" placeholder="Password" name="passwd" type="password" value="">
                                        </div>

                                        <!-- Change this to a button or input when using this as a form -->
                                        <input type="button" value="invia" class="btn btn-lg btn-success btn-block" onClick="submitLogon()">
                                    </fieldset>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Core Scripts - Include with every page -->
            <script src="js/jquery-1.10.2.js"></script>
            <script src="js/bootstrap.min.js"></script>
            <script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>

            <!-- SB Admin Scripts - Include with every page -->
            <script src="js/sb-admin.js"></script>


            <%    }
            %>
        </body>

    </html>