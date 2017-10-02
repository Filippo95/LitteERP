<%@page import="BusinessLogic.Interface.PeopleInterface"%>
<%@page import="BusinessLogic.Interface.QuoteInterface"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="QuoteBean" scope="page" class="BusinessFlow.QuoteBean" />
<jsp:setProperty name="QuoteBean" property="*" />
<%
    boolean loggedon = false;
    QuoteInterface quote = null;
    Cookie[] cookies;
    cookies = request.getCookies();
    String status = null;
    if (cookies != null) {
        if (QuoteBean.getId() == 0) {
            loggedon = QuoteBean.isLoggedOn(cookies);
            status = "insertQuote";
        } else {
            status = "modifyQuote";
            loggedon = QuoteBean.getOne(cookies);
            quote = QuoteBean.getAll().get(0);
        }
    } 
    if (QuoteBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (QuoteBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {
%>

<jsp:forward page="index.jsp"/>
<%
    }
    List<PeopleInterface> people = QuoteBean.getPeople();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Inserimento Fattura</title>
        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>
        <script language="javascript">
            function isEmpty(value) {
                    if (value == null || value.length == 0)
                        return true;
                    for (var count = 0; count < value.length; count++) {
                        if (value.charAt(count) != " ")
                            return false;
                    }
                    return true;
                }
            function submitForm()
            {
                if(isEmpty(document.mainForm.expiring_date.value))
                {
                    alert("Inserire la data");
                    return;
                }
                if(!document.getElementById('r1').checked&&!document.getElementById('r2').checked)
                {
                    alert("Definire se è stato approvato o no");
                    return;
                }
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
        <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.min.css" />
        <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />

        <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.min.js"></script>

    </head>
    <body>

        <jsp:include page="Top.jsp" />

        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Inserimento/Modifica di una Preventivo (intestazione)</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <form role="form" name="mainForm" action="QuoteDetails.jsp" method="get">
                                    <input type="hidden" name="id" <%if (status.equals("modifyQuote")) {%>value="<%=quote.getId()%>"<%}%>/><br/>
                                    <div class="form-group">
                                        <label>Seleziona il cliente</label>
                                        <select name="id_people" class="form-control" required>
                                            <%for (int i = 0; i < people.size(); i++) {%>
                                            <option value="<%=people.get(i).getId()%>"<%if (status.equals("modifyQuote")) {
                                                    if (quote.getId_people() == people.get(i).getId()) {%>selected="selected"<%}
                                                        }%>><%=people.get(i).getName()%></option>
                                            <%}%>
                                        </select>
                                    </div>
                                    <div class="form-group" >
                                        <label>Data di Scadenza</label>
                                        <div id="sandbox-container">
                                            <div class="input-group date">
                                                <input placeholder="dd-mm-yyyy" type="text" name="expiring_date" class="form-control" <%if (status.equals("modifyQuote")) {%>value="<%=quote.getExpiring_date()%>"<%}%>><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
                                            </div>
                                        </div>
                                        <p class="help-block">Inserire qui la data di scadenza del preventivo.</p>
                                    </div>
                                    <script type="text/javascript">
            $(function () {
                $('#sandbox-container .input-group.date').datepicker({
                    format: 'yyyy-mm-dd'
                });
            });
                                    </script>
                                    <div class="form-group">
                                        <label>Descrizione</label>
                                        <input type="text" name="description" class="form-control" <%if (status.equals("modifyQuote")) {%>value="<%=quote.getDescription()%>"<%}%>  >
                                        <p class="help-block">Inserire qui la descrizione del preventivo.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Approvata dal cliente</label>
                                        <div class="radio">
                                            <label>
                                                <input type="radio" id="r1" name="approved" value="true" <%if (status.equals("modifyQuote")) {
                                                           if (quote.getApproved()) {%>checked="checked"<%}
                                                               }%>>S&igrave;
                                            </label>
                                        </div>
                                        <div class="radio">
                                            <label>
                                                <input type="radio" id="r2" name="approved" value="false" <%if (status.equals("modifyQuote")) {
                                                           if (!quote.getApproved()) {%>checked="checked"<%}
                                                               }%>>No
                                            </label>
                                        </div>
                                    </div>
                                    <input type="hidden" name="status"/>
                                    <a class="btn btn-default" href="javascript:submitForm();" >Conferma</a>
                                    <a class="btn btn-default" href="javascript:cancel();" >Annulla</a>
                                </form>
                                <form role="form" name="cancelForm" action="Quote.jsp" method="post">
                                    <input type="hidden" name="status" value="view"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="Bottom.jsp" />

