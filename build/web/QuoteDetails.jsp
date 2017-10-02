<%@page import="BusinessLogic.Interface.QuoteInterface"%>
<%@page import="BusinessLogic.Interface.QuoteRowInterface"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="QuoteBean" scope="page" class="BusinessFlow.QuoteBean" />
<jsp:setProperty name="QuoteBean" property="*" />
<%
    boolean loggedon = false;
    List<QuoteRowInterface> quoteRowFree;
    List<QuoteRowInterface> quoteRowProduct;
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
            loggedon = QuoteBean.getOne(cookies);
        } else if (status.equals("approve")) {
            loggedon=QuoteBean.approve(cookies);
            status="view";
        } else {
            if (status.equals("insertQuote")) {
                loggedon = QuoteBean.insert(cookies);
            }
            if (status.equals("modifyQuote")) {
                loggedon = QuoteBean.update(cookies);
            }
            if (status.equals("insertQuoteRowFree")) {
                loggedon = QuoteBean.insertRow(cookies, "rowfree");
            }
            if (status.equals("insertQuoteRowProduct")) {
                loggedon = QuoteBean.insertRow(cookies, "rowproduct");
            }
            if (status.equals("deleteRowFree")) {
                loggedon = QuoteBean.deleteRow(cookies, "rowfree");
            }
            if (status.equals("deleteRowProduct")) {
                loggedon = QuoteBean.deleteRow(cookies, "rowproduct");
            }
            status = "insert";
        }
    }

    if (QuoteBean.getResult()
            == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (QuoteBean.getResult()
            == -2) {
    }
    if (cookies == null || !loggedon) {%>

<jsp:forward page="index.jsp"/>
<% }
    QuoteInterface quote = QuoteBean.getAll().get(0);
    quoteRowFree = QuoteBean.getQuoteRowsFree();
    quoteRowProduct = QuoteBean.getQuoteRowsProduct();
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
        <title>Quote Details View</title>
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
        <%if (!status.equals(
                    "view")) {%>
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Inserimento o modifica Effettuata!</h1>
            </div>
        </div>
        <%}%>
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Dettagli del preventivo</h1>
            </div>
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h3>Data di Scadenza</h3>
                        <p><%=quote.getExpiring_date()%></p>
                        <h3>Cliente</h3>
                        <p><%=quote.getClientName()%></p>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h3>Descrizione</h3>
                        <p><%=quote.getDescription()%></p>
                        <h3>Approvata</h3>
                        <p><%=(quote.getApproved() ? "S&igrave" : "No")%></p>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3>Righe del Preventivo</h3>
                    </div>
                    <div class="panel-body">
                        <%for (int i = 0;
                                    i < quoteRowProduct.size();
                                    i++) {%>
                        <br><p>Prodotto:  <%=quoteRowProduct.get(i).getProductModel()%>   Quantit&agrave;: <%=quoteRowProduct.get(i).getQuantity()%>   Prezzo: <%=quoteRowProduct.get(i).getPrice()%></p>
                        <a class="btn btn-warning btn-sm" href="javascript:deleteRowProduct('<%=quoteRowProduct.get(i).getId_product()%>','<%=quoteRowProduct.get(i).getQuantity()%>','<%=quoteRowProduct.get(i).getPrice()%>');" >Elimina</a><br>
                        <%}%>
                        <%for (int i = 0;
                                    i < quoteRowFree.size();
                                    i++) {%>
                        <br><p>Descrizione:  <%=quoteRowFree.get(i).getDescription()%>   Quantit&agrave;: <%=quoteRowFree.get(i).getQuantity()%>   Sub-Totale:<%=quoteRowFree.get(i).getSubtot()%></p>
                        <a class="btn btn-warning btn-sm" href="javascript:deleteRowFree('<%=quoteRowFree.get(i).getDescription()%>','<%=quoteRowFree.get(i).getQuantity()%>','<%=quoteRowFree.get(i).getSubtot()%>');" >Elimina</a><br>
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
                                    <input type="hidden" name="id" value="<%=quote.getId()%>"/>
                                    <input type="hidden" name="status"/>
                                    <input type="hidden" name="id_product" value=""/>
                                    <input type="hidden" name="quantity" value=""/>
                                    <input type="hidden" name="price" value=""/>
                                    <input type="hidden" name="description" value=">"/>
                                    <input type="hidden" name="subtot" value=""/>
                                    <%if (!status.equals(
                                                "view")) {%>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:submitForm('Quote.jsp','view');" >Termina Inserimento</a>
                                    </div>
                                    <%} else {%>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:submitForm('QuoteInsert.jsp','modify');" >Modifica Preventivo </a>
                                    </div>
                                    <%}%>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:submitForm('QuoteRowProductInsert.jsp','insert');" >Inserimento/Modifica Righe con Prodotti</a>
                                    </div>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:submitForm('QuoteRowFreeInsert.jsp','insert');" >Inserimento/Modifica Righe Libere</a>
                                    </div>

                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:cancel();" >Annulla</a>
                                    </div>
                                </form>
                                <form role="form" name="cancelForm" action="Quote.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
                                </form>
                            </div>
                            <div class="col-lg-6">
                                <%if (!quote.getApproved()) {%>
                                <div class="form-group">
                                    <form role="form" name="approveFrom" action="QuoteDetails.jsp" method="post">
                                        <input type="hidden" name="status" value="approve">
                                        <input type="hidden" name="id" value="<%=quote.getId()%>">
                                        <input type="submit" class="btn btn-warning btn-lg btn-block" value="Preventivo approvato dal Cliente">
                                    </form>
                                </div>
                                <%} else {%>
                                <div class="form-group">
                                    <form role="form" name="generateInvoiceForm" action="InvoiceDetails.jsp" method="post">
                                        <input type="hidden" name="status" value="insertFromQuote">
                                        <input type="hidden" name="id_quote" value="<%=quote.getId()%>">
                                        <input type="submit" class="btn btn-primary btn-lg btn-block" value="Genera Fattura dal Preventivo">
                                    </form>
                                </div>
                                <div class="form-group">
                                    <form role="form" name="generateDdtForm" action="DdtDetails.jsp" method="post">
                                        <input type="hidden" name="status" value="insertFromQuote">
                                        <input type="hidden" name="id_quote" value="<%=quote.getId()%>">
                                        <input type="submit" class="btn btn-primary btn-lg btn-block" value="Genera DDT dal Preventivo">
                                    </form>
                                </div>
                                <%}%>
                                <div class="form-group">
                                    <form role="form" name="" action="DocumentPrinter.jsp" method="post" target="_blank">
                                        <input type="hidden" name="doctype" value="quote">
                                        <input type="hidden" name="id" value="<%=quote.getId()%>">
                                        <input type="submit" class="btn btn-primary btn-lg btn-block" value="Stampa Preventivo">
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <jsp:include page="Bottom.jsp" /> 

