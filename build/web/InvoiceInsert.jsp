<%@page import="BusinessLogic.Interface.InvoiceInterface"%>
<%@page import="BusinessLogic.Interface.PeopleInterface"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="InvoiceBean" scope="page" class="BusinessFlow.InvoiceBean" />
<jsp:setProperty name="InvoiceBean" property="*" />
<%
    boolean loggedon = false;
    InvoiceInterface invoice = null;
    Cookie[] cookies;
    cookies = request.getCookies();
    String status = null;
    if (cookies != null) {
        if (InvoiceBean.getId() == 0) {
            loggedon = InvoiceBean.isLoggedOn(cookies);
            status = "insertInvoice";
        } else {
            status = "modifyInvoice";
            loggedon = InvoiceBean.getOne(cookies);
            invoice = InvoiceBean.getAll().get(0);
        }
    } 
    if (InvoiceBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (InvoiceBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {
%>

<jsp:forward page="index.jsp"/>
<%
    }
    List<PeopleInterface> people = InvoiceBean.getPeople();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Inserimento Fattura</title>
        
        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>
        <script language="javascript">

            function submitForm()
            {
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
                <h1 class="page-header">Inserimento/Modifica di una fattura (intestazione)</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <form role="form" name="mainForm" action="InvoiceDetails.jsp" method="post">
                                    <input type="hidden" name="id" <%if (status.equals("modifyInvoice")) {%>value="<%=invoice.getId()%>"<%}%>>
                                    <input type="hidden" name="id_drain" <%if (status.equals("modifyInvoice")) {%>value="<%=invoice.getId_drain()%>"<%}%>>
                                    <div class="form-group">
                                        <label>Seleziona il cliente</label>
                                        <select name="id_people" class="form-control">
                                            <%for (int i = 0; i < people.size(); i++) {%>
                                            <option value="<%=people.get(i).getId()%>"<%if (status.equals("modifyInvoice")) {
                                                    if (invoice.getId_people() == people.get(i).getId()) {%>selected="selected"<%}
                                                }%>><%=people.get(i).getName()%></option>
                                            <%}%>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Giorni prima della scadenza</label>
                                        <input class="form-control" type="number" name="expiring_days" <%if (status.equals("modifyInvoice")) {%>value="<%=invoice.getExpiring_days()%>"<%}%> >
                                        <p class="help-block">Inserire qui il numero di giorni prima della scadenza della fattura.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Pagata:</label>
                                        <div class="radio">
                                            <label>
                                                   <input type="radio" name="payed" value="true" <%if (status.equals("modifyInvoice")) {
                                                           if (invoice.getPayed()) {%>checked="checked"<%}
                                                                  }%>>S&igrave;
                                            </label>
                                        </div>
                                        <div class="radio">
                                            <label>
                                                   <input type="radio" name="payed" value="false" <%if (status.equals("modifyInvoice")) {
                                                           if (!invoice.getPayed()) {%>checked="checked"<%}
                                                                  }%>>No
                                            </label>
                                        </div>
                                    </div>
                                    <input type="hidden" name="status"/>
                                    <a class="btn btn-default" href="javascript:submitForm();" >Conferma</a>
                                    <a class="btn btn-default" href="javascript:cancel();" >Annulla</a>
                                </form>
                                <form role="form" name="cancelForm" action="Invoice.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="Bottom.jsp" />

