/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessFlow;

import BusinessLogic.Interface.BrandInterface;
import BusinessLogic.Interface.CategoryInterface;
import BusinessLogic.Interface.DataInterface;
import BusinessLogic.Interface.DdtInterface;
import BusinessLogic.Interface.DdtRowInterface;
import BusinessLogic.Interface.DescriptionInterface;
import BusinessLogic.Interface.DrainInterface;
import BusinessLogic.Interface.DrainRowInterface;
import BusinessLogic.Interface.InvoiceInterface;
import BusinessLogic.Interface.InvoiceRowInterface;
import BusinessLogic.Interface.LanguageInterface;
import BusinessLogic.Interface.PeopleInterface;
import BusinessLogic.Interface.ProductInterface;
import BusinessLogic.Interface.QuoteInterface;
import BusinessLogic.Interface.QuoteRowInterface;
import BusinessLogic.Interface.UnitInterface;
import BusinessLogic.VirtualEntities.VirtualBrand;
import BusinessLogic.VirtualEntities.VirtualCategory;
import BusinessLogic.VirtualEntities.VirtualData;
import BusinessLogic.VirtualEntities.VirtualDdt;
import BusinessLogic.VirtualEntities.VirtualDdtRow;
import BusinessLogic.VirtualEntities.VirtualDescription;
import BusinessLogic.VirtualEntities.VirtualDrain;
import BusinessLogic.VirtualEntities.VirtualDrainRow;
import BusinessLogic.VirtualEntities.VirtualInvoice;
import BusinessLogic.VirtualEntities.VirtualInvoiceRow;
import BusinessLogic.VirtualEntities.VirtualLanguage;
import BusinessLogic.VirtualEntities.VirtualPeople;
import BusinessLogic.VirtualEntities.VirtualProduct;
import BusinessLogic.VirtualEntities.VirtualQuote;
import BusinessLogic.VirtualEntities.VirtualQuoteRow;
import BusinessLogic.VirtualEntities.VirtualUnit;
import Services.Database.Database;
import Services.Database.DatabaseService;
import Services.Exception.PersonalizedException;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import javax.servlet.http.Cookie;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author filippo
 */
public class DocumentPrinterBean {

    private Database db;
    private Login lg;
    private int id;
    public boolean Invoice = false, Ddt = false, Drain = false, Product = false, Quote = false;
    //VirtualObjects for generate document
    DdtInterface vddt;
    ProductInterface vp;
    InvoiceInterface vi;
    PeopleInterface vP,vPP;
    VirtualInvoiceRow vir;

    //DATI dell'azienda
    private DataInterface data = null;

    //Liste di appoggio 
    private VirtualCategory vc = null;
    private VirtualBrand vb = null;
    private VirtualUnit vu = null;
    private VirtualDescription vd = null;
    private VirtualLanguage vl = null;
    private QuoteInterface vq = null;
    private VirtualDdtRow ddtr = null;
    private DrainInterface drain=null;
    private VirtualDrainRow drainrow=null;
    private VirtualQuoteRow vqr=null;

    private List<CategoryInterface> allCategories;
    private List<BrandInterface> allBrand;
    private List<UnitInterface> allUnit;
    private List<DescriptionInterface> descriptions;
    private List<LanguageInterface> languages;
    private List<InvoiceRowInterface> irowsf;
    private List<InvoiceRowInterface> irowsp;
    private List<DdtRowInterface> ddtrowsf;
    private List<DdtRowInterface> ddtrowsp;
    private List<DrainRowInterface> drainrows;
    
    private List<QuoteRowInterface> vqrf;    
    private List<QuoteRowInterface> vqrp;
    private int result;

    public DocumentPrinterBean() {

    }

    public Boolean isLoggedOn(Cookie[] cookies, String DocType, int id) {
        db = null;
        try {
            db = DatabaseService.getDataBase();

            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);

            if (lg.checkUser(auth[0], auth[1])) {
                populateAllList();
                VirtualData auxdata = new VirtualData(db);
                data = auxdata.getData();
                System.out.println("DocumentPrinter Bean: il chekuser  true");
                if (DocType.equals("ddt")) {
                    Ddt = true;
                    // qui instanzio il Virtual in modo da averli disponibili nel metodo generate                    
                    VirtualDdt vddtaux = new VirtualDdt(db);
                    vddt = vddtaux.getVirtualDdt(id);

                
                    VirtualPeople auxvP = new VirtualPeople(db);
                    vP = auxvP.getVirtualPeople(vddt.getId_people());
                    if(vddt.getId_people_vett()!=0)
                    {
                    VirtualPeople auxvPP=new VirtualPeople(db);
                    
                    vPP=auxvPP.getVirtualPeople(vddt.getId_people_vett());
                    
                    }
                    ddtr=new VirtualDdtRow(db);
                    ddtrowsp = ddtr.getAllRowProduct(id);
                    ddtrowsf = ddtr.getAllRowFree(id);

                }
                if (DocType.equals("invoice")) {
                    Invoice = true;
                    VirtualInvoice auxvi = new VirtualInvoice(db);
                    vi = auxvi.getVirtualInvoice(id);
                    
                    VirtualPeople auxvP = new VirtualPeople(db);
                    vP = auxvP.getVirtualPeople(vi.getId_people());
                    vir = new VirtualInvoiceRow(db);
                    irowsp = vir.getAllRowProduct(id);
                    irowsf = vir.getAllRowFree(id);
                    vp = new VirtualProduct(db);

                }
                if (DocType.equals("drain")) {
                    
        System.out.println("drain=true");
                    Drain = true;
                    VirtualDrain auxdrain=new VirtualDrain(db);
                    System.out.println("ho istanziato il virtual drain");
                    drain=auxdrain.getVirtualDrain(id);                    
                    System.out.println("dopo che ho inizializzato il drian");
                    drainrow=new VirtualDrainRow(db);
                    drainrows=drainrow.getAllRow(id);
                    
                    System.out.println("ho ");
                }
                if (DocType.equals("product")) {
                    Product = true;
                    VirtualProduct auxvp = new VirtualProduct(db);
                    vp = auxvp.getVirtualProduct(id);
                }
                if (DocType.equals("quote")) {
                    Quote = true;
                    VirtualQuote auxvq = new VirtualQuote(db);
                    vq = auxvq.getVirtualQuote(id);
                    vqr=new VirtualQuoteRow(db);
                    vqrf=vqr.getAllRowFree(id);                    
                    vqrp=vqr.getAllRowProduct(id);
                    VirtualPeople auxvP = new VirtualPeople(db);
                    vP = auxvP.getVirtualPeople(vq.getId_people());
                }

                db.commit();
                db.close();

                System.out.println("DocumentPrinter Bean: sto per ritornare true;");
                return true;
            } else {
                System.out.println("DocumentPrinter Bean: il chekuser  false");
                db.commit();
                db.close();
                return false;
            }
        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);
            db.rollBack();
        }
        return false;
    }

    public void populateAllList() {
        this.PopulateAllBrand();
        this.PopulateAllCategories();
        this.PopulateAllUnit();
    }

    public void PopulateAllCategories() {
        try {
            vc = new VirtualCategory(db);
            allCategories = vc.getAll();
        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);
            db.rollBack();
        }
    }

    public void PopulateAllUnit() {
        try {
            vu = new VirtualUnit(db);
            allUnit = vu.getAll();

        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);
            db.rollBack();

        }

    }

    public void PopulateAllBrand() {
        try {
            vb = new VirtualBrand(db);
            allBrand = vb.getAll();

        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);
            db.rollBack();

        }

    }

    public void PopulateDescriptions() {
        try {
            vd = new VirtualDescription(db);
            descriptions = vd.getDescriptions(this.id);
        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);
            db.rollBack();
        }
    }

    public void PopulateLanguages() {
        try {
            vl = new VirtualLanguage(db);
            languages = vl.getAll();
        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);
            db.rollBack();
        }
    }

    public void generate(Document doc) {
        try {
            if (this.Ddt) {
                generateDDT(doc);

            } else if (this.Drain) {
                
        System.out.println("sto per entrare nella funzione generatrice");
                generateDrain(doc);
            } else if (this.Invoice) {
                generateInvoice(doc);
            } else if (this.Product) {
                generateProduct(doc);
            } else if (this.Quote) {
                generateQuote(doc);
            }

        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);
        }

    }

    private void generateInvoice(Document doc) throws DocumentException {
        //Paragrafo1 N°Fattura
        Paragraph p;
        PdfPTable datiAziendali = new PdfPTable(2);

        PdfPCell dati = new PdfPCell();
        dati.setBorder(PdfPCell.NO_BORDER);
        dati.addElement(new Paragraph(data.getRag_soc() + " di " + data.getResponsabile()));
        dati.addElement(new Paragraph(data.getIndirizzo() + " " + data.getCivico() + " " + data.getCap() + " " + data.getCity() + " " + data.getProvincia()));
        dati.addElement(new Paragraph("P.IVA" + data.getPiva()));
        dati.addElement(new Paragraph("C.F. " + data.getCodfisc()));

        dati.addElement(new Paragraph(" "));
        PdfPCell logo = new PdfPCell();
        logo.setBorder(PdfPCell.NO_BORDER);

        datiAziendali.addCell(dati);
        datiAziendali.addCell(logo);

        doc.add(datiAziendali);
        DottedLineSeparator separator = new DottedLineSeparator();
        separator.setPercentage(59500f / 523f);
        Chunk linebreak = new Chunk(separator);
        doc.add(linebreak);
        //Pragrafo 2 DAta Fattura
        p = new Paragraph("FATTURA");
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p);
        doc.add(linebreak);

        //Dati cliente e Dati Azienda
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        PdfPCell seller = new PdfPCell();
        seller.setBorder(PdfPCell.NO_BORDER);
        seller.addElement(new Paragraph("Documento n°" + vi.getId()));
        GregorianCalendar cal = vi.getIdate();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        String date = day + "-" + (month + 1) + "-" + year;
        seller.addElement(new Paragraph("del: " + date));
        seller.addElement(new Paragraph("Causale: Vendita"));
        seller.addElement(new Paragraph("Pagamento : " + vi.getExpiring_days() + " "));
        seller.addElement(new Paragraph("Riferimento sig./.ra : "));
        seller.addElement(new Paragraph("Riferimento: " + vP.getRefer_to()));
        table.addCell(seller);

        PdfPCell buyer = new PdfPCell();
        buyer.setBorder(PdfPCell.NO_BORDER);
        buyer.addElement(new Paragraph("Spettabile " + vP.getName()));
        buyer.addElement(new Paragraph("Indirizzo: " + vP.getAddress() + " " + vP.getCap()));
        buyer.addElement(new Paragraph("località: " + vP.getCity()));
        buyer.addElement(new Paragraph("Cod.Fisc." + vP.getCod_fisc()));
        buyer.addElement(new Paragraph("Part.Iva:" + vP.getPiva()));
        table.addCell(buyer);

        doc.add(table);
        
        table = new PdfPTable(2);
        table.setWidthPercentage(100);

        

        //Tabella  Prodotti
        table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);
        table.setWidths(new int[]{2, 2, 1, 2, 2});
        table.addCell(getCell("Codice", Element.ALIGN_LEFT));
        table.addCell(getCell("Descrizione", Element.ALIGN_LEFT));
        table.addCell(getCell("Quantità", Element.ALIGN_LEFT));
        table.addCell(getCell("Prezzo unitario", Element.ALIGN_LEFT));
        table.addCell(getCell("Totale", Element.ALIGN_LEFT));
        String Modello = "";
        double Totale = 0;
        for (int i = 0; i < irowsp.size(); i++) {

            table.addCell(getCell("" + irowsp.get(i).getId_product(), Element.ALIGN_LEFT));

            table.addCell(getCell(irowsp.get(i).getDescription(), Element.ALIGN_RIGHT));
            table.addCell(getCell("" + irowsp.get(i).getQuantity(), Element.ALIGN_RIGHT));
            table.addCell(getCell("" + irowsp.get(i).getPrice(), Element.ALIGN_RIGHT));
            table.addCell(getCell("" + irowsp.get(i).getQuantity() * irowsp.get(i).getPrice(), Element.ALIGN_RIGHT));
            Totale += irowsp.get(i).getQuantity() * irowsp.get(i).getPrice();
        }
        for (int i = 0; i < irowsf.size(); i++) {

            table.addCell(getCell("", Element.ALIGN_LEFT));

            table.addCell(getCell(irowsf.get(i).getDescription(), Element.ALIGN_RIGHT));
            table.addCell(getCell("" + irowsf.get(i).getQuantity(), Element.ALIGN_RIGHT));
            table.addCell(getCell("" + irowsf.get(i).getPrice(), Element.ALIGN_RIGHT));
            table.addCell(getCell("" + irowsf.get(i).getQuantity() * irowsf.get(i).getPrice(), Element.ALIGN_RIGHT));
            Totale += irowsp.get(i).getQuantity() * irowsp.get(i).getPrice();
        }
        doc.add(table);

        p = new Paragraph("");
        p.setAlignment(Element.ALIGN_LEFT);

        table = new PdfPTable(5);

        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);
        table.setWidths(new int[]{2, 2, 1, 2, 2});

        PdfPCell cell = new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(getCell("Imponibile: ", PdfPCell.ALIGN_RIGHT));
        table.addCell(getCell("" + (double) Math.round(Totale), PdfPCell.ALIGN_RIGHT));

        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(getCell("IVA: ", PdfPCell.ALIGN_RIGHT));
        table.addCell(getCell("22%", PdfPCell.ALIGN_RIGHT));

        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(getCell("Totale:", PdfPCell.ALIGN_RIGHT));
        Totale += (Totale * 22) / 100;
        table.addCell(getCell("" + (double) Math.round(Totale), PdfPCell.ALIGN_RIGHT));

        p.add(table);
        doc.add(p);
    }

    private void generateDDT(Document doc) throws DocumentException {
        //Paragrafo1 N°Fattura
        Paragraph p;
        PdfPTable datiAziendali = new PdfPTable(2);

        PdfPCell dati = new PdfPCell();
        dati.setBorder(PdfPCell.NO_BORDER);
        dati.addElement(new Paragraph(data.getRag_soc() + " di " + data.getResponsabile()));
        dati.addElement(new Paragraph(data.getIndirizzo() + " " + data.getCivico() + " " + data.getCap() + " " + data.getCity() + " " + data.getProvincia()));
        dati.addElement(new Paragraph("P.IVA" + data.getPiva()));
        dati.addElement(new Paragraph("C.F. " + data.getCodfisc()));

        dati.addElement(new Paragraph(" "));
        PdfPCell logo = new PdfPCell();
        logo.setBorder(PdfPCell.NO_BORDER);

        datiAziendali.addCell(dati);
        datiAziendali.addCell(logo);

        doc.add(datiAziendali);
        DottedLineSeparator separator = new DottedLineSeparator();
        separator.setPercentage(59500f / 523f);
        Chunk linebreak = new Chunk(separator);
        doc.add(linebreak);
        //Pragrafo 2 DAta Fattura
        p = new Paragraph("Documento di Trasporto");
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p);
        doc.add(linebreak);

        //Dati cliente e Dati Azienda
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        PdfPCell seller = new PdfPCell();
        seller.setBorder(PdfPCell.BOX);
        seller.addElement(new Paragraph("Documento n°" + vddt.getId()));
        GregorianCalendar cal = vddt.getDdate();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        String date = day + "-" + (month + 1) + "-" + year;
        seller.addElement(new Paragraph("del: " + date));
        seller.addElement(new Paragraph("Causale: "+vddt.getCause()));
        table.addCell(seller);
        
        
        PdfPCell buyer = new PdfPCell();
        buyer.setBorder(PdfPCell.BOX);
        buyer.addElement(new Paragraph("Spettabile " + vP.getName()));
        buyer.addElement(new Paragraph("Indirizzo: " + vP.getAddress() + " " + vP.getCap()));
        buyer.addElement(new Paragraph("località: " + vP.getCity()));
        buyer.addElement(new Paragraph("Cod.Fisc." + vP.getCod_fisc()));
        buyer.addElement(new Paragraph("Part.Iva:" + vP.getPiva()));        
        buyer.addElement(new Paragraph(" " ));
        table.addCell(buyer);

        
        PdfPCell spec1 = new PdfPCell();
        spec1.setBorder(PdfPCell.BOX);
        spec1.addElement(new Paragraph("Aspetto " + vddt.getAspect()));
       
        spec1.addElement(new Paragraph("N° Colli "+vddt.getNcoll() ));
        spec1.addElement(new Paragraph("Destinazione "+vddt.getDestination()));
     
        table.addCell(spec1);

        if(vPP!=null)
        {
        PdfPCell spec = new PdfPCell();
        spec.setBorder(PdfPCell.BOX);
        spec.addElement(new Paragraph("Vettore " + vddt.getVettName()));
        spec.addElement(new Paragraph("Indirizzo: " +vPP.getAddress() + " " + vPP.getCap()));
        spec.addElement(new Paragraph("località: " + vPP.getCity()));
        spec.addElement(new Paragraph("Cod.Fisc." + vPP.getCod_fisc()));
        spec.addElement(new Paragraph("Part.Iva:" + vPP.getPiva()));
        spec.addElement(new Paragraph(" " ));
        table.addCell(spec);
        }
        doc.add(table);

        //Tabella  Prodotti
        table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);
        table.setWidths(new int[]{2, 2, 1, 2, 2});
        table.addCell(getCell("Codice:", Element.ALIGN_LEFT));
        table.addCell(getCell("Descrizione:", Element.ALIGN_LEFT));
        table.addCell(getCell("Qauntità:", Element.ALIGN_LEFT));
        table.addCell(getCell("Prezzo unitario:", Element.ALIGN_LEFT));
        table.addCell(getCell("Totale:", Element.ALIGN_LEFT));
        String Modello = "";
        double Totale = 0;
        for (int i = 0; i < ddtrowsp.size(); i++) {

            table.addCell(getCell("" + ddtrowsp.get(i).getId_product(), Element.ALIGN_LEFT));

            table.addCell(getCell(ddtrowsp.get(i).getDescription(), Element.ALIGN_RIGHT));
            table.addCell(getCell("" + ddtrowsp.get(i).getQuantity(), Element.ALIGN_RIGHT));
            table.addCell(getCell("" + ddtrowsp.get(i).getPrice(), Element.ALIGN_RIGHT));
            table.addCell(getCell("" + ddtrowsp.get(i).getQuantity() * ddtrowsp.get(i).getPrice(), Element.ALIGN_RIGHT));
            Totale += ddtrowsp.get(i).getQuantity() * ddtrowsp.get(i).getPrice();
        }
        for (int i = 0; i < ddtrowsf.size(); i++) {

            table.addCell(getCell("", Element.ALIGN_LEFT));

            table.addCell(getCell(ddtrowsf.get(i).getDescription(), Element.ALIGN_RIGHT));
            table.addCell(getCell("" + ddtrowsf.get(i).getQuantity(), Element.ALIGN_RIGHT));
            table.addCell(getCell("" + ddtrowsf.get(i).getPrice(), Element.ALIGN_RIGHT));
            table.addCell(getCell("" + ddtrowsf.get(i).getQuantity() * ddtrowsf.get(i).getPrice(), Element.ALIGN_RIGHT));
            Totale += ddtrowsf.get(i).getQuantity() * ddtrowsf.get(i).getPrice();
        }
        doc.add(table);

        p = new Paragraph("");
        p.setAlignment(Element.ALIGN_LEFT);

        table = new PdfPTable(5);

        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);
        table.setWidths(new int[]{2, 2, 1, 2, 2});

        PdfPCell cell = new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(getCell("Imponibile: ", PdfPCell.ALIGN_RIGHT));
        table.addCell(getCell("" + Totale, PdfPCell.ALIGN_RIGHT));

        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(getCell("IVA: ", PdfPCell.ALIGN_RIGHT));
        table.addCell(getCell("22%", PdfPCell.ALIGN_RIGHT));

        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(getCell("Totale:", PdfPCell.ALIGN_RIGHT));
        Totale += (Totale * 22) / 100;
        table.addCell(getCell("" + Totale, PdfPCell.ALIGN_RIGHT));

        p.add(table);
        doc.add(p);
    }

    public PdfPCell getCell(String value, int alignment) {
        PdfPCell cell = new PdfPCell();
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        Paragraph p = new Paragraph(value);
        p.setAlignment(alignment);
        cell.addElement(p);
        return cell;
    }

    private void generateProduct(Document doc) throws BadElementException, DocumentException {
        Paragraph p;
        p = new Paragraph("Scheda Prodotto");
        doc.add(p);

        p = new Paragraph("Modello: " + vp.getModel());
        doc.add(p);
        System.out.println("ora so per iniziare la marca");
        String Marca = "";
        System.out.println("o inizializzato la marca");

        System.out.println("ho cercato la marca " + allBrand.size());
        for (int i = 0; i < allBrand.size(); i++) {
            if (allBrand.get(i).getId() == vp.getId_brand()) {
                Marca = allBrand.get(i).getName();
            }
        }

        System.out.println("ho cercato la marca " + allBrand.size());
        p = new Paragraph("Marca: " + Marca);
        doc.add(p);

        String Categoria = "";
        for (int i = 0; i < allCategories.size(); i++) {
            if (allCategories.get(i).getId() == vp.getId_category()) {
                Categoria = allCategories.get(i).getParents();
            }
        }
        p = new Paragraph("Categoria: " + Categoria);
        doc.add(p);

        p = new Paragraph("Posizione di magazzino: " + vp.getWharehouse_position());
        doc.add(p);
        p = new Paragraph("Prezzo: " + vp.getPrice() + " €");
        doc.add(p);

        BarcodeQRCode qrcode = new BarcodeQRCode("http://" + Config.Constants.HOST_IP + ":8084/Gestionale/ProductDetails.jsp?status=view&id=" + vp.getId(), 10, 10, null);
        Image image = qrcode.getImage();
        image.scaleAbsolute(150, 150);

        //Add Barcode to PDF document
        doc.add(image);

    }

    public void generateQuote(Document doc) throws DocumentException {
   //Paragrafo1 N°Fattura
        Paragraph p;
        PdfPTable datiAziendali = new PdfPTable(2);

        PdfPCell dati = new PdfPCell();
        dati.setBorder(PdfPCell.NO_BORDER);
        dati.addElement(new Paragraph(data.getRag_soc() + " di " + data.getResponsabile()));
        dati.addElement(new Paragraph(data.getIndirizzo() + " " + data.getCivico() + " " + data.getCap() + " " + data.getCity() + " " + data.getProvincia()));
        dati.addElement(new Paragraph("P.IVA" + data.getPiva()));
        dati.addElement(new Paragraph("C.F. " + data.getCodfisc()));

        dati.addElement(new Paragraph(" "));
        PdfPCell logo = new PdfPCell();
        logo.setBorder(PdfPCell.NO_BORDER);

        datiAziendali.addCell(dati);
        datiAziendali.addCell(logo);

        doc.add(datiAziendali);
        DottedLineSeparator separator = new DottedLineSeparator();
        separator.setPercentage(59500f / 523f);
        Chunk linebreak = new Chunk(separator);
        doc.add(linebreak);
        //Pragrafo 2 DAta Fattura
        p = new Paragraph("Preventivo");
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p);
        doc.add(linebreak);

        //Dati cliente e Dati Azienda
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);

        

        PdfPCell buyer = new PdfPCell();
        buyer.setBorder(PdfPCell.NO_BORDER);
        buyer.addElement(new Paragraph("Spettabile " + vP.getName()));
        buyer.addElement(new Paragraph("Indirizzo: " + vP.getAddress() + " " + vP.getCap()));
        buyer.addElement(new Paragraph("località: " + vP.getCity()));
        buyer.addElement(new Paragraph("Cod.Fisc." + vP.getCod_fisc()));
        buyer.addElement(new Paragraph("Part.Iva:" + vP.getPiva()));
        table.addCell(buyer);

        doc.add(table);
        

        

        //Tabella  Prodotti
        table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);
        table.setWidths(new int[]{2, 2, 1, 2, 2});
        table.addCell(getCell("Codice", Element.ALIGN_LEFT));
        table.addCell(getCell("Descrizione", Element.ALIGN_LEFT));
        table.addCell(getCell("Quantità", Element.ALIGN_LEFT));
        table.addCell(getCell("Prezzo unitario", Element.ALIGN_LEFT));
        table.addCell(getCell("Totale", Element.ALIGN_LEFT));
        String Modello = "";
        double Totale = 0;
        for (int i = 0; i < vqrp.size(); i++) {

            table.addCell(getCell("" + vqrp.get(i).getId_product(), Element.ALIGN_LEFT));

            table.addCell(getCell(vqrp.get(i).getDescription(), Element.ALIGN_RIGHT));
            table.addCell(getCell("" + vqrp.get(i).getQuantity(), Element.ALIGN_RIGHT));
            table.addCell(getCell("" + vqrp.get(i).getPrice(), Element.ALIGN_RIGHT));
            table.addCell(getCell("" + vqrp.get(i).getQuantity() * vqrp.get(i).getPrice(), Element.ALIGN_RIGHT));
            Totale += vqrp.get(i).getQuantity() * vqrp.get(i).getPrice();
        }
        for (int i = 0; i < vqrf.size(); i++) {

            table.addCell(getCell("", Element.ALIGN_LEFT));

            table.addCell(getCell(vqrf.get(i).getDescription(), Element.ALIGN_RIGHT));
            table.addCell(getCell("" + vqrf.get(i).getQuantity(), Element.ALIGN_RIGHT));
            table.addCell(getCell("" + vqrf.get(i).getPrice(), Element.ALIGN_RIGHT));
            table.addCell(getCell("" + vqrf.get(i).getQuantity() * vqrf.get(i).getPrice(), Element.ALIGN_RIGHT));
            Totale += vqrf.get(i).getQuantity() * vqrf.get(i).getPrice();
        }
        doc.add(table);

      
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    private void generateDrain(Document doc) throws DocumentException {
        //Tabella  Prodotti
        Paragraph p;
        DottedLineSeparator separator = new DottedLineSeparator();
        separator.setPercentage(59500f / 523f);
        Chunk linebreak = new Chunk(separator);
     
        p = new Paragraph("Scheda di scarico da magazzino");
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p);
        doc.add(linebreak);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);
        table.setWidths(new int[]{2, 2, 1, 2});
        table.addCell(getCell("Codice prodotto", Element.ALIGN_LEFT));
        table.addCell(getCell("Scansia", Element.ALIGN_LEFT));
        table.addCell(getCell("Quantità", Element.ALIGN_LEFT));
        table.addCell(getCell("Prezzo unitario", Element.ALIGN_LEFT));
        
        for (int i = 0; i < drainrows.size(); i++)
        {
            table.addCell(getCell("" + drainrows.get(i).getId_product(), Element.ALIGN_LEFT));
            table.addCell(getCell("" + drainrows.get(i).getProdloc(), Element.ALIGN_LEFT));
            table.addCell(getCell("" + drainrows.get(i).getQuantity(), Element.ALIGN_RIGHT));
            table.addCell(getCell(""+drainrows.get(i).getPrice()+"€", Element.ALIGN_RIGHT));
        }
        
        doc.add(table);
    }

}
