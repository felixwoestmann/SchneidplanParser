package processing;

import model.Einzelteil;
import model.Parsable;
import model.Schneidplan;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Parser {
    private final String ENCODING = "UTF-8";

    /**
     * Parse the Information out of the Einzelteile section of the table!
     * @param einzelteile
     * @return
     */
    private ArrayList<Einzelteil> parseEinzelteile(Element einzelteile) {
        ArrayList<Einzelteil> einzelteilList=new ArrayList<>();
        /*
           We have to discriminate between different Einzeltiele in the table.
           We do that by assuming, that a new Einzelteil starts when there are three table cells in a table row.
           We collect all rows in the subtable until a new row with three cells start.
         */
        ArrayList<Element> subtable=new ArrayList<>();
        Elements tablerows =  einzelteile.select("tr");

        for(Element tablerow:tablerows) {
            //does new table of einzelteile start ?
            if(tablerow.childNodeSize()==3){
                //new table, but list is empty? just add elements
                if(subtable.isEmpty()){
                    subtable.add(tablerow);
                }else{
                //new table, but list is filled? Parse list and empty list
                    einzelteilList.add(parseEinzelteil(subtable));
                    subtable=new ArrayList<>();
                }
            }else {
                subtable.add(tablerow);
            }
        }
        return einzelteilList;
    }

    private Einzelteil parseEinzelteil(ArrayList<Element> tablerows) {
        Einzelteil einzelteil=new Einzelteil();
        tablerows.parallelStream().forEach(element -> processTableRow(einzelteil,element));
        //TODO parralelstream thread safe?
        return null;
    }

    /**
     * Parses a tablerow and calls setAttribute on the given parsable
     * @param parsable
     * @param tablerow
     */
    private void processTableRow(Parsable parsable,Element tablerow) {
        Elements cells = tablerow.select("td");
        //only add if the row got at least two cells
        if (cells.size() >= 2) {
            String trigger = cells.get(0).text();
            String content = cells.get(1).text();
            trigger = removeWeirdWhitespace(trigger);
            parsable.setAttribute(trigger, content);
        }
    }

    /**
     * Parse the information out of the header of a Schneidplan.
     * @param headertable
     * @return
     */
    private Schneidplan parseSchneideplanHead(Element headertable) {
        Schneidplan schneidplan = new Schneidplan();
        //get all tablerows
        Elements tablerows = headertable.select("tr");
        //get all table cells of row
        for (Element tablerow : tablerows) {
         processTableRow(schneidplan,tablerow);
        }
        return schneidplan;

    }

    /**
     * From Table extracted phrases semm to have an whitespace, which won't correspond to the normal whitespace ascii code.
     * Therefore this method exists.
     * @param string
     * @return
     */
    private String removeWeirdWhitespace(String string) {
        return string.substring(0, string.length() - 1);
    }

    public Schneidplan parseSchneidplan(String path) {
        File input = new File(path);
        Document complete_html;
        try {
            complete_html = Jsoup.parse(input, ENCODING);
        } catch (IOException e) {
            System.out.println("Datei kann nicht geladen werden!");
            e.printStackTrace();
            return null;
        }

        Elements tables = complete_html.select("table");
        //meta information from second table in document
        Element head = tables.get(1);
        //information about einzelteile from 4th table in document
        Element einzelteile = tables.get(3);

        Schneidplan schneideplan = parseSchneideplanHead(head);
      //  schneideplan.setEinzelteile(parseEinzelteile(Element einzelteile));
        return schneideplan;
    }
}
