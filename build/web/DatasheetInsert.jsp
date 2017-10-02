
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="sun.misc.BASE64Encoder"%>
<%@page import="org.apache.tomcat.util.http.fileupload.util.Streams"%>
<%@page import="org.apache.tomcat.util.http.fileupload.FileUploadException"%>
<%@page import="org.apache.tomcat.util.http.fileupload.FileItemStream"%>
<%@page import="org.apache.tomcat.util.http.fileupload.FileItemIterator"%>
<%@page import="java.io.InputStream"%>
<%@page import="org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.tomcat.util.http.fileupload.FileItem"%>
<%@page import="org.apache.tomcat.util.http.fileupload.FileItem"%>
<%@page import="org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload"%>
<%@page import="java.io.DataInputStream"%>
<%@page import="java.util.ArrayList"%>
<%@page import="BusinessLogic.VirtualEntities.VirtualProduct"%>
<%@page import="java.util.List"%>
<%@ page errorPage="/ErrorPage.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<jsp:useBean id="ProductBean" scope="page" class="BusinessFlow.ProductBean" />
<jsp:setProperty name="ProductBean" property="*" />
<%
    boolean loggedon = false;
    List<VirtualProduct> productList = new <VirtualProduct>ArrayList();
    Cookie[] cookies;
    cookies = request.getCookies();

    int IDProduct = 0;
    if (cookies != null) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {
            System.out.println("Ok so che è multipart ");
            try {
                ServletFileUpload upload = new ServletFileUpload();

                FileItemIterator iterator = upload.getItemIterator(request);

                while (iterator.hasNext()) {
                    FileItemStream item = iterator.next();

                    InputStream stream = item.openStream();

                    if (item.isFormField()) {

                        IDProduct = Integer.parseInt(Streams.asString(stream));
                        System.out.println("l'id ora ce l'ho ed è: " + IDProduct);
                    } else {
                        //setto le proprietà del bean
                        ProductBean.setId(IDProduct);
                        String filename = item.getContentType();
                        String[] contentType = filename.split("/");
                        ProductBean.setContent_type(contentType[1]);
                        System.out.println("Il content type che ho trovaro è: ");
                        byte[] buffer = new byte[8192];
                        byte[] content;
                        int bytesRead;
                        ByteArrayOutputStream output = new ByteArrayOutputStream();
                        while ((bytesRead = stream.read(buffer)) != -1) {
                            output.write(buffer, 0, bytesRead);
                        }
                        content = output.toByteArray();

                        ProductBean.setContent(content);

                        System.out.println("ho settato le property nel bean ");
                    }

                }
                loggedon = ProductBean.insertDatasheet(cookies);
                if (loggedon) {
                    System.out.println("il bean ritorna true");
                } else {

                    System.out.println("il bean ritorna false");
                }
                System.out.println("ho inserto");
            } catch (FileUploadException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {

            System.out.println("Non è multipart, cerco l'id ");
            IDProduct = Integer.parseInt(request.getParameter("id"));
            ProductBean.setId(IDProduct);

            System.out.println("HO l'id " + IDProduct);
            if (request.getParameter("id_img") != null) {
                if (request.getParameter("show") != null) {
                    loggedon = ProductBean.isLoggedOn(cookies);
                    byte[] Content = null;
                    for (int i = 0; i < ProductBean.getAllDatasheet().size(); i++) {
                        if (ProductBean.getAllDatasheet().get(i).getId() == Integer.parseInt(request.getParameter("id_img"))) {
                            Content = ProductBean.getAllDatasheet().get(i).getContent();
                            String filename = ProductBean.getAllDatasheet().get(i).getId() + "." + ProductBean.getAllDatasheet().get(i).getFile_format();

                            response.setContentType(ProductBean.getAllDatasheet().get(i).getFile_format());
                            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                            response.getOutputStream().write(Content);
                            response.getOutputStream().close();
                        }
                    }

                } else {
                    loggedon = ProductBean.isLoggeOn(cookies, Integer.parseInt(request.getParameter("id_img")), 2);
                }

            } else {
                loggedon = ProductBean.isLoggedOn(cookies);
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

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Datasheet Insert</title>
        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>
        <script language="javascript">

            function submitForm()
            {
                document.mainForm.submit();
                return;
            }

            function stopForm()
            {
                document.stop.submit();
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
                <h1 class="page-header">Carica Datasheet per Prodotto</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <%                                    String Modello = "", Note = "", Tags = "", Marca = "", Categoria = "";
                                    Double Prezzo = 0.0;
                                    for (int i = 0; i < ProductBean.getAll().size(); i++) {
                                        if (ProductBean.getAll().get(i).getId() == ProductBean.getId()) {
                                            Modello = ProductBean.getAll().get(i).getModel();
                                            Note = ProductBean.getAll().get(i).getNote();
                                            Prezzo = ProductBean.getAll().get(i).getPrice();
                                            Tags = ProductBean.getAll().get(i).getTags();
                                            for (int k = 0; k < ProductBean.getAllBrand().size(); k++) {
                                                if (ProductBean.getAllBrand().get(k).getId() == ProductBean.getAll().get(i).getId_brand()) {
                                                    Marca = ProductBean.getAllBrand().get(k).getName();
                                                }
                                            }
                                            for (int k = 0; k < ProductBean.getAllCategories().size(); k++) {
                                                if (ProductBean.getAllCategories().get(k).getId() == ProductBean.getAll().get(i).getId_category()) {
                                                    Categoria = ProductBean.getAllCategories().get(k).getParents();
                                                }
                                            }
                                        }
                                    }
                                %>
                                <p>
                                <h2>Stai inserendo i Datasheet per il prodotto: </h2>
                                <label>Modello: </label><%=Modello%> <br>                               
                                <label>Marca </label><%=Marca%>      <br>                           
                                <label>Categoria </label><%=Categoria%><br> 
                                <label>Note: </label><%=Note%><br> 
                                <label>Prezzo: </label><%=Prezzo%><br> 
                                <label>Tags: </label><%=Tags%><br> 
                                </p>

                            </div>
                            <div class="col-lg-6">
                                <form role="form" name="mainForm" action="DatasheetInsert.jsp"  enctype="multipart/form-data" method="post">

                                    <div class="form-group">
                                        <input type="hidden" name="id" value="<%=IDProduct%>"/>
                                        <label>Scegli un documento</label>
                                        <input class="form-control"style="height: 100%" type="file" name="img"  size="20"/><br/>
                                        <p class="help-block">Inserire qui il file</p>
                                    </div>
                                    <a class="btn btn-default" href="javascript:submitForm();" >Conferma</a>
                                    <a class="btn btn-default" href="javascript:cancel();" >Annulla</a>
                                    <a class="btn btn-primary" href="javascript:stopForm();">Termina Inserimento Datasheet</a>
                                </form>
                                <form role="form" name="cancelForm" action="Product.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
                                </form>
                                <form role="form" name="stop" action="ProductConfirm.jsp" method="post">
                                    <input type="hidden" name="id" value="<%=ProductBean.getId()%>">
                                    <input type="hidden" name="status" value="view">
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <% for (int i = 0; i < ProductBean.getAllDatasheet().size(); i++) {%>
                                <%
                                    if (ProductBean.getAllDatasheet().get(i).getId_product() == ProductBean.getId()) {

                                        System.out.println("if ok ");

                                %>
                                <span >
                                    <h1><%=i%></h1>
                                    <form method="post" action="DatasheetInsert.jsp">
                                        <input type="hidden" name="id" value="<%=ProductBean.getId()%>">
                                        <input type="hidden" name="id_img" value="<%=ProductBean.getAllDatasheet().get(i).getId()%>">
                                        <input type="hidden" name="show" value="<%=ProductBean.getId()%>">
                                        <button type="submit">Download!</button>
                                    </form>
                                    <form action="DatasheetInsert.jsp" method="post">
                                        <input type="hidden" name="id" value="<%=ProductBean.getId()%>">
                                        <input type="hidden" name="id_img" value="<%=ProductBean.getAllDatasheet().get(i).getId()%>">
                                        <span> <input type="submit" name="delete" value="Elimina"> </span>

                                    </form>
                                </span>
                                <%
                                        }
                                        System.out.println("ho ciclato");
                                    }%>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <jsp:include page="Bottom.jsp" />

