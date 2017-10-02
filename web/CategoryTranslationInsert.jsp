<%@page import="BusinessLogic.Interface.LanguageInterface"%>
<%@page import="BusinessLogic.Interface.CategoryInterface"%>
<%@page import="java.util.ArrayList"%>
<%@page import="BusinessLogic.VirtualEntities.VirtualCategory"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>

<jsp:useBean id="CategoryBean" scope="page" class="BusinessFlow.CategoryBean" />
<jsp:setProperty name="CategoryBean" property="*" />
<%
    boolean loggedon = false;
    List<CategoryInterface> categoryList = new <CategoryInterface>ArrayList();
    List<LanguageInterface> languageList = new <VirtualLanguage>ArrayList();
    Cookie[] cookies;
    cookies = request.getCookies();
    String status = null;
    if (request.getParameter("status") == null) {
%>
<jsp:forward page="Category.jsp"/>
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
    if (status.equals("insertCategory")) {
        //Non faccio niente, prendo solo la lista di lingue
        loggedon = CategoryBean.categoryInsertViewActionInsert(cookies);
        status = "insertCategoryAndTranslation";
    } else if (status.equals("modifyCategory")) {
        /*Prendo solo la losta di lingue e la categoria che devo modificare*/
        loggedon = CategoryBean.categoryInsertViewActionModify(cookies);
        categoryList = CategoryBean.getAll();
        if (categoryList.size() > languageNumber) {
            status = "modifyCategoryAndTranslation";
        } else {
            status = "insertCategoryAndTranslation";
        }
    } else {
        /*Inserisco la categoria nella tabella category insieme alla prima category_translation*/
        if (status.equals("insertCategoryAndTranslation")) {
            loggedon = CategoryBean.categoryInsertInsertCategory(cookies);
            status = "insert";
        } else if (status.equals("modifyCategoryAndTranslation")) {
            loggedon = CategoryBean.categoryInsertUpdateCategory(cookies);
            status = "modify";
            categoryList = CategoryBean.getAll();
        } else {
            //Inserisco la sola traduzione di una categoria già presente
            if (status.equals("insert")) {
                loggedon = CategoryBean.categoryInsertInsertTranslation(cookies);
                categoryList = CategoryBean.getAll();
            }
            if (status.equals("modify")) {
                loggedon = CategoryBean.categoryInsertUpdateTranslation(cookies);
                categoryList = CategoryBean.getAll();
                if (categoryList.size() > languageNumber) {
                    status = "modify";
                } else {
                    status = "insert";
                }
            }
        }
    }
    if (CategoryBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (CategoryBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {
%>

<jsp:forward page="index.jsp"/>
<%
    }
    languageNumber++;
    languageList = CategoryBean.getLanguages();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Category Insert</title>
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
        <script language="JavaScript" src="https://code.jquery.com/jquery-1.11.1.min.js" type="text/javascript"></script><link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

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
                <h5 class="page-header">Avrai la possibilit&agrave; di inserire i particolari delle categorie nelle lingue che hai definito in anticipo.</h5>
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
                                    <input type="hidden" name="id" value="<%=CategoryBean.getId()%>"/><br/>
                                    <div class="form-group">
                                        <label>Nome:</label>
                                        <input class="form-control" type="text" name="name" <%if (status.equals("modify") || status.equals("modifyCategoryAndTranslation")) {%>value="<%=categoryList.get(0).getTransList().get(languageNumber - 1).getName()%>"<%}%> size="20"/><br/>
                                        <p class="help-block">Inserire qui il nome della categoria in <%=languageList.get(languageNumber - 1).getName()%>.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Descrizione:</label>
                                        <input class="form-control" type="text" name="description" <%if (status.equals("modify") || status.equals("modifyCategoryAndTranslation")) {%>value="<%=categoryList.get(0).getTransList().get(languageNumber - 1).getDescription()%>"<%}%> size="100"/><br/>
                                        <p class="help-block">Inserire qui la descrizione della categoria in <%=languageList.get(languageNumber - 1).getName()%>.</p>
                                    </div>
                                    <%if (status.equals("insertCategoryAndTranslation") || status.equals("modifyCategoryAndTranslation")) {%>
                                    <input type="hidden" name="id_parent" value="<%=CategoryBean.getId_parent()%>"/>
                                    <%}%>
                                    <input type="hidden" name="id_lang" value="<%=languageList.get(languageNumber - 1).getId()%>">
                                    <input type="hidden" name="status"/>
                                    <input type="hidden" name="languageNumber" value="<%=languageNumber%>"/>
                                    <div class="form-group">
                                        <%if (languageNumber < languageList.size()) {%>
                                        <a class="btn btn-default btn-lg btn-block" href="javascript:submitForm('CategoryTranslationInsert.jsp');">Prossima Lingua</a>
                                        <%}%>
                                    </div>
                                    <div class="form-group">
                                        <a class="btn btn-warning" href="javascript:cancel();">Annulla</a>
                                        <a class="btn btn-primary" href="javascript:submitForm('Category.jsp');">Fine inserimento Traduzione Categorie</a>
                                    </div>
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
