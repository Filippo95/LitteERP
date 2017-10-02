<%@page import="BusinessLogic.Interface.BrandInterface"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="BrandBean" scope="page" class="BusinessFlow.BrandBean" />
<jsp:setProperty name="BrandBean" property="*" />
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
            loggedon = BrandBean.delete(cookies);
        }
        if (status.equals("insert")) {
            loggedon = BrandBean.insert(cookies);
        }
        if (status.equals("modify")) {
            loggedon = BrandBean.update(cookies);
        }
        if (status.equals("view")) {
            loggedon = BrandBean.list(cookies);
        }
    }
    if (BrandBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (BrandBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {%>

<jsp:forward page="index.jsp"/>
<% }
    List<BrandInterface> brandArray = BrandBean.getAll();

%>
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Brand View</title>
        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

        <link href="css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">


        <!-- SB Admin CSS - Include with every page -->
        <link href="css/sb-admin.css" rel="stylesheet">
        <script language="javascript">
            function modifyBrand(code)
            {
                document.modifyForm.id.value = code;
                document.modifyForm.submit();
                return;
            }

            function insertBrand()
            {
                document.insertForm.submit();
                return;
            }

            function deleteBrand(code, hasProducts)
            {
                if (hasProducts == 'true') {
                    alert("Attenzione! Esistono prodotti con marca che stai cancellando.\n\Per poter cancellare questa marca dovrai prima cancellare tutti i prodotti corrispondenti.");
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
                <h1 class="page-header">Marche</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <a class="btn btn-default" href="javascript:insertBrand();">Nuova Marca</a>
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
                                        <th>Azione</th>
                                        <th>Azione</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% int i;
                                        for (i = 0; i < brandArray.size(); i++) {
                                    %><tr class="even gradeA">
                                        <td class="center"><%=brandArray.get(i).getName()%> </td>
                                        <td class="center"><%=brandArray.get(i).getDescription()%></td>
                                        <td><a class="btn btn-primary btn-sm btn-block" href="javascript:modifyBrand('<%=brandArray.get(i).getId()%>');">Modifica</a></td>
                                        <td><a class="btn btn-warning btn-sm btn-block" href="javascript:deleteBrand('<%=brandArray.get(i).getId()%>','<%=brandArray.get(i).getHasProducts()%>');">Elimina</a></td>
                                    </tr><%
                                        }%>
                                </tbody>
                            </table>

                            <form name="insertForm" action="BrandInsert.jsp" method="post">
                                <input type="hidden" name="status" value="view"/>
                            </form>

                            <form name="modifyForm" action="BrandInsert.jsp" method="post">
                                <input type="hidden" name="status" value="view"/>
                                <input type="hidden" name="id" />
                            </form>

                            <form name="deleteForm" action="Brand.jsp" method="post">
                                <input type="hidden" name="status" value="delete"/>
                                <input type="hidden" name="id"/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>  
        <script>
            $(document).ready(function () {
                $('#table').dataTable();
            });
        </script>

        <jsp:include page="Bottom.jsp" /> 

