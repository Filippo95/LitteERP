<%@page import="BusinessLogic.Interface.UserInterface"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>

<jsp:useBean id="UserBean" scope="page" class="BusinessFlow.UserBean" />
<jsp:setProperty name="UserBean" property="*" />
<%
    boolean loggedon = false;

    Cookie[] cookies;
    cookies = request.getCookies();
    String status;
    if (request.getParameter("status") == null) {
        status = "view";
    } else {
        status = request.getParameter("status");
    }
    if (cookies != null) {
        if (status.equals("insert")) {
            loggedon = UserBean.insert(cookies);
        }
        if (status.equals("modify")) {
            loggedon = UserBean.update(cookies);
        }
        if (status.equals("view")) {
            loggedon = UserBean.getOne(cookies);
        }
    }

    if (UserBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (UserBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {%>

<jsp:forward page="index.jsp"/>
<% }
    UserInterface user = UserBean.getAll().get(0);

 if(UserBean.getLg().getVu().getAdmin())
{
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
        <link href="css/sb-admin.css" rel="stylesheet">
        <title>User Details</title>
        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>
        <script language="javascript">
            function cancel()
            {
                document.cancelForm.submit();
                return;
            }
            function modify()
            {
                document.modifyForm.submit();
                return;
            }
        </script>
        <script language="JavaScript" src="https://code.jquery.com/jquery-1.11.1.min.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

    </head>
    <body>
        <jsp:include page="Top.jsp" />

        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Dati dell'utente</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <h1></h1>
            </div>
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h3>Username</h3>
                        <p><%=user.getUsername()%></p>
                        <h3>Password</h3>
                        <p><%=user.getPasswd()%></p>
                        <h3>Nome</h3>
                        <p><%=user.getNome()%></p>
                        <h3>Cognome</h3>
                        <p><%=user.getCognome()%></p>

                    </div>
                </div>
            </div>
            <div class="col-lg-6"><div class="panel panel-default">
                    <div class="panel-body">
                        <h3>Telefono</h3>
                        <p><%=user.getTel()%></p>
                        <h3>Email</h3>
                        <p><%=user.getEmail()%></p>
                        <h3>Indirizzo</h3>
                        <address>
                            <%=user.getIndirizzo()%>, <%=user.getCivico()%>
                            <br><%=user.getCity()%>, <%=user.getProvincia()%>  <%=user.getCap()%>
                        </address>
                        <h3>Amministratore</h3>
                        <p><%=(user.getAdmin() ? "S&igrave;" : "No")%></p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <a href="javascript:cancel();" class="btn btn-primary btn-lg btn-block">Indietro</a>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <a href="javascript:modify();" class="btn btn-default btn-lg btn-block">Modifica</a>
            </div>
        </div>
        <form name="cancelForm" action="User.jsp" method="post">
            <input type="hidden" name="status" value="view"/>
        </form>
        <form name="modifyForm" action="UserInsert.jsp" method="post">
            <input type="hidden" name="status" value="modify"/>
            <input type="hidden" name="id" value="<%=UserBean.getId()%>"/>
        </form>

        <jsp:include page="Bottom.jsp" /> 
 
        <% }
else
{
%>

<jsp:forward page="NotAllowed.jsp"/>
<%
}%>