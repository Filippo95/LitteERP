<%@page import="BusinessLogic.Interface.UserInterface"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="UserBean" scope="page" class="BusinessFlow.UserBean" />
<jsp:setProperty name="UserBean" property="*" />
<%
    boolean loggedon = false;
    List<UserInterface> userList = new ArrayList();
    Cookie[] cookies;
    cookies = request.getCookies();
    String status = null;
    if (UserBean.getId() == 0) {
        loggedon = UserBean.isLoggeOn(cookies);
        status = "insert";
    } else {
        status = "modify";
        loggedon = UserBean.getOne(cookies);
        userList = UserBean.getAll();
    }
    if (UserBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (UserBean.getResult() == -2) {
    }
    
   
    if (cookies == null || !loggedon) {
%>

<jsp:forward page="index.jsp"/>
<%
    }
 if(UserBean.getLg().getVu().getAdmin())
{


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
        <script language="JavaScript" src="https://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js" type="text/javascript"></script>
        <script language="JavaScript" src="https://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/3/dataTables.bootstrap.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

    </head>
    <body>
        <jsp:include page="Top.jsp" />
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Inserimento/Modifica di una unit&agrave; di un Utente.</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <form name="mainForm" action="UserDetails.jsp" method="post">
                                    <input type="hidden" name="id" <%if (status.equals("modify")) {%>value="<%=userList.get(0).getId()%>"<%}%>/>
                                    <div class="form-group">
                                        <label>Username:</label>
                                        <input class="form-control" type="text" name="username" <%if (status.equals("modify")) {%>value="<%=userList.get(0).getUsername()%>"<%}%>/>
                                        <p class="help-block">Inserire qui lo username.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Password:</label>
                                        <% if(status.equals("modify"))
                                        {
                                            %>
                                            <input type="hidden" name="oldpasswd" value="<%=userList.get(0).getPasswd()%>">
                                        <%}%>
                                        
                                        <input class="form-control" type="password" name="passwd"  />
                                        <p class="help-block">Inserire qui la nuova password se la si vuole cambiare, lascare vuoto per mantenere invarata.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Nome:</label>
                                        <input class="form-control" type="text" name="nome" <%if (status.equals("modify")) {%>value="<%=userList.get(0).getNome()%>"<%}%> />
                                        <p class="help-block">Inserire qui il nome dell'utente.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Cognome:</label>
                                        <input class="form-control" type="text" name="cognome" <%if (status.equals("modify")) {%>value="<%=userList.get(0).getCognome()%>"<%}%> />
                                        <p class="help-block">Inserire qui il cognome dell'utente.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Telefono:</label>
                                        <input class="form-control" type="text" name="tel" <%if (status.equals("modify")) {%>value="<%=userList.get(0).getTel()%>"<%}%> />
                                        <p class="help-block">Inserire qui il telefono.</p>
                                    </div>
                                    <label>Email:</label>
                                    <div class="form-group input-group">
                                        <span class="input-group-addon">@</span>
                                        <input placeholder="Email" class="form-control" type="text" name="email" <%if (status.equals("modify")) {%>value="<%=userList.get(0).getEmail()%>"<%}%>/>
                                    </div>
                                    <div class="form-group">
                                        <label>Indirizzo:</label>
                                        <input class="form-control" type="text" name="indirizzo" <%if (status.equals("modify")) {%>value="<%=userList.get(0).getIndirizzo()%>"<%}%> />
                                        <p class="help-block">Inserire qui l'indirizzo.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Civico:</label>
                                        <input class="form-control" type="text" name="civico" <%if (status.equals("modify")) {%>value="<%=userList.get(0).getCivico()%>"<%}%> />
                                        <p class="help-block">Inserire qui il civico.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Cap:</label>
                                        <input class="form-control" type="text" name="cap" <%if (status.equals("modify")) {%>value="<%=userList.get(0).getCap()%>"<%}%> />
                                        <p class="help-block">Inserire qui il cap.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Citt&agrave;: </label>
                                        <input class="form-control" type="text" name="city" <%if (status.equals("modify")) {%>value="<%=userList.get(0).getCity()%>"<%}%> />
                                        <p class="help-block">Inserire qui la citt&agrave;: .</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Provincia:</label>
                                        <input class="form-control" type="text" name="provincia" <%if (status.equals("modify")) {%>value="<%=userList.get(0).getProvincia()%>"<%}%> />
                                        <p class="help-block">Inserire qui la provincia.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Amministratore</label>
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="admin" value="true" <%if (status.equals("modify")) {
                                                           if (userList.get(0).getAdmin()) {%>checked="checked"<%}
                                                               }%>>S&igrave;
                                            </label>
                                        </div>
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="admin" value="false" <%if (status.equals("modify")) {
                                                           if (!userList.get(0).getAdmin()) {%>checked="checked"<%}
                                                               }%>>No
                                            </label>
                                        </div>
                                    </div>  
                                    <input type="hidden" name="status"/>
                                    <a class="btn btn-default" href="javascript:cancel();">Annulla</a>
                                    <a class="btn btn-default" href="javascript:submitForm();">Conferma</a>
                                </form>

                                <form name="cancelForm" action="User.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="Bottom.jsp" />
    <% } else
{%>
<jsp:forward page="NotAllowed.jsp"/>
<%
}%>
    