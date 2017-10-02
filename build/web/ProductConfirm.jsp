<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="ProductBean" scope="page" class="BusinessFlow.ProductBean" />
<jsp:setProperty name="ProductBean" property="*" />
<%
    boolean loggedon = false;
    Cookie[] cookies;
    cookies = request.getCookies();
    String status = null;
    if (request.getParameter("status") == null) {
%>
<jsp:forward page="Product.jsp"/>
<%
    } else {
        status = request.getParameter("status");
    }
    if (status.equals("view")) {
        loggedon = ProductBean.getOne(cookies);
        status = "modify";
    }
    if (status.equals("insertProduct")) {
        loggedon = ProductBean.insert(cookies);
        status = "insert";
    }
    if (status.equals("modifyProduct")) {
        loggedon = ProductBean.update(cookies);
        status = "modify";
    }
    if (status.equals("insertDescription")) {
        loggedon = ProductBean.insertDescription(cookies);
        status = "insert";
    }
    if (status.equals("modifyDescription")) {
        loggedon = ProductBean.updateDescription(cookies);
        status = "modify";
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
        <title>Conferma Avvenuta Modifica</title>
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
        <script language="JavaScript" src="https://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/3/dataTables.bootstrap.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

    </head>
    <body>

        <jsp:include page="Top.jsp" />

        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Inserimento o modifica Effettuata!</h1>
            </div>
            <!-- /.col-lg-12 -->
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
                                <form role="form" name="mainForm" action="" method="get">
                                    <input type="hidden" name="id" value="<%=ProductBean.getId()%>"/>
                                    <input type="hidden" name="status"/>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:submitForm('Product.jsp');" >Termina Inserimento</a>
                                    </div>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:submitForm('DescriptionInsert.jsp');" >Inserimento/Modifica Descrizioni</a>
                                    </div>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:submitForm('ProductInsertImage.jsp');" >Inserimento/Modifica Immagini</a>
                                    </div>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:submitForm('DatasheetInsert.jsp');" >Inserimento/Modifica Datasheet</a>
                                    </div>
                                    <div class="form-group">
                                        <a class="btn btn-primary btn-lg btn-block" href="javascript:cancel();" >Annulla</a>
                                    </div>
                                </form>
                                <form role="form" name="cancelForm" action="Product.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
                                </form>
                                    <form role="form" name="" action="DocumentPrinter.jsp" method="post">
                                        <input type="hidden" name="doctype" value="product">
                                        <input type="hidden" name="id" value="<%=ProductBean.getId()%>">
                                        <input type="submit" class="btn btn-primary btn-lg btn-block" value="Stampa ">
                                    </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="Bottom.jsp" />


