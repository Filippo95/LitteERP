<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page errorPage="/ErrorPage.jsp" %>
<jsp:useBean id="AdminArea" scope="page" class="BusinessFlow.AdminArea" />
<jsp:setProperty name="AdminArea" property="*" />
<%
    boolean loggedon = false;

    Cookie[] cookies;
    cookies = request.getCookies();

    loggedon = AdminArea.isLoggeOn(cookies);

    System.out.println("Sto per fare il greande if");
    if (cookies == null || !loggedon) {%>

<jsp:forward page="index.jsp"/>
<% }

    if (AdminArea.getLg().getVu().getAdmin()) {


%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Area</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

        <link rel="shortcut icon" type="image/png" href="images/logo.png"/>
        <link href="css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">


        <!-- SB Admin CSS - Include with every page -->
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
                <h1 class="page-header">Admin Area</h1>
            </div>
            <!-- /.col-lg-12 -->
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
                    <h2 class="timer count-title count-number" data-to="<%=AdminArea.Fatturato%>" data-speed="1500"></h2>
                    <p class="count-text ">Fatturato</p>
                </div>

                <div class="counter col_fourth">
                    <h2 class="timer count-title count-number" data-to="<%=AdminArea.fc%>" data-speed="1500"></h2>
                    <p class="count-text ">Fatturato - carichi</p>
                </div>

             

                <div class="counter col_fourth ">
                    <h2 class="timer count-title count-number" data-to="<%=AdminArea.nfat%>" data-speed="1500"></h2>
                    <p class="count-text ">n°Fatture Emesse</p>
                </div>
                <div class="counter col_fourth ">
                    <h2 class="timer count-title count-number" data-to="<%=AdminArea.nfatpag%>" data-speed="1500"></h2>
                    <p class="count-text ">n°Fatture pagate</p>
                </div>
                <div class="counter col_fourth ">
                    <h2 class="timer count-title count-number" data-to="<%=AdminArea.nprev%>" data-speed="1500"></h2>
                    <p class="count-text ">n° Preventivi</p>
                </div>
            </div>
        </div>

        <!-- /.row -->
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
            </script>


        <jsp:include page="Bottom.jsp" /> 

        <%} else {
        %>
        <jsp:forward page="NotAllowed.jsp"/>
        <%
    }%>