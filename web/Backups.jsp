<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="BackupsBean" scope="page" class="BusinessFlow.BackupsBean" />
<jsp:setProperty name="BackupsBean" property="*" />
<%
    boolean loggedon = false;

    Cookie[] cookies;
    cookies = request.getCookies();

    loggedon = BackupsBean.isLoggeOn(cookies);

    System.out.println("Sto per fare il greande if");
    if (cookies == null || !loggedon) {%>

<jsp:forward page="index.jsp"/>
<% }

    if(BackupsBean.getLg().getVu().getAdmin()){


%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bakups View</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

        <link href="css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">

        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>

        <!-- SB Admin CSS - Include with every page -->
        <link href="css/sb-admin.css" rel="stylesheet">

        <script language="JavaScript" src="https://code.jquery.com/jquery-1.11.1.min.js" type="text/javascript"></script>
        <script language="JavaScript" src="https://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js" type="text/javascript"></script>
        <script language="JavaScript" src="https://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/3/dataTables.bootstrap.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

    </head>
    <body>
        <jsp:include page="Top.jsp" />
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Backups</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">

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
                                        <th>File</th>
                                        <th>Data</th>
                                        <th>Scarica</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        for (File f : BackupsBean.contents) {
                                    %>
                                    <tr class="even gradeA">
                                        <td class="center"><%=f.getName() %></td>
                                        <td class="center"><%=f.lastModified() %></td>
                                        <td><a class="btn btn-warning btn-sm btn-block" href="Backups/<%=f.getName()%>">Scarica</a></td>
                                    </tr><%
                                         
   }
                                    %>
                                </tbody>
                            </table>

                            <form name="insertForm" action="BrandInsert.jsp" method="post">
                                <input type="hidden" name="status" value="view"/>
                            </form>

                            <form name="modifyForm" action="BrandInsert.jsp" method="post">
                                <input type="hidden" name="status" value="view"/>
                                <input type="hidden" name="id" />
                            </form>

                            <form name="deleteForm" action="Brand.jsp" method="post">
                                <input type="hidden" name="status" value="delete"/>
                                <input type="hidden" name="id"/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>  
        <script>
            $(document).ready(function () {
                $('#table').dataTable();
            });
        </script>

        <jsp:include page="Bottom.jsp" /> 

<%}
else
{
%>
<jsp:forward page="NotAllowed.jsp"/>
<%
}%>