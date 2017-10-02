
<%@page import="BusinessLogic.Interface.ProductInterface"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %><%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="ProductBean" scope="page" class="BusinessFlow.ProductBean" />
<jsp:setProperty name="ProductBean" property="*" />
<%
    boolean loggedon = false;

    Cookie[] cookies;
    cookies = request.getCookies();
    String status = "";
    if ((status = request.getParameter("status")) == null) {
        status = "view";
    }
    if (cookies != null) {
        if (status.equals("view")||status.equals("modify")||status.equals("insert")) {

            loggedon = ProductBean.list(cookies);
        }
        if (status.equals("delete")) {

            loggedon = ProductBean.delete(cookies);
        }
        
    }

    if (ProductBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (ProductBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {%>

<jsp:forward page="index.jsp"/>
<% }
    List<ProductInterface> productArray = ProductBean.getAll();

%>
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Product View</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>
        <link href="css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">


        <!-- SB Admin CSS - Include with every page -->
        <link href="css/sb-admin.css" rel="stylesheet">
        <script language="javascript">
            function detailsProduct(code)
            {
                document.detailsForm.id.value = code;
                document.detailsForm.submit();
                return;
            }
            function modifyProduct(code)
            {
                document.modifyForm.id.value = code;
                document.modifyForm.submit();
                return;
            }

            function insertProduct()
            {
                document.insertForm.submit();
                return;
            }

            function deleteProduct(code)
            {
                document.deleteForm.id.value = code;
                document.deleteForm.submit();
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
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Prodotti</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <a class="btn btn-default" href="javascript:insertProduct();">Nuovo Prodotto</a>
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
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover" id="table">
                                <thead>
                                    <tr>
                                        <th>Modello</th>
                                        <th>Categoria</th>
                                        <th>Marca</th>
                                        <th>stato</th>
                                        <th>prezzo</th>
                                        <th>Azione</th>
                                        <th>Azione</th>
                                        <th>Azione</th>
                                    </tr>
                                </thead>
                                <tbody >
                                    <% int i;
                                        for (ProductInterface product:productArray) {
                                    %><tr>
                                        <td><%=product.getModel()%></td>
                                        <td><%=product.getCategoryName()%></td>


                                        <td><%=product.getBrandName()%></td>

                                        <td><%=product.getState()%></td>

                                        <td><%=product.getPrice()%></td>

                                        <td><a class="btn btn-default btn-sm btn-block" href="javascript:detailsProduct('<%=product.getId()%>');">Visualizza</a></td>
                                        <td><a class="btn btn-primary btn-sm btn-block" href="javascript:modifyProduct('<%=product.getId()%>');">Modifica</a></td>
                                        <td><a class="btn btn-warning btn-sm btn-block" href="javascript:deleteProduct('<%=product.getId()%>');">Elimina</a></td>
                                    </tr><%
                                        }%>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <form name="detailsForm" action="ProductDetails.jsp" method="post">
            <input type="hidden" name="id" />
        </form>

        <form name="insertForm" action="ProductInsert.jsp" method="post">
            <input type="hidden" name="status" value="view"/>
        </form>

        <form name="modifyForm" action="ProductInsert.jsp" method="post">
            <input type="hidden" name="status" value="view"/>
            <input type="hidden" name="id" />
        </form>

        <form name="deleteForm" action="Product.jsp" method="post">
            <input type="hidden" name="status" value="delete"/>
            <input type="hidden" name="id"/>
        </form>

        <script src="js/plugins/dataTables/jquery.dataTables.js"></script>
        <script src="js/plugins/dataTables/dataTables.bootstrap.js"></script>
        <script>
            $(document).ready(function () {
                $('#table').dataTable();
            });
        </script>
        <jsp:include page="Bottom.jsp" /> 

