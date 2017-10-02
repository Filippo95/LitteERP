<%@page import="BusinessLogic.Interface.UserInterface"%>
<%@page import="java.util.List"%>
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
        if (status.equals("delete")) {

            loggedon = UserBean.delete(cookies);
        }
        if (status.equals("view")) {
            loggedon = UserBean.list(cookies);
        }
    }
    if (UserBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (UserBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {%>

<jsp:forward page="index.jsp"/>
<% }
    List<UserInterface> userArray = UserBean.getAll();
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
        <link href="css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
        <title>User View</title>
        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>
        <script language="javascript">
            function modifyUser(code)
            {
                document.modifyForm.id.value = code;
                document.modifyForm.submit();
                return;
            }

            function insertUser()
            {
                document.insertForm.submit();
                return;
            }

            function deleteUser(code)
            {
                document.deleteForm.id.value = code;
                document.deleteForm.submit();
                return;
            }
            function detailsUser(code)
            {
                document.detailsForm.id.value = code;
                document.detailsForm.submit();
                return;
            }

        </script>
        <script language="JavaScript" src="https://code.jquery.com/jquery-1.11.1.min.js" type="text/javascript"></script>
        <script language="JavaScript" src="https://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js" type="text/javascript"></script>
        <script language="JavaScript" src="https://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/3/dataTables.bootstrap.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    </head>
    <body>
        <jsp:include page="Top.jsp" />

        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Utenti</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <a class="btn btn-default" href="javascript:insertUser();">Inserisci un nuovo utente</a>
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
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover" id="table">
                                <thead>
                                    <tr>
                                        <th>Username</th>
                                        <th>Nome</th>
                                        <th>Cognome</th>
                                        <th>Email</th>
                                        <th>Indirizzo</th>
                                        <th>Citt&agrave;</th>
                                        <th>Azione</th>
                                        <th>Azione</th>
                                        <th>Azione</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% int i;
                                        for (i = 0; i < userArray.size(); i++) {
                                    %><tr>
                                        <td><%=userArray.get(i).getUsername()%> </td>
                                        <td><%=userArray.get(i).getNome()%> </td>
                                        <td><%=userArray.get(i).getCognome()%> </td>
                                        <td><%=userArray.get(i).getEmail()%> </td>
                                        <td><%=userArray.get(i).getIndirizzo()%>, <%=userArray.get(i).getCivico()%></td>
                                        <td><%=userArray.get(i).getCity()%>, <%=userArray.get(i).getCap()%>   <%=userArray.get(i).getProvincia()%></td>
                                        <td><a class="btn btn-primary btn-sm btn-block" href="javascript:detailsUser('<%=userArray.get(i).getId()%>');">Visualizza</a></td>
                                        <td><a class="btn btn-primary btn-sm btn-block" href="javascript:modifyUser('<%=userArray.get(i).getId()%>');">Modifica</a></td>
                                        <td><a class="btn btn-warning btn-sm btn-block" href="javascript:deleteUser('<%=userArray.get(i).getId()%>');">Elimina</a></td>
                                    </tr><%
                                        }%>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <form name="insertForm" action="UserInsert.jsp" method="post">
            <input type="hidden" name="status" value="view"/>
        </form>
        <form name="detailsForm" action="UserDetails.jsp" method="post">
            <input type="hidden" name="id" />
            <input type="hidden" name="status" value="view"/>
        </form>
        <form name="modifyForm" action="UserInsert.jsp" method="post">
            <input type="hidden" name="status" value="view"/>
            <input type="hidden" name="id" />
        </form>

        <form name="deleteForm" action="User.jsp" method="post">
            <input type="hidden" name="status" value="delete"/>
            <input type="hidden" name="id"/>
        </form>
        <script>
            $(document).ready(function () {
                $('#table').dataTable();
            });
        </script>
        <jsp:include page="Bottom.jsp" /> 
<% } else
{%>
<jsp:forward page="NotAllowed.jsp"/>
<%
}%>