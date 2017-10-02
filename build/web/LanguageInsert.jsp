<%@page import="BusinessLogic.Interface.LanguageInterface"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="LanguageBean" scope="page" class="BusinessFlow.LanguageBean" />
<jsp:setProperty name="LanguageBean" property="*" />
<%
    boolean loggedon = false;
    List<LanguageInterface> languageList = new ArrayList();
    Cookie[] cookies;
    cookies = request.getCookies();
    String status = null;
    if (LanguageBean.getId() == 0) {
        loggedon = LanguageBean.isLoggeOn(cookies);
        status = "insert";
    } else {
        status = "modify";
        loggedon = LanguageBean.getOne(cookies);
        languageList = LanguageBean.getAll();
    }
    if (LanguageBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (LanguageBean.getResult() == -2) {
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
        <title>Inserimento Lingua</title>
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

        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>
    </head>
    <body>
        <jsp:include page="Top.jsp" />
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Inserimento/Modifica di una lingua</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <form role="form" name="mainForm" action="Language.jsp" method="post">
                                    <input type="hidden" name="id" <%if (status.equals("modify")) {%>value="<%=languageList.get(0).getId()%>"<%}%>/><br/>
                                    <div class="form-group">
                                        <label>Nome:</label>
                                        <input class="form-control" type="text" name="name" <%if (status.equals("modify")) {%>value="<%=languageList.get(0).getName()%>"<%}%> size="20"/><br/>
                                        <p class="help-block">Inserire qui il nome della lingua.</p>
                                    </div>
                                    <input type="hidden" name="status"/>
                                    <a class="btn btn-default" href="javascript:cancel();">Annulla</a>
                                    <a class="btn btn-default" href="javascript:submitForm();">Conferma</a>
                                </form>

                                <form name="cancelForm" action="Language.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="Bottom.jsp" />
    
