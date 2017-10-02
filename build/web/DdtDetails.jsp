<%@page import="BusinessLogic.Interface.DdtInterface"%>
<%@page import="BusinessLogic.Interface.DdtRowInterface"%>
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
    List<DdtRowInterface> ddtRowFree;
    List<DdtRowInterface> ddtRowProduct;
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
            loggedon = DdtBean.getOne(cookies);
        } else if (status.equals("viewFromRelatedInvoice")) {
            loggedon = DdtBean.getOneByIdDrain(cookies);
            status = "view";
        } else {
            if (status.equals("insertDdt")) {
                loggedon = DdtBean.insert(cookies);
            }
            if (status.equals("modifyDdt")) {
                loggedon = DdtBean.update(cookies);
            }
            if (status.equals("insertDdtRowFree")) {
                loggedon = DdtBean.insertRow(cookies, "rowfree");
            }
            if (status.equals("insertDdtRowProduct")) {
                loggedon = DdtBean.insertRow(cookies, "rowproduct");
            }
            if (status.equals("deleteRowFree")) {
                loggedon = DdtBean.deleteRow(cookies, "rowfree");
            }
            if (status.equals("deleteRowProduct")) {
                loggedon = DdtBean.deleteRow(cookies, "rowproduct");
            }
            if(status.equals("insertFromQuote")){
                loggedon=DdtBean.insertFromQuote(cookies);
            }
            status = "insert";
        }
    }
    if (DdtBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (DdtBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {%>

<jsp:forward page="index.jsp"/>
<% }
    DdtInterface ddt = DdtBean.getAll().get(0);
    ddtRowFree = DdtBean.getDdtRowsFree();
    ddtRowProduct = DdtBean.getDdtRowsProduct();
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
        <title>Ddt Details View</title>
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
                document.mainForm.unit_price.value = unit_price;
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
                <h1 class="page-header">Dettagli del Documento di Trasporto</h1>
            </div>
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h3>Data</h3>
                        <% GregorianCalendar cal = ddt.getDdate();
                            int day = cal.get(Calendar.DAY_OF_MONTH);
                            int month = cal.get(Calendar.MONTH);
                            int year = cal.get(Calendar.YEAR);
                            String date = day + "-" + (month + 1) + "-" + year;%>
                        <p><%=date%></p>
                        <h3>Cliente</h3>
                        <p><%=ddt.getClientName()%></p>
                        <h3>Vettore</h3>
                        <p><%if(ddt.getId_people_vett()!=0){%><%=ddt.getVettName() %>
                            <%}else{%><%=""%><%}%></p>
                        <h3>Indirizzo di Spedizione</h3>
                        <p><%=ddt.getDestination()%></p>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h3>Causa</h3>
                        <p><%=ddt.getCause()%></p>
                        <h3>Aspetto</h3>
                        <p><%=ddt.getAspect()%></p>
                        <h3>Numero Colli</h3>
                        <p><%=ddt.getNcoll()%></p> 
                        <!--TODO: Mostrare le varie righe!-->
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3>Righe del Ddt</h3>
                    </div>
                    <div class="panel-body">
                        <%for (int i = 0; i < ddtRowProduct.size(); i++) {%>
                        <br><p>Prodotto:  <%=ddtRowProduct.get(i).getProductModel() %>   Quantit&agrave;: <%=ddtRowProduct.get(i).getQuantity()%>   Prezzo: <%=ddtRowProduct.get(i).getPrice()%></p>
                        <a class="btn btn-warning btn-sm" href="javascript:deleteRowProduct('<%=ddtRowProduct.get(i).getId_product()%>','<%=ddtRowProduct.get(i).getQuantity()%>','<%=ddtRowProduct.get(i).getPrice()%>');" >Elimina</a><br>
                        <%}%>
                        <%for (int i = 0; i < ddtRowFree.size(); i++) {%>
                        <br><p>Descrizione:  <%=ddtRowFree.get(i).getDescription()%>   Quantit&agrave;: <%=ddtRowFree.get(i).getQuantity()%>   Prezzo: <%=ddtRowFree.get(i).getUnit_price()%></p>
                        <a class="btn btn-warning btn-sm" href="javascript:deleteRowFree('<%=ddtRowFree.get(i).getDescription()%>','<%=ddtRowFree.get(i).getQuantity()%>','<%=ddtRowFree.get(i).getUnit_price()%>');" >Elimina</a><br>
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
                                    <input type="hidden" name="id" value="<%=ddt.getId()%>"/>
                                    <input type="hidden" name="id_drain" value="<%=ddt.getId_drain()%>">
                                    <input type="hidden" name="status"/>
                                    <input type="hidden" name="id_product" value=""/>
                                    <input type="hidden" name="quantity" value=""/>
                                    <input type="hidden" name="price" value=""/>
                                    <input type="hidden" name="description" value=">"/>
                                    <input type="hidden" name="unit_price" value=""/>
                                    <%if (!status.equals("view")) {%>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:submitForm('Ddt.jsp','view');" >Termina Inserimento</a>
                                    </div>
                                    <%} else {%>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:submitForm('DdtInsert.jsp','modify');" >Modifica Documento di Trasporto</a>
                                    </div>
                                    <%}%>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:submitForm('DdtRowProductInsert.jsp','insert');" >Inserimento/Modifica Righe con Prodotti</a>
                                    </div>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:submitForm('DdtRowFreeInsert.jsp','insert');" >Inserimento/Modifica Righe Libere</a>
                                    </div>

                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:cancel();" >Annulla</a>
                                    </div>
                                </form>
                                <form role="form" name="cancelForm" action="Ddt.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
                                </form>
                            </div>
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <form role="form" name="relatedDrainForm" action="DrainDetails.jsp" method="post">
                                        <input type="hidden" name="status" value="view">
                                        <input type="hidden" name="id" value="<%=ddt.getId_drain()%>">
                                        <input type="submit" class="btn btn-primary btn-lg btn-block" value="Vai allo scarico corrispondente">
                                    </form>
                                </div>
                                <%if (DdtBean.getExistInvoice()) {%>
                                <div class="form-group">
                                    <form role="form" name="relatedInvoiceForm" action="InvoiceDetails.jsp" method="post">
                                        <input type="hidden" name="status" value="viewFromRelatedDdt">
                                        <input type="hidden" name="id_drain" value="<%=ddt.getId_drain()%>">
                                        <input type="submit" class="btn btn-primary btn-lg btn-block" value="Vai alla fattura corrispondente">
                                    </form>
                                </div>
                                <%} else {%>
                                <div class="form-group">
                                    <form role="form" name="relatedInvoiceForm" action="InvoiceDetails.jsp" method="post">
                                        <input type="hidden" name="status" value="insertFromRelatedDdt">
                                        <input type="hidden" name="id_drain" value="<%=ddt.getId_drain()%>">
                                        <input type="submit" class="btn btn-primary btn-lg btn-block" value="Genera Fattura">
                                    </form>
                                </div>
                                <%}%>
                                <div class="form-group">
                                    <form role="form" name="" action="DocumentPrinter.jsp" method="post" target="_blank">
                                        <input type="hidden" name="doctype" value="ddt">
                                        <input type="hidden" name="id" value="<%=ddt.getId()%>">
                                        <input type="submit" class="btn btn-primary btn-lg btn-block" value="Stampa documento di trasporto">
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <jsp:include page="Bottom.jsp" /> 