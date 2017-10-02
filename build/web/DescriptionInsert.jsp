<%@page import="BusinessLogic.Interface.LanguageInterface"%>
<%@page import="BusinessLogic.Interface.DescriptionInterface"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="ProductBean" scope="page" class="BusinessFlow.ProductBean" />
<jsp:setProperty name="ProductBean" property="*" />
<%
    boolean loggedon = false;
    List<DescriptionInterface> descriptions = new ArrayList();
    List<LanguageInterface> languageList = new ArrayList();
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
    int languageNumber;
    if (request.getParameter("languageNumber") == null) {
        languageNumber = 0;
    } else {
        languageNumber = Integer.parseInt(request.getParameter("languageNumber"));
    }
    if (status.equals("insert")) {
        loggedon = ProductBean.isLoggedOn(cookies);
        status = "insertDescription";
    } else if (status.equals("modify")) {
        loggedon = ProductBean.getOne(cookies);
        descriptions = ProductBean.getDescriptions();
        if (descriptions.size() > languageNumber) {
            status = "modifyDescription";
        } else {
            status = "insertDescription";
        }
    } else {
        if (status.equals("insertDescription")) {
            loggedon = ProductBean.insertDescription(cookies);
        } else if (status.equals("modifyDescription")) {
            loggedon = ProductBean.updateDescription(cookies);
            descriptions = ProductBean.getDescriptions();
            if (descriptions.size() > languageNumber) {
                status = "modifyDescription";
            } else {
                status = "insertDescription";
            }
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
    languageNumber++;
    languageList = ProductBean.getLanguages();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Description Insert</title>
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
                <h1 class="page-header"><%=languageList.get(languageNumber - 1).getName()%></h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <h5 class="page-header">Attenzione!</h5>
                <h5 class="page-header">Avrai la possibilit&agrave; di inserire le descrizioni dei prodotti nelle lingue che hai definito in anticipo.</h5>
                <h5 class="page-header">Se vuoi modificare le lingue vai a: <a href="Language.jsp">Gestione Lingue</a></h5>
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
                                    <input type="hidden" name="id" value="<%=ProductBean.getId()%>"/><br/>
                                    <div class="form-group">
                                        <label>Descrizione:</label>
                                        <input type='text' class="form-control" rows="5" name="description" <%if (status.equals("modify") || status.equals("modifyDescription")) {%>value="<%=descriptions.get(languageNumber - 1).getDescription()%>"<%}%>/>
                                        <p class="help-block">Inserire qui la descrizione del prodotto <%=languageList.get(languageNumber - 1).getName()%>.</p>
                                    </div>
                                    <input type="hidden" name="id_lang" value="<%=languageList.get(languageNumber - 1).getId()%>">
                                    <input type="hidden" name="status"/>
                                    <input type="hidden" name="languageNumber" value="<%=languageNumber%>"/>
                                    <div class="form-group">
                                        <%if (languageNumber < languageList.size()) {%>
                                        <a class="btn btn-default btn-lg btn-block" href="javascript:submitForm('DescriptionInsert.jsp');">Prossima Lingua</a>
                                        <%}%>
                                    </div>
                                    <div class="form-group">
                                        <a class="btn btn-warning" href="javascript:cancel();">Annulla</a>
                                        <a class="btn btn-primary" href="javascript:submitForm('ProductConfirm.jsp');">Fine inserimento descrizioni</a>
                                    </div>
                                </form>

                                <form name="cancelForm" action="Product.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="Bottom.jsp" />
