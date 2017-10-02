<%@ page errorPage="/ErrorPage.jsp" %>
<%@page import="java.util.Date"%>
<%@page import="com.itextpdf.text.Document"%>
<%@page import="com.itextpdf.text.Paragraph"%>
<%@page import="com.itextpdf.text.pdf.PdfWriter"%>
<%@page import="BusinessLogic.VirtualEntities.VirtualBrand"%>
<%@page import="BusinessLogic.VirtualEntities.VirtualCategory"%>
<%@page import="BusinessLogic.VirtualEntities.VirtualLanguage"%>
<%@page import="BusinessLogic.VirtualEntities.VirtualProduct"%>


<%@page import="BusinessLogic.VirtualEntities.VirtualDdt"%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<jsp:useBean id="DocumentPrinterBean" scope="page" class="BusinessFlow.DocumentPrinterBean" />
<jsp:setProperty name="DocumentPrinterBean" property="*" />
<%
    boolean loggedon = false;

    Cookie[] cookies;
    cookies = request.getCookies();
    int idDocument = Integer.parseInt(request.getParameter("id"));
    DocumentPrinterBean.setId(idDocument);

    if (request.getParameter("doctype").equals("ddt")) {

        System.out.println("DocumentPrinter JSP: ok so che il documento è un ddt ");
        loggedon = DocumentPrinterBean.isLoggedOn(cookies, "ddt", idDocument);

        System.out.println("DocumentPrinter JSP: ho settato loggedon:" + loggedon);
    } else if (request.getParameter("doctype").equals("invoice")) {
        loggedon = DocumentPrinterBean.isLoggedOn(cookies, "invoice", idDocument);
    } else if (request.getParameter("doctype").equals("drain")) {
        loggedon = DocumentPrinterBean.isLoggedOn(cookies, "drain", idDocument);
    } else if (request.getParameter("doctype").equals("product")) {
        loggedon = DocumentPrinterBean.isLoggedOn(cookies, "product", idDocument);
    }  else if (request.getParameter("doctype").equals("quote")) {
        loggedon = DocumentPrinterBean.isLoggedOn(cookies, "quote", idDocument);
    }else {

        System.out.println("DocumentPrinter JSP: setto loggedon false");
        loggedon = false;
    }
    if (DocumentPrinterBean.getResult() == -1) {
        throw new Exception("Errore nell'applicazione: consultare i logs.");
    } else if (DocumentPrinterBean.getResult() == -2) {
    }
    if (cookies == null || !loggedon) {
        System.out.println("DocumentPrinter JSP: Torno all'index cookies:" + cookies.toString() + " loggedon " + loggedon);%>

<jsp:forward page="index.jsp"/>
<% }
//Ora si sa che siamo loggati 
// cerco di capire di che tipo di entità si tratta 

    Document document = new Document();
    try {
        response.setContentType("application/pdf");
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        DocumentPrinterBean.generate(document);
        //Add more content here
    } catch (Exception e) {
        e.printStackTrace();
    }
    document.close();


%>

