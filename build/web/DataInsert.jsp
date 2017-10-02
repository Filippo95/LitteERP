<%@page import="BusinessLogic.Interface.DataInterface"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="DataBean" scope="page" class="BusinessFlow.DataBean" />
<jsp:setProperty name="DataBean" property="*" />
<%
    boolean loggedon = false;
    DataInterface data;
    Cookie[] cookies;
    cookies = request.getCookies();
    String status = "modify";
    loggedon = DataBean.isLoggeOn(cookies);
    data=DataBean.getData();
    if (DataBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (DataBean.getResult() == -2) {
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
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
        <link href="css/sb-admin.css" rel="stylesheet">
        <link href="css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
        <title>Modifica Dati dell'azienda</title>
        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>
        <script language="javascript">

            function submitForm()
            {
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
        <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

    </head>
    <body>
        <jsp:include page="Top.jsp" />
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Modifica dei Dati dell'Azienda.</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <form name="mainForm" action="Data.jsp" method="post">
                                    <div class="form-group">
                                        <label>Ragione Sociale</label>
                                        <input class="form-control" type="text" name="rag_soc" value="<%=data.getRag_soc()%>"/>
                                        <p class="help-block">Inserire qui la ragione sociale.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Responsabile:</label>
                                        <input class="form-control" type="text" name="responsabile" value="<%=data.getResponsabile()%>"/>
                                        <p class="help-block">Inserire qui il nome del responsabile.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Dominio:</label>
                                        <input class="form-control" type="text" name="domain" value="<%=data.getDomain()%>"/>
                                        <p class="help-block">Inserire qui il dominio.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Partita IVA:</label>
                                        <input class="form-control" type="text" name="piva" value="<%=data.getPiva()%>"/>
                                        <p class="help-block">Inserire qui la partita IVA.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Codice Fiscale</label>
                                        <input class="form-control" type="text" name="codfisc" value="<%=data.getCodfisc()%>"/>
                                        <p class="help-block">Inserire qui il codice fiscale.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Indirizzo:</label>
                                        <input class="form-control" type="text" name="indirizzo" value="<%=data.getIndirizzo()%>" />
                                        <p class="help-block">Inserire qui l'indirizzo.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Civico:</label>
                                        <input class="form-control" type="text" name="civico" value="<%=data.getCivico()%>" />
                                        <p class="help-block">Inserire qui il civico.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Cap:</label>
                                        <input class="form-control" type="text" name="cap" value="<%=data.getCap()%>" />
                                        <p class="help-block">Inserire qui il cap.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Citt&agrave;: </label>
                                        <input class="form-control" type="text" name="city" value="<%=data.getCity()%>" />
                                        <p class="help-block">Inserire qui la citt&agrave;: .</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Provincia:</label>
                                        <input class="form-control" type="text" name="provincia" value="<%=data.getProvincia()%>" />
                                        <p class="help-block">Inserire qui la provincia.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Mail Principale</label>
                                        <input class="form-control" type="text" name="main_mail" value="<%=data.getMain_mail()%>"/>
                                        <p class="help-block">Inserire qui la mail dell'azienda.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Contact From Mail</label>
                                        <input class="form-control" type="text" name="contact_from_mail" value="<%=data.getContact_from_mail()%>"/>
                                        <p class="help-block"></p>
                                    </div>
                                    <div class="form-group">
                                        <label>Mail Amministratore di Sistema</label>
                                        <input class="form-control" type="text" name="sysadmin_mail" value="<%=data.getSysadmin_mail()%>"/>
                                        <p class="help-block">Inserire qui la mail dell'Amministratore di Sistema.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Server SMTP</label>
                                        <input class="form-control" type="text" name="smtp_srv" value="<%=data.getSmtp_srv()%>"/>
                                        <p class="help-block">Inserire qui il Server SMTP.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Server POP3</label>
                                        <input class="form-control" type="text" name="pop3_srv" value="<%=data.getPop3_srv()%>"/>
                                        <p class="help-block">Inserire qui il sever POP3.</p>
                                    </div>
                                    <input type="hidden" name="status" value='<%=status%>'/>
                                    <a class="btn btn-default" href="javascript:cancel();">Annulla</a>
                                    <a class="btn btn-default" href="javascript:submitForm();">Conferma</a>
                                </form>

                                <form name="cancelForm" action="Data.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="Bottom.jsp" />
