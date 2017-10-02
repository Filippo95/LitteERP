<%@page import="BusinessLogic.Interface.QuoteRowInterface"%>
<%@page import="BusinessLogic.Interface.QuoteInterface"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="QuoteBean" scope="page" class="BusinessFlow.QuoteBean" />
<jsp:setProperty name="QuoteBean" property="*" />
<%
    boolean loggedon = false;
    QuoteInterface quote;
    List<QuoteRowInterface> quoteRows;
    Cookie[] cookies;
    cookies = request.getCookies();
    String status = null;
    if (request.getParameter("status") == null) {
%>
<jsp:forward page="Quote.jsp"/>
<%
    } else {
        status = request.getParameter("status");
    }
    if (status.equals("insert")) {
        loggedon = QuoteBean.isLoggedOn(cookies);
        status = "insertQuoteRowFree";
    } else {
        if (status.equals("insertQuoteRowFree")) {
            loggedon = QuoteBean.insertRow(cookies, "rowfree");
        }
        if (status.equals("insertQuoteRowProduct")) {
            loggedon = QuoteBean.insertRow(cookies, "rowproduct");
            status = "insertQuoteRowFree";
        }
    }
    if (QuoteBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (QuoteBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {
%>

<jsp:forward page="index.jsp"/>
<%
    }
    quote = QuoteBean.getAll().get(0);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quote Row Free Insert</title>
        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>
        <script language="javascript">

            function submitForm(code)
            {
                document.mainForm.action = code;
                document.mainForm.status.value = "<%=status%>";
                document.mainForm.submit();
                return;
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
        <script language="JavaScript" src="https://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

    </head>
    <body>
        <jsp:include page="Top.jsp" />
        <div class="row">
            <div class="col-lg-12">
                <h3 class="page-header">Stai inserendo una riga riguardo al preventivo:</h3>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h4>Data di Scadenza</h4>
                        <p><%=quote.getExpiring_date()%></p>
                        <h4>Cliente</h4>
                        <p><%=quote.getClientName()%></p>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h4>Descrizione</h4>
                        <p><%=quote.getDescription()%></p>
                        <h4>Approvata</h4>
                        <p><%=(quote.getApproved() ? "S&igrave" : "No")%></p>
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
                                    <input type="hidden" name="id" value="<%=QuoteBean.getId()%>"/><br/>
                                    <div class="form-group">
                                        <label>Descrizione:</label>
                                        <input type='text' class="form-control" name="description"/>
                                        <p class="help-block">Inserire qui la descrizione.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Quantit&agrave;</label>
                                        <input type='number' class="form-control" name="quantity"/>
                                        <p class="help-block">Inserire qui la quantit&agrave;.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Sub-Totale:</label>
                                        <input type='number' step="0.01" class="form-control" name="subtot"/>
                                        <p class="help-block">Inserire qui sub-totale.</p>
                                    </div>
                                    <input type="hidden" name="status"/>
                                    <div class="form-group">
                                        <a class="btn btn-default btn-lg btn-block" href="javascript:submitForm('QuoteRowFreeInsert.jsp');">Inserisci Nuova Riga</a>
                                        <a class="btn btn-default btn-lg btn-block" href="javascript:submitForm('QuoteRowProductInsert.jsp');">Inserisci Nuova Riga con Prodotto</a>
                                    </div>
                                    <div class="form-group">
                                        <a class="btn btn-warning" href="javascript:cancel();">Annulla</a>
                                        <a class="btn btn-primary" href="javascript:submitForm('QuoteDetails.jsp');">Fine inserimento righe</a>
                                    </div>
                                </form>

                                <form name="cancelForm" action="QuoteDetails.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
                                    <input type="hidden" name="id" value="<%=QuoteBean.getId()%>"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="Bottom.jsp" />
