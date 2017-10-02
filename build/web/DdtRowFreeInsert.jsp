<%@page import="BusinessLogic.Interface.DdtRowInterface"%>
<%@page import="BusinessLogic.Interface.DdtInterface"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="DdtBean" scope="page" class="BusinessFlow.DdtBean" />
<jsp:setProperty name="DdtBean" property="*" />
<%
    boolean loggedon = false;
    DdtInterface ddt;
    List<DdtRowInterface> ddtRows;
    Cookie[] cookies;
    cookies = request.getCookies();
    String status = null;
    if (request.getParameter("status") == null) {
%>
<jsp:forward page="Ddt.jsp"/>
<%
    } else {
        status = request.getParameter("status");
    }
    if (status.equals("insert")) {
        loggedon = DdtBean.isLoggedOn(cookies);
        status = "insertDdtRowFree";
    } else {
        if (status.equals("insertDdtRowFree")) {
            loggedon = DdtBean.insertRow(cookies, "rowfree");
        }
        if (status.equals("insertDdtRowProduct")) {
            loggedon = DdtBean.insertRow(cookies, "rowproduct");
            status = "insertDdtRowFree";
        }
    }
    if (DdtBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (DdtBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {
%>

<jsp:forward page="index.jsp"/>
<%
    }
    ddt = DdtBean.getAll().get(0);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Ddt Row Free Insert</title>
        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>
        <script language="javascript">

            function submitForm(code)
            {
                if (document.mainForm.description.value.length == 0)
                {
                    alert("Attenzione, inserire la descrizione");
                } else if (document.mainForm.quantity.value.length == 0)
                {
                    alert("Attenzione, inserire la quantità");
                } else if (document.mainForm.unit_price.value.length == 0)
                {
                    alert("Attenzione, inserire il prezzo");
                } else {
                    document.mainForm.action = code;
                    document.mainForm.status.value = "<%=status%>";
                    document.mainForm.submit();
                    return;
                }
            }

            function cancel()
            {
                document.cancelForm.submit();
                return;
            }
        </script>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
        <link href="css/sb-admin.css" rel="stylesheet">
        <script language="JavaScript" src="https://code.jquery.com/jquery-1.11.1.min.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

    </head>
    <body>
        <jsp:include page="Top.jsp" />
        <div class="row">
            <div class="col-lg-12">
                <h3 class="page-header">Stai inserendo riga riguardo al DDT:</h3>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h4>Data</h4>
                        <% GregorianCalendar cal = ddt.getDdate();
                            int day = cal.get(Calendar.DAY_OF_MONTH);
                            int month = cal.get(Calendar.MONTH);
                            int year = cal.get(Calendar.YEAR);
                            String date = day + "-" + (month + 1) + "-" + year;%>
                        <p><%=date%></p>
                        <h4>Cliente</h4>
                        <p><%=ddt.getClientName()%></p>
                        <h4>Vettore</h4>
                        <p><%if (ddt.getId_people_vett() != 0) {%><%=ddt.getVettName()%>
                            <%} else {%><%=""%><%}%></p>
                        <h4>Indirizzo di Spedizione</h4>
                        <p><%=ddt.getDestination()%></p>
                        <h4>Causa</h4>
                        <p><%=ddt.getCause()%></p>
                        <h4>Aspetto</h4>
                        <p><%=ddt.getAspect()%></p>
                        <h4>Numero Colli</h4>
                        <p><%=ddt.getNcoll()%></p> 
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <form role="form" name="mainForm" action="" method="post">
                                    <input type="hidden" name="id" value="<%=DdtBean.getId()%>">
                                    <input type="hidden" name="id_drain" value="<%=DdtBean.getId_drain()%>">
                                    <div class="form-group">
                                        <label>Descrizione:</label>
                                        <input type='text' class="form-control" name="description">
                                        <p class="help-block">Inserire qui la descrizione.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Quantit&agrave;</label>
                                        <input type='number' class="form-control" name="quantity">
                                        <p class="help-block">Inserire qui la quantit&agrave;.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Prezzo Unitario:</label>
                                        <input type='number' step="0.01" class="form-control" name="unit_price">
                                        <p class="help-block">Inserire qui il prezzo unitario.</p>
                                    </div>
                                    <input type="hidden" name="status">
                                    <div class="form-group">
                                        <a class="btn btn-default btn-lg btn-block" href="javascript:submitForm('DdtRowFreeInsert.jsp');">Inserisci Nuova Riga</a>
                                        <a class="btn btn-default btn-lg btn-block" href="javascript:submitForm('DdtRowProductInsert.jsp');">Inserisci Nuova Riga con Prodotto</a>
                                    </div>
                                    <div class="form-group">
                                        <a class="btn btn-warning" href="javascript:cancel();">Annulla</a>
                                        <a class="btn btn-primary" href="javascript:submitForm('DdtDetails.jsp');">Fine inserimento righe</a>
                                    </div>
                                </form>

                                <form name="cancelForm" action="DdtDetails.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
                                    <input type="hidden" name="id" value="<%=DdtBean.getId()%>"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="Bottom.jsp" />
