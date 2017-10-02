/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessFlow;

/**
 *
 * @author filippo
 */
import BusinessLogic.Interface.DrainInterface;
import BusinessLogic.Interface.DrainRowInterface;
import BusinessLogic.Interface.InvoiceInterface;
import BusinessLogic.Interface.InvoiceRowInterface;
import BusinessLogic.Interface.QuoteInterface;
import BusinessLogic.Interface.SupplyInterface;
import BusinessLogic.VirtualEntities.VirtualDrain;
import BusinessLogic.VirtualEntities.VirtualInvoice;
import BusinessLogic.VirtualEntities.VirtualInvoiceRow;
import BusinessLogic.VirtualEntities.VirtualQuote;
import BusinessLogic.VirtualEntities.VirtualSupply;
import Services.Database.Database;
import Services.Database.DatabaseService;
import Services.Exception.PersonalizedException;
import java.util.List;
import javax.servlet.http.Cookie;

/**
 *
 * @author filippo
 *
 */
public class AdminArea {

    private Database db;
    private Login lg;

    private VirtualInvoice vinvoice;
    private List<InvoiceInterface> invoices;  
    private VirtualInvoiceRow invoicerow;
    private List<InvoiceRowInterface> invoicerowsp;    ;
    private List<InvoiceRowInterface> invoicerowsf;

    private VirtualDrain vdrain;
    private List<DrainInterface> drains;    
    private List<DrainRowInterface> drainrows;
    

    private VirtualSupply vsupply;
    private List<SupplyInterface> supplies;
    
    private VirtualQuote vQuote;
    private List<QuoteInterface> quotes;
    
    public double Fatturato,fc,nfat,nprev,nfatpag;
    
    private int result;

    public AdminArea() {

    }

    public Boolean isLoggeOn(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vinvoice=new VirtualInvoice(db);
                invoices=vinvoice.getAll();
                invoicerow=new VirtualInvoiceRow(db);
                invoicerowsp=invoicerow.getAllRowProduct(result);
                invoicerowsf=invoicerow.getAllRowFree(result);
                
                vdrain=new VirtualDrain(db);
                drains=vdrain.getAll();
                
                vsupply=new VirtualSupply(db);
                supplies=vsupply.getAll();
                vQuote=new VirtualQuote(db);
                quotes=vQuote.getAll();
                System.out.println("Faccio i calcoli");
                calcoli();
                
                db.commit();
                db.close();
                
                return true;
            } else {
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

    public Login getLg() {
        return lg;
    }

    public void setLg(Login lg) {
        this.lg = lg;
    }
    
    private void calcoli()
    {System.out.println("sono dentro  i calcoli");
        // calcolo del fatturato 
        /*
        Sommo tutte le righe delle fatture (senza iva) poi ci aggiungo l'iva         
        */
        double Totale=0;
        for(int i=0;i<invoicerowsp.size();i++)
        {
            Totale+=invoicerowsp.get(i).getPrice()*invoicerowsp.get(i).getQuantity();
        }
        for(int i=0;i<invoicerowsf.size();i++)
        {
            Totale+=invoicerowsf.get(i).getPrice()*invoicerowsf.get(i).getQuantity();
        }
        Totale+=(Totale*22)/100;
        Fatturato=Totale;
        
        //Calcolo de fatturato - i carichi 
        double TotaleCarichi=0;
        for(int i=0; i<supplies.size();i++)
        {
            TotaleCarichi+=supplies.get(i).getQuantity()*supplies.get(i).getProvider_unit_price();
        }
        fc=Fatturato-TotaleCarichi;
        
        
        //numero di fatture emesse
        nfat=invoices.size();
        //numero preventivi 
        nprev=quotes.size();
        
        
        for(int i=0;i<invoices.size();i++)
        {
            if(invoices.get(i).getPayed())
            {
                nfatpag++;
            }
        }
    }

}
