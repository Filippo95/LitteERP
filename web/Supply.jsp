<%@page import="BusinessLogic.Interface.SupplyInterface"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="SupplyBean" scope="page" class="BusinessFlow.SupplyBean" />
<jsp:setProperty name="SupplyBean" property="*" />
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

            loggedon = SupplyBean.delete(cookies);
        }
        if (status.equals("insert")) {
            loggedon = SupplyBean.insert(cookies);
        }
        if (status.equals("modify")) {
            loggedon = SupplyBean.update(cookies);
        }
        if (status.equals("view")) {
            loggedon = SupplyBean.list(cookies);
        }
    }
    if (SupplyBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (SupplyBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {%>

<jsp:forward page="index.jsp"/>
<% }
    List<SupplyInterface> supplyArray = SupplyBean.getAll();

%>
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Supply View</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>
        <link href="css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">


        <!-- SB Admin CSS - Include with every page -->
        <link href="css/sb-admin.css" rel="stylesheet">
        <script language="javascript">
            function modifySupply(code)
            {
                document.modifyForm.id.value = code;
                document.modifyForm.submit();
                return;
            }

            function insertSupply()
            {
                document.insertForm.submit();
                return;
            }

            function deleteSupply(code)
            {
                document.deleteForm.id.value = code;
                document.deleteForm.submit();
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
                <h1 class="page-header">Forniture</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <a class="btn btn-default" href="javascript:insertSupply();">Nuova fornitura</a>
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
                                        <th>Prodotto</th>
                                        <th>Fornitore</th>
                                        <th>Quantit&agrave;</th>
                                        <th>Prezzo unitario</th>
                                        <th>Data di registrazione</th>
                                        <th>Azione</th>
                                        <th>Azione</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% int i;
                                        for (i = 0; i < supplyArray.size(); i++) {
                                    %><tr class="even gradeA">
                                        <td class="center"><%=supplyArray.get(i).getProductName()%> </td>
                                        <td class="center"><%=supplyArray.get(i).getProviderName()%></td>
                                        <td class="center"><%=supplyArray.get(i).getQuantity()%></td>
                                        <td class="center"><%=supplyArray.get(i).getProvider_unit_price()%></td>
                                        <% GregorianCalendar cal = supplyArray.get(i).getLast_mod();
                                            int day = cal.get(Calendar.DAY_OF_MONTH);
                                            int month = cal.get(Calendar.MONTH);
                                            int year = cal.get(Calendar.YEAR);
                                            String date = day + "-" + (month + 1) + "-" + year;%>
                                        <td class="center"><%=date%></td>
                                        <td><a class="btn btn-primary btn-sm btn-block" href="javascript:modifySupply('<%=supplyArray.get(i).getId()%>');">Modifica</a></td>
                                        <td><a class="btn btn-warning btn-sm btn-block" href="javascript:deleteSupply('<%=supplyArray.get(i).getId()%>');">Elimina</a></td>
                                    </tr><%
                                        }%>
                                </tbody>
                            </table>

                            <form name="insertForm" action="SupplyInsert.jsp" method="post">
                                <input type="hidden" name="status" value="view"/>
                            </form>

                            <form name="modifyForm" action="SupplyInsert.jsp" method="post">
                                <input type="hidden" name="status" value="view"/>
                                <input type="hidden" name="id" />
                            </form>

                            <form name="deleteForm" action="Supply.jsp" method="post">
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

