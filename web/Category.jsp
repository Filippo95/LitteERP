<%@page import="BusinessLogic.Interface.LanguageInterface"%>
<%@page import="BusinessLogic.Interface.CategoryInterface"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>

<jsp:useBean id="CategoryBean" scope="page" class="BusinessFlow.CategoryBean" />
<jsp:setProperty name="CategoryBean" property="*" />
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
            loggedon = CategoryBean.categoryViewDelete(cookies);
        }
        if (status.equals("insert") || status.equals("insertCategoryAndTranslation")) {
            loggedon = CategoryBean.categoryViewInsert(cookies);
        }
        if (status.equals("modify") || status.equals("modifyCategoryAndTranslation")) {
            loggedon = CategoryBean.categoryViewUpdate(cookies);
        }
        if (status.equals("view")) {
            loggedon = CategoryBean.categoryViewList(cookies);
        }
    }
    if (CategoryBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (CategoryBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {%>

<jsp:forward page="index.jsp"/>
<% }
    List<CategoryInterface> categoryArray = CategoryBean.getAll();
    List<LanguageInterface> languages = CategoryBean.getLanguages();
%>
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Category View</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>

        <link href="css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">


        <!-- SB Admin CSS - Include with every page -->
        <link href="css/sb-admin.css" rel="stylesheet">
        <script language="javascript">
            function modifyCategory(code)
            {
                document.modifyForm.id.value = code;
                document.modifyForm.submit();
                return;
            }

            function insertCategory()
            {
                document.insertForm.submit();
                return;
            }

            function deleteCategory(code, hasChildren, hasProducts)
            {
                if (hasChildren == 'true') {
                    alert("Attenzione, questa categoria ha categorie figlie.\n\
        Se vuoi eliminarla prima sposta o elimina le figlie.");
                } else if (hasProducts == 'true') {
                    alert("Attenzione, questa categoria ha dei prodotti.\n\
        Se vuoi eliminarla prima sposta o elimina i prodotti corrispondenti.");
                } else {
                    document.deleteForm.id.value = code;
                    document.deleteForm.submit();
                    return;
                }
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
                <h1 class="page-header">Categorie</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <a class="btn btn-default" href="javascript:insertCategory();">Nuova Categoria</a>
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
                                        <th>Descrizione</th>
                                        <th>Categoria Padre</th>
                                            <% int j;
                                                for (j = 1; j < languages.size(); j++) {
                                            %>
                                        <th><%=languages.get(j).getName()%> - Nome</th>
                                        <th><%=languages.get(j).getName()%> -Descrizione</th>
                                            <%}%>
                                        <th>Azione</th>
                                        <th>Azione</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% int i;
                                        for (i = 0; i < categoryArray.size(); i++) {
                                    %><tr>
                                        <td><%=categoryArray.get(i).getTransList().get(0).getName()%></td>
                                        <td><%=categoryArray.get(i).getTransList().get(0).getDescription()%></td>
                                        <td><%=categoryArray.get(i).getParents()%> </td>
                                        <%  for (j = 1; j < languages.size(); j++) {
                                                if (categoryArray.get(i).getTransList().size() - 1 >= j) {%>

                                        <td><%=categoryArray.get(i).getTransList().get(j).getName()%></td>
                                        <td><%=categoryArray.get(i).getTransList().get(j).getDescription()%></td>
                                        <%} else {
                                        %>
                                        <td></td><td></td>
                                        <%
                                                }

                                            }%>

                                        <td><a class="btn btn-primary btn-sm btn-block" href="javascript:modifyCategory('<%=categoryArray.get(i).getId()%>');">Modifica</a></td>
                                        <td><a class="btn btn-warning btn-sm btn-block" href="javascript:deleteCategory('<%=categoryArray.get(i).getId()%>','<%=categoryArray.get(i).getHasChildren()%>','<%=categoryArray.get(i).getHasProducts()%>');">Elimina</a></td>
                                    </tr><%
                                        }%>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <form name="insertForm" action="CategoryParentChooser.jsp" method="post">
            <input type="hidden" name="status" value="view"/>
        </form>

        <form name="modifyForm" action="CategoryParentChooser.jsp" method="post">
            <input type="hidden" name="status" value="view"/>
            <input type="hidden" name="id" />
        </form>

        <form name="deleteForm" action="Category.jsp" method="post">
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

