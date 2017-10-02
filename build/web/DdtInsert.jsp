<%@page import="BusinessLogic.Interface.PeopleInterface"%>
<%@page import="BusinessLogic.Interface.DdtInterface"%>
<%@page import="BusinessFlow.DdtBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="DdtBean" scope="page" class="BusinessFlow.DdtBean" />
<jsp:setProperty name="DdtBean" property="*" />
<%
    boolean loggedon = false;
    DdtInterface ddt = null;
    Cookie[] cookies;
    cookies = request.getCookies();
    String status = null;
    if (cookies != null) {
        if (DdtBean.getId() == 0) {
            loggedon = DdtBean.isLoggedOn(cookies);
            status = "insertDdt";
        } else {
            status = "modifyDdt";
            loggedon = DdtBean.getOne(cookies);
            ddt = DdtBean.getAll().get(0);
        }
    }
    if (DdtBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (DdtBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {
%>

<jsp:forward page="index.jsp"/>
<%
    }
    List<PeopleInterface> people = DdtBean.getPeople();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>DDT Insert</title>
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
                <h1 class="page-header">Inserimento/Modifica di un ddt (intestazione)</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <form role="form" name="mainForm" action="DdtDetails.jsp" method="post">
                                    <input type="hidden" name="id" <%if (status.equals("modifyDdt")) {%>value="<%=ddt.getId()%>"<%}%>/><br/>
                                    <div class="form-group">
                                        <label>Seleziona il cliente</label>
                                        <select name="id_people" class="form-control">
                                            <%for (int i = 0; i < people.size(); i++) {%>
                                            <option value="<%=people.get(i).getId()%>"<%if (status.equals("modifyDdt")){if (ddt.getId_people() == people.get(i).getId()) {%>selected="selected"<%}}%>><%=people.get(i).getName()%></option>
                                            <%}%>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Seleziona il vettore</label>
                                        <select name="id_people_vett" class="form-control">
                                            <%for (int i = 0; i < people.size(); i++) {%>
                                            <option  value="<%=people.get(i).getId()%>" <%if (status.equals("modifyDdt")){if (ddt.getId_people_vett() == people.get(i).getId()) {%>selected="selected"<%}}%>><%=people.get(i).getName()%></option>
                                            <%}%>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Indirizzo di Spedizione</label>
                                        <input class="form-control" type="text" name="destination" <%if (status.equals("modifyDdt")) {%>value="<%=ddt.getDestination()%>"<%}%> />
                                        <p class="help-block">Inserire qui l'indirizzo di spedizione per la merce.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Motivo</label>
                                        <input class="form-control" type="text" name="cause" <%if (status.equals("modifyDdt")) {%>value="<%=ddt.getCause()%>"<%}%>/>
                                        <p class="help-block">Inserire qui la causa della spedizione.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Aspetto</label>
                                        <input class="form-control" type="text" name="aspect" <%if (status.equals("modifyDdt")) {%>value="<%=ddt.getAspect()%>"<%}%>/>
                                        <p class="help-block">Inserire qui l'aspetto della merce.</p>
                                    </div>
                                        <div class="form-group">
                                        <label>Numero Colli</label>
                                        <input class="form-control" type="number" name="ncoll" <%if (status.equals("modifyDdt")) {%>value="<%=ddt.getNcoll()%>"<%}%>/>
                                        <p class="help-block">Inserire qui il numero di colli.</p>
                                    </div>
                                    <input type="hidden" name="status"/>
                                    <%if(status.equals("modifyDdt")){%><input type='hidden' name='id_drain' value='<%=ddt.getId_drain()%>'><%}%>
                                    <a class="btn btn-default" href="javascript:submitForm();" >Conferma</a>
                                    <a class="btn btn-default" href="javascript:cancel();" >Annulla</a>
                                </form>
                                <form role="form" name="cancelForm" action="Ddt.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="Bottom.jsp" />

