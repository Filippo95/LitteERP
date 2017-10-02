
<%@page import="BusinessLogic.Interface.DescriptionInterface"%>
<%@page import="BusinessLogic.Interface.LanguageInterface"%>
<%@page import="BusinessLogic.Interface.ProductInterface"%>
<%@page import="java.util.List"%>
<%@page import="sun.misc.BASE64Encoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="ProductBean" scope="page" class="BusinessFlow.ProductBean" />
<jsp:setProperty name="ProductBean" property="*" />
<%
    boolean loggedon = false;

    Cookie[] cookies;
    cookies = request.getCookies();

    if (cookies != null) {
        loggedon = ProductBean.getOne(cookies);
    }
    if (ProductBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (ProductBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {%>

<jsp:forward page="index.jsp"/>
<% }
    ProductInterface product = ProductBean.getAll().get(0);
    List<LanguageInterface> languages = ProductBean.getLanguages();
    List<DescriptionInterface> descriptions = ProductBean.getDescriptions();
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
        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>
        <title>Product View</title>
        <script language="javascript">
            function cancel()
            {
                document.cancelForm.submit();
                return;
            }
            function modify()
            {
                document.modifyForm.submit();
                return;
            }

        </script>

        <script language="JavaScript" src="https://code.jquery.com/jquery-1.11.1.min.js" type="text/javascript"></script>
        <script language="JavaScript" src="https://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js" type="text/javascript"></script>
        <script language="JavaScript" src="https://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/3/dataTables.bootstrap.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>
    </head>
    <body>
        <jsp:include page="Top.jsp" />

        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Dettagli del prodotto</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <h1></h1>
            </div>
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h3>Modello</h3>
                        <p><%=product.getBrandName() %>,  <%=product.getModel()%></p>
                        <h3>Categoria</h3>
                        <p><%=product.getCategoryName()%></p>
                        <%for (int i = 0; i < languages.size(); i++) {%>
                        <h3>Descrizione (<%=languages.get(i).getName()%>):</h3>
                        <p><%if(descriptions.size()!=0){%><%=descriptions.get(i).getDescription()%><%}%></p>
                        <%}%>
                        <h3>Stato</h3>
                        <p><%=product.getState()%></p>
                        <h3>Unit&agrave; di misura</h3>
                        <p><%=product.getUnitName()%></p>
                        <h3>Tags</h3>
                        <p><%=product.getTags()%></p>
                        <h3>Note</h3>
                        <p><%=product.getNote()%></p>
                    </div>
                </div>
            </div>
            <div class="col-lg-6"><div class="panel panel-default">
                    <div class="panel-body">
                        <h3>Posizione nel Magazzino</h3>
                        <p><%=product.getWharehouse_position()%></p>
                        <h3>Peso</h3>
                        <p><%=product.getWeight()%></p>
                        <h3>Ordine Minimo</h3>
                        <p><%=product.getMin_order()%></p>
                        <h3>Ordine Massimo</h3>
                        <p><%=product.getMax_order()%></p>
                        <h3>Profitto</h3>
                        <p><%=product.getProfit()%></p>
                        <h3>Prezzo</h3>
                        <p><%=product.getPrice()%></p>
                        <h3>Aliquota IVA</h3>
                        <p><%=product.getIva()%></p>
                        <h3>Immagini</h3>
                        <% for (int i = 0; i < ProductBean.getAllImages().size(); i++) {%>
                        <%
                            if (ProductBean.getAllImages().get(i).getId_product() == ProductBean.getId()) {

                                BASE64Encoder base64Encoder = new BASE64Encoder();
                                StringBuilder imageString = new StringBuilder();
                                imageString.append("data:image/png;base64,");
                                imageString.append(base64Encoder.encode(ProductBean.getAllImages().get(i).getContent()));
                                String image = imageString.toString();
                        %>
                        <img src="<%=image%>" class="img-thumbnail" alt="" width="304" height="236">
                        <%}
                            }%>
                        <h3>Datasheets</h3>
                        <% for (int i = 0; i < ProductBean.getAllDatasheet().size(); i++) {%>
                        <%
                            if (ProductBean.getAllDatasheet().get(i).getId_product() == ProductBean.getId()) {

                                System.out.println("if ok ");

                        %>
                        <span >
                            <h1><%=i%></h1>
                            <form method="post" action="DatasheetInsert.jsp">
                                <input type="hidden" name="id" value="<%=ProductBean.getId()%>">
                                <input type="hidden" name="id_img" value="<%=ProductBean.getAllDatasheet().get(i).getId()%>">
                                <input type="hidden" name="show" value="<%=ProductBean.getId()%>">
                                <button type="submit">Download!</button>
                            </form>

                        </span>
                        <%
                                }
                                
                            }%>

                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <a href="javascript:cancel();" class="btn btn-primary btn-lg btn-block">Indietro</a>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <a href="javascript:modify();" class="btn btn-default btn-lg btn-block">Modifica</a>
            </div>
        </div>
        <form name="cancelForm" action="Product.jsp" method="post">
            <input type="hidden" name="status" value="view"/>
        </form>
        <form name="modifyForm" action="ProductInsert.jsp" method="post" target="_blank">
            <input type="hidden" name="status" value="modify"/>
            <input type="hidden" name="id" value="<%=ProductBean.getId()%>"/>
        </form>

        <jsp:include page="Bottom.jsp" /> 