<%@page import="BusinessLogic.Interface.QuoteInterface"%>
<%@page import="BusinessLogic.Interface.InvoiceInterface"%>
<%@page import="BusinessLogic.Interface.ProductInterface"%>
<%@page import="BusinessLogic.Interface.BrandInterface"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false" %>
<%@page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="LoginBean" scope="page" class="BusinessFlow.LoginBean" />
<jsp:setProperty name="LoginBean" property="*" />
<%
    String status;
    status = request.getParameter("login");
    
    
    if (status == null) {
        status = "view";
    }
    boolean loggedon = false;
    Cookie[] cookies;
    cookies = request.getCookies();
    if (!status.equals("login")&&!status.equals("logout")) {
        loggedon = LoginBean.checkUser(cookies,"view");
    } else {
        if (status.equals("login")) {
            loggedon = LoginBean.checkUser(cookies,"login");
            if (loggedon) {
                cookies = LoginBean.getCookies();
                for (int i = 0; i < cookies.length; i++) {
                    response.addCookie(cookies[i]);
                }
            }
        }
        if (status.equals("logout")) {

            cookies = LoginBean.logout(cookies);
            for (int i = 0; i < cookies.length; i++) {
                response.addCookie(cookies[i]);
            }
            loggedon = false;
        }
    }

    if (LoginBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (LoginBean.getResult() == -2) {
    }
    if (loggedon) {
        List<ProductInterface> products=LoginBean.getProducts();
        List<BrandInterface> brands=LoginBean.getBrands();
        List<InvoiceInterface> invoices=LoginBean.getInvoices();
        List<QuoteInterface> quotes=LoginBean.getQuotes();
        String[] dates=LoginBean.getDate();
       
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Benvenuti!</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>

        <!-- SB Admin CSS - Include with every page -->
        <link href="css/sb-admin.css" rel="stylesheet">

        <script language="JavaScript" src="https://code.jquery.com/jquery-1.11.1.min.js" type="text/javascript"></script>
        <script language="JavaScript" src="https://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js" type="text/javascript"></script>
        <script language="JavaScript" src="https://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/3/dataTables.bootstrap.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
        <!-- Hight Charts -->
        <script src="https://code.highcharts.com/highcharts.js"></script>
        <script src="https://code.highcharts.com/modules/exporting.js"></script>
        <script src="https://code.highcharts.com/highcharts-3d.js"></script>
        <script src="https://code.highcharts.com/modules/drilldown.js"></script>
        <script src="https://code.highcharts.com/modules/data.js"></script>




    </head>
    <body>
        <jsp:include page="Top.jsp" />
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Benvenuto!</h1>
            </div>

        </div>
        <div class="row" id="counter">
            <style>

                #counter {
                    font-family: Arial;
                    padding: 25px;
                    background-color: #f5f5f5;
                    color: #808080;
                    text-align: center;	
                }

                /*-=-=-=-=-=-=-=-=-=-=-=- */
                /* Column Grids */
                /*-=-=-=-=-=-=-=-=-=-=-=- */

                .col_half { width: 49%; }
                .col_third { width: 32%; }
                .col_fourth { width: 23.5%; }
                .col_fifth { width: 18.4%; }
                .col_sixth { width: 15%; }
                .col_three_fourth { width: 74.5%;}
                .col_twothird{ width: 66%;}
                .col_half,
                .col_third,
                .col_twothird,
                .col_fourth,
                .col_three_fourth,
                .col_fifth{
                    position: relative;
                    display:inline;
                    display: inline-block;
                    float: left;
                    margin-right: 2%;
                    margin-bottom: 20px;
                }
                .end { margin-right: 0 !important; }
                /* Column Grids End */

                .wrapper { width: 980px; margin: 30px auto; position: relative;}
                .counter { background-color: #ffffff; padding: 20px 0; border-radius: 5px;}
                .count-title { font-size: 40px; font-weight: normal;  margin-top: 10px; margin-bottom: 0; text-align: center; }
                .count-text { font-size: 13px; font-weight: normal;  margin-top: 10px; margin-bottom: 0; text-align: center; }
                .fa-2x { margin: 0 auto; float: none; display: table; color: #4ad1e5; }
            </style>
            <div class="wrapper">
                <div class="counter col_fourth">
                    <h2 class="timer count-title count-number" data-to="<%=products.size()%>" data-speed="1500"></h2>
                    <p class="count-text ">Prodotti</p>
                </div>
                    
                <div class="counter col_fourth">
                    <h2 class="timer count-title count-number" data-to="<%=brands.size()%>" data-speed="1500"></h2>
                    <p class="count-text ">Marche</p>
                </div>

                <div class="counter col_fourth">
                    <h2 class="timer count-title count-number" data-to="<%=invoices.size()%>" data-speed="1500"></h2>
                    <p class="count-text ">Fatture</p>
                </div>

                <div class="counter col_fourth end">
                    <h2 class="timer count-title count-number" data-to="<%=quotes.size()%>" data-speed="1500"></h2>
                    <p class="count-text ">Preventivi</p>
                </div>
            </div>
        </div>
        <div class="row">

            <div class="panel panel-primary">
                <div class="panel-heading">
                    Fatture
                </div>
                <div class="panel-body">
                    <div id="Fatture" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
                </div>

            </div>

        </div>
     
            <!-- /.col-lg-4 -->

            <!-- /.col-lg-4 -->
       
        <script>
            (function ($) {
                $.fn.countTo = function (options) {
                    options = options || {};

                    return $(this).each(function () {
                        // set options for current element
                        var settings = $.extend({}, $.fn.countTo.defaults, {
                            from: $(this).data('from'),
                            to: $(this).data('to'),
                            speed: $(this).data('speed'),
                            refreshInterval: $(this).data('refresh-interval'),
                            decimals: $(this).data('decimals')
                        }, options);

                        // how many times to update the value, and how much to increment the value on each update
                        var loops = Math.ceil(settings.speed / settings.refreshInterval),
                                increment = (settings.to - settings.from) / loops;

                        // references & variables that will change with each update
                        var self = this,
                                $self = $(this),
                                loopCount = 0,
                                value = settings.from,
                                data = $self.data('countTo') || {};

                        $self.data('countTo', data);

                        // if an existing interval can be found, clear it first
                        if (data.interval) {
                            clearInterval(data.interval);
                        }
                        data.interval = setInterval(updateTimer, settings.refreshInterval);

                        // initialize the element with the starting value
                        render(value);

                        function updateTimer() {
                            value += increment;
                            loopCount++;

                            render(value);

                            if (typeof (settings.onUpdate) == 'function') {
                                settings.onUpdate.call(self, value);
                            }

                            if (loopCount >= loops) {
                                // remove the interval
                                $self.removeData('countTo');
                                clearInterval(data.interval);
                                value = settings.to;

                                if (typeof (settings.onComplete) == 'function') {
                                    settings.onComplete.call(self, value);
                                }
                            }
                        }

                        function render(value) {
                            var formattedValue = settings.formatter.call(self, value, settings);
                            $self.html(formattedValue);
                        }
                    });
                };

                $.fn.countTo.defaults = {
                    from: 0, // the number the element should start at
                    to: 0, // the number the element should end at
                    speed: 1000, // how long it should take to count between the target numbers
                    refreshInterval: 100, // how often the element should be updated
                    decimals: 0, // the number of decimal places to show
                    formatter: formatter, // handler for formatting the value before rendering
                    onUpdate: null, // callback method for every time the element is updated
                    onComplete: null       // callback method for when the element finishes updating
                };

                function formatter(value, settings) {
                    return value.toFixed(settings.decimals);
                }
            }(jQuery));

            jQuery(function ($) {
                // custom formatting example
                $('.count-number').data('countToOptions', {
                    formatter: function (value, options) {
                        return value.toFixed(options.decimals).replace(/\B(?=(?:\d{3})+(?!\d))/g, ',');
                    }
                });

                // start all the timers
                $('.timer').each(count);

                function count(options) {
                    var $this = $(this);
                    options = $.extend({}, options || {}, $this.data('countToOptions') || {});
                    $this.countTo(options);
                }
            });
            Highcharts.chart('Fatture', {
                chart: {
                    type: 'spline'
                },
                title: {
                    text: 'Fatture'
                },
                subtitle: {
                    text: 'Quando sono state emesse più fatture?'
                },
                xAxis: {
                    type: 'datetime',
                    dateTimeLabelFormats: {// don't display the dummy year
                        month: '%e. %b',
                        year: '%b'
                    },
                    title: {
                        text: 'Date'
                    }
                },
                yAxis: {
                    title: {
                        text: 'Fatture Emesse'
                    },
                    min: 0
                },
                tooltip: {
                    headerFormat: '<b>{series.name}</b><br>',
                    pointFormat: 'il {point.x:%e. %b} ne sono state emesse: {point.y:.2f} '
                },
                plotOptions: {
                    spline: {
                        marker: {
                            enabled: true
                        }
                    }
                },
                series: [{
                        name: ' Fatture',
                        data: [
                            <%for(int i=0;i<dates.length;i++)
                            {
                                %>
                            <%=dates[i]%>
                            <%}%>
                        ]
                    }]
            });
            
        </script>


        <jsp:include page="Bottom.jsp" />          
        <%} else {


        %>
        <!DOCTYPE html>
    <html>

        <head>

            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">

            <title>Login</title>

            <!-- Core CSS - Include with every page -->
            <link href="css/bootstrap.min.css" rel="stylesheet">
            <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

            <!-- SB Admin CSS - Include with every page -->
            <link href="css/sb-admin.css" rel="stylesheet">
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

                function submitLogon() {

                    if (isEmpty(login.username.value)) {
                        alert("Inserire uno username.");
                        return;
                    }

                    if (isEmpty(login.passwd.value)) {
                        alert("Inserire una password.");
                        return;
                    }

                    login.submit();
                }
                function setVisible(status, loggedon) {
                    if (status !== "login" && loggedon != true)
                    {
                        document.getElementById("loginError").style = "display:none";
                    }
                }

            </script>
            <script language="JavaScript" src="https://code.jquery.com/jquery-1.11.1.min.js" type="text/javascript"></script>
            <script language="JavaScript" src="https://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js" type="text/javascript"></script>
            <script language="JavaScript" src="https://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/3/dataTables.bootstrap.js" type="text/javascript"></script>
            <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">


        </head>

        <body onload="setVisible('<%=status%>', '<%=loggedon%>');">

            <div class="container">
                <div class="row">
                    <div class="col-md-4 col-md-offset-4">
                        <div class="login-panel panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">Please Sign In</h3>
                            </div>
                            <div class="panel-body">
                                <p id="loginError" class="text-danger">Login fallito, username o password errati</p>
                                <form role="form" action="index.jsp" name="login" method="post" >
                                    <fieldset>
                                        <div class="form-group">
                                            <input class="form-control" placeholder="Username" name="username" type="text" autofocus>
                                        </div>
                                        <input type="hidden" name="login" value="login">
                                        <div class="form-group">
                                            <input class="form-control" placeholder="Password" name="passwd" type="password" value="">
                                        </div>

                                        <!-- Change this to a button or input when using this as a form -->
                                        <input type="button" value="invia" class="btn btn-lg btn-success btn-block" onClick="submitLogon()">
                                    </fieldset>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Core Scripts - Include with every page -->
            <script src="js/jquery-1.10.2.js"></script>
            <script src="js/bootstrap.min.js"></script>
            <script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>

            <!-- SB Admin Scripts - Include with every page -->
            <script src="js/sb-admin.js"></script>


            <%    }
            %>
        </body>

    </html>