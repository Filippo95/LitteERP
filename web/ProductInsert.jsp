<%@page import="BusinessLogic.Interface.ProductInterface"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="ProductBean" scope="page" class="BusinessFlow.ProductBean" />
<jsp:setProperty name="ProductBean" property="*" />
<%
    boolean loggedon = false;
    ProductInterface product = null;
    Cookie[] cookies;
    cookies = request.getCookies();
    String status = null;
    if (cookies != null) {
        if (ProductBean.getId() == 0) {
            loggedon = ProductBean.isLoggedOn(cookies);
            status = "insertProduct";
        } else {
            status = "modifyProduct";
            loggedon = ProductBean.getOne(cookies);
            product = ProductBean.getAll().get(0);
        }
    }
    if (ProductBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (ProductBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {
%>

<jsp:forward page="index.jsp"/>
<%
    }

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Inserimento Prodotti</title>
        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>
        <script language="javascript">

            function submitForm()
            {
                if (document.mainForm.model.value.length == 0)
                {
                    alert("Inserire il modello del prodotto");
                } else
                if (!document.getElementById('r1').checked && !document.getElementById('r2').checked)
                {
                    alert("Selezionare lo stato del prodotto");
                } else if (document.mainForm.profit.value.length == 0)
                {
                    alert("Inserire il profitto che vuoi garantirti sul prodotto");
                } else if (document.mainForm.price.value.length == 0)
                {
                    alert("Inserire il profitto che vuoi garantirti sul prodotto");
                } else
                {
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
        <script language="JavaScript" src="https://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js" type="text/javascript"></script>
        <script language="JavaScript" src="https://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/3/dataTables.bootstrap.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

    </head>
    <body>

        <jsp:include page="Top.jsp" />

        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Inserimento/Modifica di Prodotto</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <form role="form" name="mainForm" action="ProductConfirm.jsp" method="post">
                                    <input type="hidden" name="id" value="<%=ProductBean.getId()%>"/>
                                    <div class="form-group">
                                        <label>Modello</label>
                                        <input class="form-control" type="text" name="model" <%if (status.equals("modifyProduct")) {%>value="<%=product.getModel()%>"<%}%> size="20"/><br/>
                                        <p class="help-block">Inserire qui il modello</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Categoria</label>
                                        <select class="form-control" name="id_category">
                                            <%for (int i = 0; i < ProductBean.getAllCategories().size(); i++) {%>
                                            <option  value="<%=ProductBean.getAllCategories().get(i).getId()%>" <%if (status.equals("modifyProduct") && ProductBean.getAllCategories().get(i).getId() == product.getId_category()) {%>selected="selected"<%}%>><%=ProductBean.getAllCategories().get(i).getParents()%></option>
                                            <%}%>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Marca</label>
                                        <select class="form-control" name="id_brand">
                                            <% for (int i = 0; i < ProductBean.getAllBrand().size(); i++) {%>
                                            <option  value="<%=ProductBean.getAllBrand().get(i).getId()%>" <%if (status.equals("modifyProduct") && ProductBean.getAllBrand().get(i).getId() == product.getId_brand()) {%>selected="selected"<%}%>><%=ProductBean.getAllBrand().get(i).getName()%></option>
                                            <%}%>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Unità</label>
                                        <select class="form-control" name="id_unit">
                                            <%
                                                for (int i = 0; i < ProductBean.getAllUnit().size(); i++) {
                                            %>
                                            <option  value="<%=ProductBean.getAllUnit().get(i).getId()%>" <%if (status.equals("modifyProduct") && ProductBean.getAllUnit().get(i).getId() == product.getId_unit()) {%>selected="selected"<%}%>><%=ProductBean.getAllUnit().get(i).getName()%></option>
                                            <%}%>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Stato</label>
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="state" value="new" id="r1" <%if (status.equals("modifyProduct")) {
                                                           if (product.getState().equals("new")) {%>checked="checked"<%}
                                                               }%>>Nuovo
                                            </label>
                                        </div>
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="state" value="used" id="r2" <%if (status.equals("modifyProduct")) {
                                                           if (product.getState().equals("used")) {%>checked="checked"<%}
                                                               }%>>Usato
                                            </label>
                                        </div>
                                    </div>  
                                    <div class="form-group">
                                        <label>Tags</label>
                                        <input class="form-control" type="text" name="tags" <%if (status.equals("modifyProduct")) {%>value="<%=product.getTags()%>"<%}%> size="20"/><br/>
                                        <p class="help-block">Inserire qui i tags di ricerca</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Note</label>
                                        <input class="form-control" type="text" name="note" <%if (status.equals("modifyProduct")) {%>value="<%=product.getNote()%>"<%}%> size="20"/><br/>
                                        <p class="help-block">Inserire qui le note</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Posizione magazzino </label>
                                        <input class="form-control" type="text" name="wharehouse_position" <%if (status.equals("modifyProduct")) {%>value="<%=product.getWharehouse_position()%>"<%}%> size="20"/><br/>
                                        <p class="help-block">Inserire qui la posizione di magazzino</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Peso</label>
                                        <input class="form-control" type="number" step="0.2" name="weight" <%if (status.equals("modifyProduct")) {%>value="<%=product.getWeight()%>"<%}%> size="20"/><br/>
                                        <p class="help-block">Inserire qui Il peso </p>
                                    </div>

                                    <div class="form-group">
                                        <label>Ordine Massimo </label>
                                        <input class="form-control" type="number" name="max_order" <%if (status.equals("modifyProduct")) {%>value="<%=product.getMax_order()%>"<%}%> size="20"/><br/>
                                        <p class="help-block">Inserire qui la posizione di magazzino</p>
                                    </div><div class="form-group">
                                        <label>Ordine Minimo</label>
                                        <input class="form-control" type="number" name="min_order" <%if (status.equals("modifyProduct")) {%>value="<%=product.getMin_order()%>"<%}%> size="20"/><br/>
                                        <p class="help-block">Inserire qui l'ordine minino</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Profitto </label>
                                        <input class="form-control" type="number" step="0.01" name="profit" <%if (status.equals("modifyProduct")) {%>value="<%=product.getProfit()%>"<%}%> size="20"/><br/>
                                        <p class="help-block">Inserire qui l'aliquota di profitto</p>

                                    </div>
                                    <label>Prezzo </label>
                                    <div class="form-group input-group">
                                        <span class="input-group-addon"><i class="fa fa-eur"></i>
                                        </span>
                                        <input class="form-control" type="number" step="0.02" name="price" <%if (status.equals("modifyProduct")) {%>value="<%=product.getPrice()%>"<%}%> size="20"/><br/>
                                    </div><p class="help-block">Inserire qui il prezzo</p>
                                    <div class="form-group">
                                        <label>Aliquota IVA</label>
                                        <select class="form-control" name="iva">
                                            <option  value="3" <%if (status.equals("modifyProduct") && product.getIva() == 3) {%>selected="selected"<%}%>>3%</option>
                                            <option  value="10" <%if (status.equals("modifyProduct") && product.getIva() == 10) {%>selected="selected"<%}%>>10%</option>
                                            <option  value="19" <%if (status.equals("modifyProduct") && product.getIva() == 19) {%>selected="selected"<%}%>>19%</option>
                                            <option  value="22" <%if (status.equals("modifyProduct") && product.getIva() == 22) {%>selected="selected"<%}%>>22%</option>
                                            <option  value="25" <%if (status.equals("modifyProduct") && product.getIva() == 25) {%>selected="selected"<%}%>>25%</option>
                                        </select>
                                        <p class="help-block">Inserire qui l'aliquota IVA sul prodotto</p>
                                    </div>
                                    <input type="hidden" name="status"/>
                                    <a class="btn btn-default" href="javascript:submitForm();" >Conferma</a>
                                    <a class="btn btn-default" href="javascript:cancel();" >Annulla</a>
                                </form>
                                <form role="form" name="cancelForm" action="Product.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="Bottom.jsp" />
