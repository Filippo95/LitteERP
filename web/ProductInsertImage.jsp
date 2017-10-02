<%@page import="BusinessLogic.Interface.ProductInterface"%>
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
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="ProductBean" scope="page" class="BusinessFlow.ProductBean" />
<jsp:setProperty name="ProductBean" property="*" />
<%
    boolean loggedon = false;
    List<ProductInterface> productList = new ArrayList();
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
                        ProductBean.setContent_type("");
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
                loggedon = ProductBean.insertImage(cookies);
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
                loggedon = ProductBean.isLoggeOn(cookies, Integer.parseInt(request.getParameter("id_img")),1);

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
        <title>Insert Product Image</title>
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
                <h1 class="page-header">Carica foto per Prodotto</h1>
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
                                <h2>Stai inserendo le immagini per il prodotto: </h2>
                                <label>Modello: </label><%=Modello%> <br>                               
                                <label>Marca </label><%=Marca%>      <br>                           
                                <label>Categoria </label><%=Categoria%><br> 
                                <label>Note: </label><%=Note%><br> 
                                <label>Prezzo: </label><%=Prezzo%><br> 
                                <label>Tags: </label><%=Tags%><br> 
                                </p>

                            </div>
                            <div class="col-lg-6">
                                <form role="form" name="mainForm" action="ProductInsertImage.jsp"  enctype="multipart/form-data" method="post">

                                    <div class="form-group">
                                        <input type="hidden" name="id" value="<%=IDProduct%>"/>
                                        <label>Scegli un immagine</label>
                                        <input class="form-control"style="height: 100%" type="file" name="img"  size="20"/><br/>
                                        <p class="help-block">Inserire qui il file</p>
                                    </div>
                                    <a class="btn btn-default" href="javascript:submitForm();" >Conferma</a>
                                    <a class="btn btn-default" href="javascript:cancel();" >Annulla</a>
                                    <a class="btn btn-primary" href="javascript:stopForm();">Termina Inserimento Immagini</a>
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
                                <% for (int i = 0; i < ProductBean.getAllImages().size(); i++) {%>
                                <%
                                    if (ProductBean.getAllImages().get(i).getId_product() == ProductBean.getId()) {

                                        BASE64Encoder base64Encoder = new BASE64Encoder();
                                        StringBuilder imageString = new StringBuilder();
                                        imageString.append("data:image/png;base64,");
                                        imageString.append(base64Encoder.encode(ProductBean.getAllImages().get(i).getContent()));
                                        String image = imageString.toString();
                                %>
                                <form action="ProductInsertImage.jsp" method="post">
                                    <input type="hidden" name="id" value="<%=ProductBean.getId()%>">
                                    <input type="hidden" name="id_img" value="<%=ProductBean.getAllImages().get(i).getId()%>">
                                    <span><img src="<%=image%>" class="img-thumbnail" alt="" width="304" height="236"> <input type="submit" name="delete" value="Elimina"> </span>
                                </form>
                                <%
                                        }
                                    }%>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <jsp:include page="Bottom.jsp" />

