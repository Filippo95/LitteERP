<%@page import="BusinessLogic.Interface.InvoiceInterface"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="InvoiceBean" scope="page" class="BusinessFlow.InvoiceBean" />
<jsp:setProperty name="InvoiceBean" property="*" />
<%
    boolean loggedon = false;

    Cookie[] cookies;
    cookies = request.getCookies();
    String status;
    loggedon = InvoiceBean.list(cookies);
    if (InvoiceBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (InvoiceBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {%>

<jsp:forward page="index.jsp"/>
<% }
    List<InvoiceInterface> invoiceArray = InvoiceBean.getAll();
%>
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Invoice View</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

        <link href="css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">

        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>

        <!-- SB Admin CSS - Include with every page -->
        <link href="css/sb-admin.css" rel="stylesheet">
        <script language="javascript">
            function detailsProduct(code)
            {
                document.detailsForm.id.value = code;
                document.detailsForm.submit();
                return;
            }
            function modifyInvoice(code)
            {
                document.modifyForm.id.value = code;
                document.modifyForm.submit();
                return;
            }

            function insertInvoice()
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
                <h1 class="page-header">Fatture</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <a class="btn btn-default" href="javascript:insertInvoice();">Nuova Fattura</a>
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
                                        <th>Scadenza</th>
                                        <th>Pagata</th>
                                        <th>Azione</th>
                                        <th>Azione</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% int i,day,month,year;
                                    String date="";
                                        for (InvoiceInterface invoice:invoiceArray) {
                                    %>
                                    <tr class="even gradeA">
                                        <td class="center"><%=invoice.getClientName()%> </td>
                                        <% GregorianCalendar cal=invoice.getIdate();
                                            day = cal.get(Calendar.DAY_OF_MONTH);
                                            month = cal.get(Calendar.MONTH);
                                            year = cal.get(Calendar.YEAR);
                                            date = day + "-" + (month+1) + "-" + year;%>
                                        <td class="center"><%=date%></td>
                                        <%  cal.add(Calendar.DATE, invoice.getExpiring_days());
                                            day = cal.get(Calendar.DAY_OF_MONTH);
                                            month = cal.get(Calendar.MONTH);
                                            year = cal.get(Calendar.YEAR);
                                            date = day + "-" + (month+1) + "-" + year;%>
                                        <td class="center"><%=date%></td>
                                        <td class="center"><%=(invoice.getPayed() ? "S&igrave" : "No")%></td>
                                        <td><a class="btn btn-default btn-sm btn-block" href="javascript:detailsProduct('<%=invoice.getId()%>');">Visualizza</a></td>
                                        <td><a class="btn btn-primary btn-sm btn-block" href="javascript:modifyInvoice('<%=invoice.getId()%>');">Modifica</a></td>
                                    </tr><%
                                        }%>
                                </tbody>
                            </table>

                            <form name="detailsForm" action="InvoiceDetails.jsp" method="post">
                                <input type="hidden" name="id" />
                                <input type="hidden" name="status" value="view"/>
                            </form>
                            <form name="insertForm" action="InvoiceInsert.jsp" method="post">
                                <input type="hidden" name="status" value="view"/>
                            </form>

                            <form name="modifyForm" action="InvoiceInsert.jsp" method="post">
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

