<%@page import="BusinessLogic.Interface.BrandInterface"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>

<jsp:useBean id="BrandBean" scope="page" class="BusinessFlow.BrandBean" />
<jsp:setProperty name="BrandBean" property="*" />
<%
    boolean loggedon = false;
    List<BrandInterface> brandList = new <BrandInterface>ArrayList();
    Cookie[] cookies;
    cookies = request.getCookies();
    String status = null;
    if (cookies != null) {
        if (BrandBean.getId() == 0) {
            loggedon = BrandBean.isLoggeOn(cookies);
            status = "insert";
        } else {
            status = "modify";
            loggedon = BrandBean.getOne(cookies);
            brandList = BrandBean.getAll();
        }
    }
    if (BrandBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (BrandBean.getResult() == -2) {
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
        <title>Brand Insert</title>
        
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
                <h1 class="page-header">Inserimento/Modifica di una marca</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <form role="form" name="mainForm" action="Brand.jsp" method="post">
                                    <input type="hidden" name="id" <%if (status.equals("modify")) {%>value="<%=brandList.get(0).getId()%>"<%}%>/><br/>
                                    <div class="form-group">
                                        <label>Nome:</label>
                                        <input class="form-control" type="text" name="name" <%if (status.equals("modify")) {%>value="<%=brandList.get(0).getName()%>"<%}%> size="20"/><br/>
                                        <p class="help-block">Inserire qui il nome della marca.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Descrizione:</label>
                                        <input class="form-control" type="text" name="description" <%if (status.equals("modify")) {%>value="<%=brandList.get(0).getDescription()%>"<%}%> size="100"/><br/>
                                        <p class="help-block">Inserire qui la descrizione della marca.</p>
                                    </div>
                                    <input type="hidden" name="status"/>
                                    <a class="btn btn-default" href="javascript:submitForm();" >Conferma</a>
                                    <a class="btn btn-default" href="javascript:cancel();" >Annulla</a>
                                </form>
                                <form role="form" name="cancelForm" action="Brand.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="Bottom.jsp" />

