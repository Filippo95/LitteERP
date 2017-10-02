<%@page import="BusinessLogic.Interface.PeopleInterface"%>
<%@page import="BusinessLogic.Interface.ProductInterface"%>
<%@page import="BusinessLogic.Interface.SupplyInterface"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="SupplyBean" scope="page" class="BusinessFlow.SupplyBean" />
<jsp:setProperty name="SupplyBean" property="*" />
<%
    boolean loggedon = false;
    List<SupplyInterface> supplyList = new ArrayList();
    Cookie[] cookies;
    cookies = request.getCookies();
    String status = null;
    if (cookies != null) {
        if (SupplyBean.getId() == 0) {
            loggedon = SupplyBean.SupplyInsertActionInsert(cookies);
            status = "insert";
        } else {
            status = "modify";
            loggedon = SupplyBean.SupplyInsertActionModify(cookies);
            supplyList = SupplyBean.getAll();
        }
    }
    if (SupplyBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (SupplyBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {
%>

<jsp:forward page="index.jsp"/>
<%
    }
    List<ProductInterface> products = SupplyBean.getProducts();
    List<PeopleInterface> providers = SupplyBean.getProviders();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Supply Insert</title>
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

            function selectedProduct(code, price, profit)
            {
                $("#id_product").attr('value', code);
                console.log("il valore di id_product è: " + code);
                $("#table_wrapper").remove();
                price = price - price * profit / 100;
                $("#p").html("<div class=\"alert alert-success\" role=\"alert\">il prodotto è stato selezionato correttamente</div>");
                $("#adPrice").html("<div class=\"alert alert-info\" role=\"alert\">Prezzo Consigliato: " + price + " <a class=\"btn btn-default btn-sm btn-block\" href=\"javascript:setPrice(" + price + ");\">Utilizza prezzo Consigliato</a></div>");
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
        <script language="JavaScript" src="https://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/3/dataTables.bootstrap.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

    </head>
    <body>

        <jsp:include page="Top.jsp" />

        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Inserimento/Modifica di una Fornitura</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <form role="form" name="mainForm" action="Supply.jsp" method="post">
                                    <input type="hidden" name="id" <%if (status.equals("modify")) {%>value="<%=supplyList.get(0).getId()%>"<%}%>/><br/>
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

                                                    <td><a class="btn btn-default btn-sm btn-block" href="javascript:selectedProduct('<%=products.get(i).getId()%>','<%=products.get(i).getPrice()%>','<%=products.get(i).getProfit()%>');">Seleziona</a></td>
                                                </tr><%
                                                    }%>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="form-group">
                                        <label>Seleziona il fornitore</label>
                                        <select name="id_provider" class="form-control">
                                            <%for (i = 0; i < providers.size(); i++) {%>
                                            <option  value="<%=providers.get(i).getId()%>" <%if (status.equals("modify")) {
                                                    if (providers.get(i).getId() == SupplyBean.getId_provider()) {%> selected="selected"<%}
                                                }%>><%=providers.get(i).getName()%></option>
                                            <%}%>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Quantit&agrave;</label>
                                        <input class="form-control" type="number" name="quantity" <%if (status.equals("modify")) {%>value="<%=supplyList.get(0).getQuantity()%>"<%}%> />
                                        <p class="help-block">Inserire qui la quantit&agrave; di prodotti.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Prezzo Unitario Prodotto</label>
                                        <input class="form-control" type="number" step="0.01" name="provider_unit_price" id="priceinput" <%if (status.equals("modify")) {%>value="<%=supplyList.get(0).getProvider_unit_price()%>"<%}%>/><div id="adPrice"></div>
                                        <p class="help-block">Inserire qui il prezzo unitario del prodotto.</p>
                                    </div>
                                    <input type="hidden" name="status"/>
                                    <a class="btn btn-default" href="javascript:submitForm();" >Conferma</a>
                                    <a class="btn btn-default" href="javascript:cancel();" >Annulla</a>
                                </form>
                                <form role="form" name="cancelForm" action="Supply.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
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

