<%@page import="BusinessLogic.Interface.LanguageInterface"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="LanguageBean" scope="page" class="BusinessFlow.LanguageBean" />
<jsp:setProperty name="LanguageBean" property="*" />
<%
    boolean loggedon = false;

    Cookie[] cookies;
    cookies = request.getCookies();
    String status;
    if (request.getParameter("status") == null) {
        status = "view";
    } else {
        status = request.getParameter("status");
    }
    if (cookies != null) {
        if (status.equals("delete")) {

            loggedon = LanguageBean.delete(cookies);
        }
        if (status.equals("insert")) {
            loggedon = LanguageBean.insert(cookies);
        }
        if (status.equals("modify")) {
            loggedon = LanguageBean.update(cookies);
        }
        if (status.equals("view")) {
            loggedon = LanguageBean.list(cookies);
        }
    }
    if (LanguageBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (LanguageBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {%>

<jsp:forward page="index.jsp"/>
<% }
    List<LanguageInterface> languageArray = LanguageBean.getAll();

%>
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>


        <!-- SB Admin CSS - Include with every page -->
        <link href="css/sb-admin.css" rel="stylesheet">
        <title>Language View</title>
        <script language="javascript">
            function modifyLanguage(code)
            {
                document.modifyForm.id.value = code;
                document.modifyForm.submit();
                return;
            }

            function insertLanguage()
            {
                document.insertForm.submit();
                return;
            }

            function deleteLanguage(code)
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
                <h1 class="page-header">Lingue</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <a class="btn btn-default" href="javascript:insertLanguage();">Inserisci nuova lingua</a>
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
                                        <th>Nome</th>
                                        <th>Azione</th>
                                        <th>Azione</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% int i;
                                        for (LanguageInterface language:languageArray) {
                                    %><tr>
                                        <td><%=language.getName()%> </td>
                                        <td><a class="btn btn-primary btn-sm btn-block" href="javascript:modifyLanguage('<%=language.getId()%>');">Modifica</a></td>
                                        <td><a class="btn btn-warning btn-sm btn-block" href="javascript:deleteLanguage('<%=language.getId()%>');">Elimina</a></td>
                                    </tr><%
                                        }%>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <form name="insertForm" action="LanguageInsert.jsp" method="post">
            <input type="hidden" name="status" value="view"/>
        </form>

        <form name="modifyForm" action="LanguageInsert.jsp" method="post">
            <input type="hidden" name="status" value="view"/>
            <input type="hidden" name="id" />
        </form>

        <form name="deleteForm" action="Language.jsp" method="post">
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

