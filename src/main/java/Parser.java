import model.Einzelteil;
import model.Schneidplan;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    private final String ENCODING="UTF-8";

    private ArrayList<Einzelteil> parseEinzelteile(){
        return null;
    }

    private Einzelteil parseEinzelteil(){
        return null;
    }

    private Schneidplan parseSchneideplanHead(Element headertable) {
        Schneidplan schneidplan=new Schneidplan();
        //get all tablerows
        Elements tablerows= headertable.select("tr");
        //get all table cells of row
        for (Element tablerow : tablerows) {
            Elements cells=tablerow.select("td");
            //only add if the row got at least two cells
            if(cells.size()>=2){
                String trigger=cells.get(0).text();
                String content=cells.get(1).text();
                trigger=removeWeirdWhitespace(trigger);
                schneidplan.setAttribute(trigger,content);
            }

        }
        return schneidplan;

    }

    private String removeWeirdWhitespace(String string){
        return string.substring(0,string.length()-1);
    }

    public Schneidplan parseSchneidplan(String path) {
        File input = new File(path);
        Document complete_html;
        try {
            complete_html= Jsoup.parse(input,ENCODING);
        } catch (IOException e) {
            System.out.println("Datei kann nicht geladen werden!");
            e.printStackTrace();
            return null;
        }

        Elements tables= complete_html.select("table");
        //meta information from second table in document
        Element head=tables.get(1);
        //information about einzelteile from 4th table in document
        Element einzelteile=tables.get(3);

        Schneidplan schneideplan= parseSchneideplanHead(head);

        return null;
    }
}
