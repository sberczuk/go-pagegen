import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class HtmlParser {
    public static void main(String[] args) throws IOException {
        File input = new File("/Users/sberczuk/code/personal/go-pagegen/events/events.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
        HtmlParser htmlParser = new HtmlParser();
        htmlParser.processEvents(doc);
    }

    public void processEvents(Document doc){
        Elements rows = doc.getElementsByTag("tr");
        for (Element row : rows){
            //System.out.println(row);
            if(row.children().size() ==3 ) {
                processEventRow(row);
            } else{
                System.out.println("NO");
            }
        }
    }

    private  void processEventRow(Element row) {
        Element venueTD = row.child(0);
        processVenueRow(venueTD);
        Element dateTD = row.child(1);
        processDateRow(dateTD);
        Element descriptionTD = row.child(2);
        processDescription(descriptionTD);
    }

    private void processDescription(Element descriptionTD) {
    }

    private void processDateRow(Element dateTD) {
        
    }

    private  void processVenueRow(Element venueTD) {

    }
}
