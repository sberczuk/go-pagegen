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
        Elements rows = doc.getElementsByTag("tr");
        for (Element row : rows){
            System.out.println(row);
            if(row.children().size() >2 ) {
                System.out.println(">" + row.child(0));
                System.out.println(">>" + row.child(1));
                System.out.println(">>" + row.child(2));
            } else{
                System.out.println("NO");
            }
        }
    }
}
