<%@page import="BusinessLogic.Interface.DrainInterface"%>
<%@page import="BusinessLogic.Interface.DrainRowInterface"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="DrainBean" scope="page" class="BusinessFlow.DrainBean" />
<jsp:setProperty name="DrainBean" property="*" />
<%
    boolean loggedon = false;
    List<DrainRowInterface> drainRow;
    Cookie[] cookies;
    cookies = request.getCookies();
    String status = null;
    if (request.getParameter("status") == null) {
        status = "view";
    } else {
        status = request.getParameter("status");
    }
    if (cookies != null) {
        if (status.equals("view")) {
            loggedon = DrainBean.getOne(cookies);
        } else {
            if (status.equals("insertDrainRow")) {
                loggedon = DrainBean.insertRow(cookies);
            }
            if (status.equals("deleteRow")) {
                loggedon = DrainBean.deleteRow(cookies);
            }
            status = "insertRowFromDetails";
        }
    }
    if (DrainBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (DrainBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {%>

<jsp:forward page="index.jsp"/>
<% }
    DrainInterface drain = DrainBean.getAll().get(0);
    drainRow = DrainBean.getDrainRows();
%>
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
        <link href="css/sb-admin.css" rel="stylesheet">
        <title>Drain Details View</title>
        
        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>
        <script language="javascript">
            function cancel()
            {
                document.cancelForm.submit();
                return;
            }

            function deleteRow(id_product, quantity) {
                document.mainForm.status.value = "deleteRow";
                document.mainForm.id_product.value = id_product;
                document.mainForm.quantity.value = quantity;
                document.mainForm.submit();
                return;
            }

            function submitForm(code,status)
            {
                document.mainForm.action = code;
                document.mainForm.status.value = status;
                document.mainForm.submit();
                return;
            }

        </script>
        <script language="JavaScript" src="https://code.jquery.com/jquery-1.11.1.min.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

    </head>
    <body>
        <jsp:include page="Top.jsp" />
        <%if (!status.equals("view")) {%>
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Inserimento o modifica Effettuata!</h1>
            </div>
        </div>
        <%}%>
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Dettagli dello scarico</h1>
            </div>
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h3>Data</h3>
                        <p><%=drain.getDdate()%></p>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3>Righe dello Scarico</h3>
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover" id="table">
                                <thead>
                                    <tr>
                                        <th>Modello Prodotto</th>
                                        <th>Quantit&agrave;</th>
                                        <th>Prezzo Unitario</th>
                                        <th>Azione</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% int i;
                                        for (i = 0; i < drainRow.size(); i++) {
                                    %>
                                    <tr class="even gradeA">
                                        <td class="center"><%=drainRow.get(i).getProductModel()%> </td>
                                        <td class="center"><%=drainRow.get(i).getQuantity()%> </td>
                                        <td class="center"><%=drainRow.get(i).getPrice()%> </td>
                                        <td><a class="btn btn-warning btn-sm" href="javascript:deleteRow('<%=drainRow.get(i).getId_product()%>','<%=drainRow.get(i).getQuantity()%>');" >Elimina</a><br></td>
                                    </tr><%
                                        }%>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <h4 class="page-header">Cosa vuoi fare ora?</h4>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <form role="form" name="mainForm" action="" method="post">
                                    <input type="hidden" name="id" value="<%=drain.getId()%>"/>
                                    <input type="hidden" name="status"/>
                                    <input type="hidden" name="id_product" value=""/>
                                    <input type="hidden" name="quantity" value=""/>
                                    <input type="hidden" name="subtot" value=""/>
                                    <%if (!status.equals("view")) {%>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:submitForm('Drain.jsp','view');" >Termina Inserimento</a>
                                    </div>
                                    <%}%>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:submitForm('DrainRowInsert.jsp','insertRowFromDetails');" >Inserimento/Modifica Nuova Riga</a>
                                    </div>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:cancel();" >Annulla</a>
                                    </div>
                                </form>
                                <form role="form" name="cancelForm" action="Drain.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
                                </form>
                                    <div class="form-group">
                                    <form role="form" name="" action="DocumentPrinter.jsp" method="post" target="_blank">
                                        <input type="hidden" name="doctype" value="drain">
                                        <input type="hidden" name="id" value="<%=drain.getId()%>">
                                        <input type="submit" class="btn btn-primary btn-lg btn-block" value="Stampa Documento di magazzino">
                                    </form>
                                </div>
                            </div>
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

