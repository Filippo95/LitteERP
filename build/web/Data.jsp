<%@page import="BusinessLogic.Interface.DataInterface"%>
<%@page import="sun.misc.BASE64Encoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>

<jsp:useBean id="DataBean" scope="page" class="BusinessFlow.DataBean" />
<jsp:setProperty name="DataBean" property="*" />
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
        if (status.equals("modify")) {
            loggedon = DataBean.update(cookies);
        }
        if (status.equals("view")) {
            loggedon = DataBean.list(cookies);
        }
    }
    if (DataBean.getResult()==-1){    
    throw new Exception("Errore nell'applicazione: consultare i logs.");
  } else if (DataBean.getResult()==-2) {
  }
    if (cookies == null || !loggedon) {%>

<jsp:forward page="index.jsp"/>
<% }
    DataInterface data = DataBean.getData();

%>
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
        <link href="css/sb-admin.css" rel="stylesheet">
        <title>Data View</title>
        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>
        <script language="javascript">
            function modifyData(code)
            {
                document.modifyForm.id.value = code;
                document.modifyForm.submit();
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
                <h1 class="page-header">Dati dell'azienda</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <a class="btn btn-default" href="javascript:modifyData();">Modifica i dati dell'azienda</a>
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
            <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h3>Ragione Sociale</h3>
                        <p><%=data.getRag_soc()%></p>
                        <h3>Responsabile</h3>
                        <p><%=data.getResponsabile()%></p>
                        <h3>Dominio</h3>
                        <p><%=data.getDomain()%></p>
                        <h3>Partita IVA</h3>
                        <p><%=data.getPiva()%></p>
                        <h3>Codice Fiscale</h3>
                        <p><%=data.getCodfisc()%></p>
                        <h3>Indirizzo</h3>
                        <address>
                            <%=data.getIndirizzo()%>, <%=data.getCivico()%>
                            <br><%=data.getCity()%>, <%=data.getProvincia()%>  <%=data.getCap()%>
                        </address>
                    </div>
                </div>
            </div>
            <div class="col-lg-6"><div class="panel panel-default">
                    <div class="panel-body">
                        <h3>Mail Principale</h3>
                        <p><%=data.getMain_mail()%></p>
                        <h3>Contact From Mail</h3>
                        <p><%=data.getContact_from_mail()%></p>
                        <h3>Mail Amministratore di Sistema</h3>
                        <p><%=data.getSysadmin_mail()%></p>
                        <h3>Server SMTP</h3>
                        <p><%=data.getSmtp_srv()%></p>
                        <h3>Server POP3</h3>
                        <p><%=data.getPop3_srv()%></p>
                        <h3>Logo</h3>
                        <%BASE64Encoder base64Encoder = new BASE64Encoder();
                            StringBuilder imageString = new StringBuilder();
                            imageString.append("data:image/png;base64,");
                            imageString.append(base64Encoder.encode(data.getLogo()));
                            String image = imageString.toString();%>
                        <img id="ItemPreview" src="<%=image%>">
                    </div>
                </div>
            </div>
        </div>
        <form name="modifyForm" action="DataInsert.jsp" method="post">
            <input type="hidden" name="status" value="view"/>
            <input type="hidden" name="id" />
        </form>

        <jsp:include page="Bottom.jsp" /> 