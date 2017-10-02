<%@page import="BusinessLogic.Interface.PeopleInterface"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="PeopleBean" scope="page" class="BusinessFlow.PeopleBean" />
<jsp:setProperty name="PeopleBean" property="*" />
<%
    boolean loggedon = false;
    List<PeopleInterface> peopleList = new ArrayList();
    Cookie[] cookies;
    cookies = request.getCookies();
    String status = null;
    if (PeopleBean.getId() == 0) {
        loggedon = PeopleBean.isLoggeOn(cookies);
        status = "insert";
    } else {
        status = "modify";
        loggedon = PeopleBean.getOne(cookies);
        peopleList = PeopleBean.getAll();
    }
    if (PeopleBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (PeopleBean.getResult() == -2) {
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
        <title>Inserimento o modifica utente</title>
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
        <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

    </head>
    <body>
        <jsp:include page="Top.jsp" />
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Inserimento/Modifica di una persona</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <form name="mainForm" action="PeopleDetails.jsp" method="post">

                                    <div class="form-group">
                                        <label>Ragione Sociale:</label><input type="hidden" name="id" <%if (status.equals("modify")) {%>value="<%=peopleList.get(0).getId()%>"<%}%>/>
                                        <input class="form-control" type="text" name="name" <%if (status.equals("modify")) {%>value="<%=peopleList.get(0).getName()%>"<%}%> />
                                        <p class="help-block">Inserire qui il nome della persona.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Riferimento Persona:</label>
                                        <input class="form-control" type="text" name="refer_to" <%if (status.equals("modify")) {%>value="<%=peopleList.get(0).getRefer_to()%>"<%}%> />
                                        <p class="help-block">Inserire qui il nome dell'azienda.</p>
                                    </div>

                                    <div class="form-group input-group">
                                        <label>Email</label>
                                        <input class="form-control" type="text" name="email" placeholder="email" <%if (status.equals("modify")) {%>value="<%=peopleList.get(0).getEmail()%>"<%}%> />
                                    </div>
                                    <div class="form-group">
                                        <label>Telefono:</label>
                                        <input class="form-control" type="text" name="telephone" <%if (status.equals("modify")) {%>value="<%=peopleList.get(0).getTelephone()%>"<%}%> />
                                        <p class="help-block">Inserire qui il telefono.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Codice Fiscale:</label>
                                        <input class="form-control" type="text" name="cod_fisc" <%if (status.equals("modify")) {%>value="<%=peopleList.get(0).getCod_fisc()%>"<%}%> />
                                        <p class="help-block">Inserire qui il codice fiscale.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Partita IVA:</label>
                                        <input class="form-control" type="text" name="piva" <%if (status.equals("modify")) {%>value="<%=peopleList.get(0).getPiva()%>"<%}%> />
                                        <p class="help-block">Inserire qui la partita IVA (se applicabile).</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Indirizzo:</label>
                                        <input class="form-control" type="text" name="address" <%if (status.equals("modify")) {%>value="<%=peopleList.get(0).getAddress()%>"<%}%> />
                                        <p class="help-block">Inserire qui l'indirizzo.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Civico:</label>
                                        <input class="form-control" type="text" name="civic" <%if (status.equals("modify")) {%>value="<%=peopleList.get(0).getCivic()%>"<%}%> />
                                        <p class="help-block">Inserire qui il civico.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Cap:</label>
                                        <input class="form-control" type="text" name="cap" <%if (status.equals("modify")) {%>value="<%=peopleList.get(0).getCap()%>"<%}%> />
                                        <p class="help-block">Inserire qui il cap.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Citt&agrave;:</label>
                                        <input class="form-control" type="text" name="city" <%if (status.equals("modify")) {%>value="<%=peopleList.get(0).getCity()%>"<%}%> />
                                        <p class="help-block">Inserire qui la citt&agrave;.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Provincia:</label>
                                        <input class="form-control" type="text" name="province" <%if (status.equals("modify")) {%>value="<%=peopleList.get(0).getProvince()%>"<%}%> />
                                        <p class="help-block">Inserire qui la provincia.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Fornitore</label>
                                        <div class="radio">
                                            <label>
                                                   <input type="radio" name="provider" value="true" <%if (status.equals("modify")) {
                                                           if (peopleList.get(0).getProvider()) {%>checked="checked"<%}
                                                                  }%>>S&igrave;
                                            </label>
                                        </div>
                                        <div class="radio">
                                            <label>
                                                   <input type="radio" name="provider" value="false" <%if (status.equals("modify")) {
                                                           if (!peopleList.get(0).getProvider()) {%>checked="checked"<%}
                                                                  }%>>No
                                            </label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label>Cliente</label>
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="customer" value="true" <%if (status.equals("modify")) {
                                                           if (peopleList.get(0).getCustomer()) {%>checked="checked"<%}
                                                               }%>>S&igrave;
                                            </label>
                                        </div>
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="customer" value="false" <%if (status.equals("modify")) {
                                                           if (!peopleList.get(0).getCustomer()) {%>checked="checked"<%}
                                                               }%>>No
                                            </label>
                                        </div>
                                    </div>
                                    <input type="hidden" name="status"/>
                                    <a class="btn btn-default" href="javascript:cancel();">Annulla</a>
                                    <a class="btn btn-default" href="javascript:submitForm();">Conferma</a>
                                </form>

                                <form name="cancelForm" action="People.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="Bottom.jsp" />
