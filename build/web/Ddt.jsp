<%@page import="BusinessLogic.Interface.PeopleInterface"%>
<%@page import="BusinessLogic.Interface.DdtInterface"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="DdtBean" scope="page" class="BusinessFlow.DdtBean" />
<jsp:setProperty name="DdtBean" property="*" />
<%
    boolean loggedon = false;

    Cookie[] cookies;
    cookies = request.getCookies();
    String status;
    loggedon = DdtBean.list(cookies);
    if (DdtBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (DdtBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {%>

<jsp:forward page="index.jsp"/>
<% }
    List<DdtInterface> ddtArray = DdtBean.getAll();
    List<PeopleInterface> clienti = DdtBean.getPeople();
%>
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Ddt View</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>

        <link href="css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">


        <!-- SB Admin CSS - Include with every page -->
        <link href="css/sb-admin.css" rel="stylesheet">
        <script language="javascript">
            function detailsProduct(code)
            {
                document.detailsForm.id.value = code;
                document.detailsForm.submit();
                return;
            }
            function modifyDdt(code)
            {
                document.modifyForm.id.value = code;
                document.modifyForm.submit();
                return;
            }

            function insertDdt()
            {
                document.insertForm.submit();
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
                <h1 class="page-header">Documenti di Trasporto</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <a class="btn btn-default" href="javascript:insertDdt();">Nuovo Documento di Trasporto</a>
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
                                        <th>Cliente</th>
                                        <th>Data di registrazione</th>
                                        <th>Azione</th>
                                        <th>Azione</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% int i;
                                        for (i = 0; i < ddtArray.size(); i++) {
                                    %><tr class="even gradeA">
                                        <td class="center"><%=ddtArray.get(i).getClientName() %> </td>
                                        <% GregorianCalendar cal=ddtArray.get(i).getDdate();
                                            int day = cal.get(Calendar.DAY_OF_MONTH);
                                            int month = cal.get(Calendar.MONTH);
                                            int year = cal.get(Calendar.YEAR);
                                            String date = day + "-" + (month+1) + "-" + year;%>
                                        <td class="center"><%=date%></td>
                                        <td><a class="btn btn-default btn-sm btn-block" href="javascript:detailsProduct('<%=ddtArray.get(i).getId()%>');">Visualizza</a></td>
                                        <td><a class="btn btn-primary btn-sm btn-block" href="javascript:modifyDdt('<%=ddtArray.get(i).getId()%>');">Modifica</a></td>
                                    </tr><%
                                        }%>
                                </tbody>
                            </table>

                            <form name="detailsForm" action="DdtDetails.jsp" method="post">
                                <input type="hidden" name="id" />
                                <input type="hidden" name="status" value="view"/>
                            </form>
                            <form name="insertForm" action="DdtInsert.jsp" method="post">
                                <input type="hidden" name="status" value="view"/>
                            </form>

                            <form name="modifyForm" action="DdtInsert.jsp" method="post">
                                <input type="hidden" name="status" value="view"/>
                                <input type="hidden" name="id" />
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

