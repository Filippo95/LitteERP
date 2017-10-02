<%@page import="BusinessLogic.Interface.ProductInterface"%>
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
    List<ProductInterface> products;
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
        status = "insertQuoteRowProduct";
    } else {
        if (status.equals("insertQuoteRowProduct")) {
            loggedon = QuoteBean.insertRow(cookies, "rowproduct");
        }
        if (status.equals("insertQuoteRowFree")) {
            loggedon = QuoteBean.insertRow(cookies, "rowfree");
            status = "insertQuoteRowProduct";
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
    products = QuoteBean.getProducts();
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
                if (document.mainForm.id_product.value.length == 0)
                {
                    alert("Attenzione, selezionare un prodotto");
                } else if (document.mainForm.quantity.value.length == 0)
                {
                    alert("Attenzione, inserire la quantità");
                } else if (document.mainForm.price.value.length == 0)
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

            function selectedProduct(code, price, quantity)
            {
                $("#id_product").attr('value', code);
                console.log("il valore di id_product è: " + code);
                $("#table_wrapper").remove();
                $("#p").html("<div class=\"alert alert-success\" role=\"alert\">il prodotto è stato selezionato correttamente</div>");
                $("#adPrice").html("<div class=\"alert alert-info\" role=\"alert\">Prezzo da Anagrafe: " + price + " <a class=\"btn btn-default btn-sm btn-block\" href=\"javascript:setPrice(" + price + ");\">Utilizza prezzo da Anagrafe</a></div>");
                $("#quantityWarning").html("<div class=\"alert alert-info\" role=\"alert\">Quantit&agrave; presente in magazzino: " + quantity + "</div>");
            }

            function setPrice(price)
            {

                $("#priceinput").attr('value', price);
            }
        </script>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
        <link href="css/sb-admin.css" rel="stylesheet">
        <script language="JavaScript" src="https://code.jquery.com/jquery-1.11.1.min.js" type="text/javascript"></script>
        <script language="JavaScript" src="https://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
        <script language="JavaScript" src="https://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/3/dataTables.bootstrap.js" type="text/javascript"></script>

    </head>
    <body>
        <jsp:include page="Top.jsp" />
        <div class="row">
            <div class="col-lg-12">
                <h3 class="page-header">Stai inserendo un prodotto nel preventivo:</h3>
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
                                    <input type="hidden" name="id" value="<%=QuoteBean.getId()%>"/>
                                    <div class="form-group">
                                        <label>Seleziona il prodotto</label>
                                        <div id="p"></div>
                                        <input type="hidden" name="id_product" id="id_product" >
                                        <table class="table table-striped table-bordered table-hover" id="table">
                                            <thead>
                                                <tr>
                                                    <th>Modello</th>
                                                    <th>Categoria</th>
                                                    <th>Marca</th>
                                                    <th>stato</th>
                                                    <th>prezzo</th>
                                                    <th>Azione</th>

                                                </tr>
                                            </thead>
                                            <tbody >
                                                <% int i;
                                                    for (i = 0; i < products.size(); i++) {
                                                %><tr>
                                                    <td><%=products.get(i).getModel()%></td>
                                                    <td><%=products.get(i).getCategoryName()%></td>
                                                    <td><%=products.get(i).getBrandName()%></td>

                                                    <td><%=products.get(i).getState()%></td>

                                                    <td><%=products.get(i).getPrice()%></td>

                                                    <td><a class="btn btn-default btn-sm btn-block" href="javascript:selectedProduct('<%=products.get(i).getId()%>','<%=products.get(i).getPrice()%>','<%=products.get(i).getCurrentlyPresentNumber()%>');">Seleziona</a></td>
                                                </tr><%
                                                    }%>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="form-group">
                                        <label>Quantit&agrave;</label>
                                        <input type='number' class="form-control" name="quantity"/>
                                        <div id="quantityWarning"></div>
                                        <p class="help-block">Inserire qui la quantit&agrave;.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Prezzo:</label>
                                        <input type='number' step="0.01" class="form-control" id="priceinput" name="price"/><div id="adPrice"></div>
                                        <p class="help-block">Inserire qui il prezzo.</p>
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
        <script>
            $(document).ready(function () {
                $('#table').dataTable();
            });
        </script>
        <jsp:include page="Bottom.jsp" />
