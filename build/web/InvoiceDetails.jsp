<%@page import="BusinessLogic.Interface.InvoiceInterface"%>
<%@page import="BusinessLogic.Interface.InvoiceRowInterface"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="InvoiceBean" scope="page" class="BusinessFlow.InvoiceBean" />
<jsp:setProperty name="InvoiceBean" property="*" />
<%
    boolean loggedon = false;
    List<InvoiceRowInterface> invoiceRowFree;
    List<InvoiceRowInterface> invoiceRowProduct;
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
            loggedon = InvoiceBean.getOne(cookies);
        } else if(status.equals("viewFromRelatedDdt")){
            loggedon=InvoiceBean.getOneByIdDrain(cookies);
            status="view";
        }else{
            if (status.equals("insertInvoice")) {
                loggedon = InvoiceBean.insert(cookies);
            }
            if (status.equals("modifyInvoice")) {
                loggedon = InvoiceBean.update(cookies);
            }
            if (status.equals("insertInvoiceRowFree")) {
                loggedon = InvoiceBean.insertRow(cookies, "rowfree");
            }
            if (status.equals("insertInvoiceRowProduct")) {
                loggedon = InvoiceBean.insertRow(cookies, "rowproduct");
            }
            if (status.equals("deleteRowFree")) {
                loggedon = InvoiceBean.deleteRow(cookies, "rowfree");
            }
            if (status.equals("deleteRowProduct")) {
                loggedon = InvoiceBean.deleteRow(cookies, "rowproduct");
            }
            if(status.equals("insertFromRelatedDdt")){
                loggedon=InvoiceBean.generateFromOtherDocument(cookies,"drain");
            }
            if(status.equals("insertFromQuote")){
                loggedon=InvoiceBean.generateFromOtherDocument(cookies,"quote");
            }
            status = "insert";
        }
    }
    if (InvoiceBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (InvoiceBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {%>

<jsp:forward page="index.jsp"/>
<% }
    InvoiceInterface invoice = InvoiceBean.getAll().get(0);
    invoiceRowFree = InvoiceBean.getInvoiceRowsFree();
    invoiceRowProduct = InvoiceBean.getInvoiceRowsProduct();
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
        <title>Invoice Details View</title>
        
        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>
        <script language="javascript">
            function cancel()
            {
                document.cancelForm.submit();
                return;
            }

            function deleteRowProduct(id_product, quantity, price) {
                document.mainForm.status.value = "deleteRowProduct";
                document.mainForm.id_product.value = id_product;
                document.mainForm.quantity.value = quantity;
                document.mainForm.price.value = price;
                document.mainForm.submit();
                return;
            }

            function deleteRowFree(description, quantity, unit_price) {
                document.mainForm.status.value = "deleteRowFree";
                document.mainForm.description.value = description;
                document.mainForm.quantity.value = quantity;
                document.mainForm.subtot.value = unit_price;
                document.mainForm.submit();
                return;
            }

            function submitForm(code, status)
            {
                document.mainForm.action = code;
                document.mainForm.status.value = status;
                document.mainForm.submit();
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
        <%if (!status.equals("view")) {%>
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Inserimento o modifica Effettuata!</h1>
            </div>
        </div>
        <%}%>
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Dettagli dela Fattura</h1>
            </div>
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <% GregorianCalendar cal = invoice.getIdate();
                            int day = cal.get(Calendar.DAY_OF_MONTH);
                            int month = cal.get(Calendar.MONTH);
                            int year = cal.get(Calendar.YEAR);
                            String date = day + "-" + (month + 1) + "-" + year;%>
                        <h3>Data</h3>
                        <p><%=date%></p>
                        <h3>Cliente</h3>
                        <p><%=invoice.getClientName()%></p>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h3>Scadenza</h3>
                        <%cal.add(Calendar.DATE, invoice.getExpiring_days());
                            day = cal.get(Calendar.DAY_OF_MONTH);
                            month = cal.get(Calendar.MONTH);
                            year = cal.get(Calendar.YEAR);
                            date = day + "-" + (month + 1) + "-" + year;%>
                        <p><%=date%></p>
                        <h3>Pagata</h3>
                        <p><%=(invoice.getPayed() ? "S&igrave" : "No")%></p>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3>Righe del Invoice</h3>
                    </div>
                    <div class="panel-body">
                        <%for (InvoiceRowInterface rowProduct:invoiceRowProduct) {%>
                        <br><p>Prodotto:  <%=rowProduct.getProductModel()%>   Quantit&agrave;: <%=rowProduct.getQuantity()%>   Prezzo: <%=rowProduct.getPrice()%></p>
                        <a class="btn btn-warning btn-sm" href="javascript:deleteRowProduct('<%=rowProduct.getId_product()%>','<%=rowProduct.getQuantity()%>','<%=rowProduct.getPrice()%>');" >Elimina</a><br>
                        <%}%>
                        <%for (InvoiceRowInterface rowFree:invoiceRowFree) {%>
                        <br><p>Descrizione:  <%=rowFree.getDescription()%>   Quantit&agrave;: <%=rowFree.getQuantity()%>   Sub-Totale:<%=rowFree.getSubtot()%></p>
                        <a class="btn btn-warning btn-sm" href="javascript:deleteRowFree('<%=rowFree.getDescription()%>','<%=rowFree.getQuantity()%>','<%=rowFree.getSubtot()%>');" >Elimina</a><br>
                        <%}%>
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
                                    <input type="hidden" name="id" value="<%=invoice.getId()%>">
                                    <input type='hidden' name='id_drain' value="<%=invoice.getId_drain()%>">
                                    <input type="hidden" name="status">
                                    <input type="hidden" name="id_product" value="">
                                    <input type="hidden" name="quantity" value="">
                                    <input type="hidden" name="price" value="">
                                    <input type="hidden" name="description" value=">">
                                    <input type="hidden" name="subtot" value="">
                                    <%if (!status.equals("view")) {%>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:submitForm('Invoice.jsp','view');" >Termina Inserimento</a>
                                    </div>
                                    <%} else {%>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:submitForm('InvoiceInsert.jsp','modify');" >Modifica Intestazione Fattura</a>
                                    </div>
                                    <%}%>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:submitForm('InvoiceRowProductInsert.jsp','insert');" >Inserimento/Modifica Righe con Prodotti</a>
                                    </div>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:submitForm('InvoiceRowFreeInsert.jsp','insert');" >Inserimento/Modifica Righe Libere</a>
                                    </div>

                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:cancel();" >Annulla</a>
                                    </div>
                                </form>
                                <form role="form" name="cancelForm" action="Invoice.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
                                </form>
                            </div>
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <form role="form" name="relatedDrainForm" action="DrainDetails.jsp" method="post">
                                        <input type="hidden" name="status" value="view">
                                        <input type="hidden" name="id" value="<%=invoice.getId_drain()%>">
                                        <input type="submit" class="btn btn-primary btn-lg btn-block" value="Vai allo scarico corrispondente">
                                    </form>
                                </div>
                                <%if (InvoiceBean.getDdtExists()) {%>
                                <div class="form-group">
                                    <form role="form" name="relatedDdtForm" action="DdtDetails.jsp" method="post">
                                        <input type="hidden" name="status" value="viewFromRelatedInvoice">
                                        <input type="hidden" name="id_drain" value="<%=invoice.getId_drain()%>">
                                        <input type="submit" class="btn btn-primary btn-lg btn-block" value="Vai al DDT corrispondente">
                                    </form>
                                </div>
                                <%}%>
                                <div class="form-group">
                                    <form role="form" name="" action="DocumentPrinter.jsp" method="post" target="_blank">
                                        <input type="hidden" name="doctype" value="invoice">
                                        <input type="hidden" name="id" value="<%=invoice.getId()%>">
                                        <input type="submit" class="btn btn-primary btn-lg btn-block" value="Stampa Fattura">
                                    </form>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>


        <jsp:include page="Bottom.jsp" /> 
