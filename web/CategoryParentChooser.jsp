<%@page import="BusinessLogic.Interface.CategoryInterface"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>

<jsp:useBean id="CategoryBean" scope="page" class="BusinessFlow.CategoryBean" />
<jsp:setProperty name="CategoryBean" property="*" />
<%
    boolean loggedon = false;
    List<CategoryInterface> categoryList = new <CategoryInterface>ArrayList();
    Cookie[] cookies;
    cookies = request.getCookies();
    String status = null;
    if (CategoryBean.getId() == 0) {
        loggedon = CategoryBean.categoryViewList(cookies);
        status = "insertCategory";
    } else {
        status = "modifyCategory";
        loggedon = CategoryBean.categoryViewList(cookies);
    }
    if (CategoryBean.getResult()==-1){    
    throw new Exception("Errore nell'applicazione: consultare i logs.");
  } else if (CategoryBean.getResult()==-2) {
  }
    if (cookies == null || !loggedon) {
%>

<jsp:forward page="index.jsp"/>
<%
    }
    categoryList = CategoryBean.getAll();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
        <link href="css/sb-admin.css" rel="stylesheet">
        <title>Selezione Categoria Padre</title>
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
        <script language="JavaScript" src="https://code.jquery.com/jquery-1.11.1.min.js" type="text/javascript"></script>
        <script language="JavaScript" src="https://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js" type="text/javascript"></script>
        <script language="JavaScript" src="https://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/3/dataTables.bootstrap.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

    </head>
    <body>
        <jsp:include page="Top.jsp" />
        <% if (status.equals("modifyCategory")) {%>
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"><%=CategoryBean.getSavedCategory(CategoryBean.getId()).getTransList().get(0).getName()%></h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <%}%>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        Seleziona la Categoria padre
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <form role="form" name="mainForm" action="CategoryTranslationInsert.jsp" method="get">
                                    <input type="hidden" name="id" <%if (status.equals("modifyCategory")) {%>value="<%=CategoryBean.getId()%>"<%}%>/><br/>
                                    <input type="hidden" name="status"/>
                                    <div class="form-group">
                                        <label>Categoria Padre:</label>
                                        <% for (int i = 0; i < categoryList.size(); i++) {%>
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="id_parent" value="<%=categoryList.get(i).getId()%>" <%if (status.equals("modifyCategory") && categoryList.get(i).getId() == CategoryBean.getSavedCategory(CategoryBean.getId()).getId_parent()) {%> checked="checked" <%}%>><%=categoryList.get(i).getParents()%>
                                            </label>
                                        </div>
                                        <%}%>
                                    </div>
                                    <a class="btn btn-default" href="javascript:cancel();">Annulla</a>
                                    <a class="btn btn-default" href="javascript:submitForm();">Conferma</a>
                                </form>

                                <form name="cancelForm" action="Category.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="Bottom.jsp" />
