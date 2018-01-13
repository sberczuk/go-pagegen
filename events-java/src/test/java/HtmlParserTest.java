import jdk.nashorn.internal.ir.annotations.Ignore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HtmlParserTest {

    private HtmlParser htmlParser;

    @BeforeEach
    void setUp() {
        htmlParser = new HtmlParser();
    }

    @Test
    void processEvents() {
    }

    @Test
    void processDescription(){
        String html = "<td>Adopt Before you Adapt. In Boston. <a href=\"adc2013/sberczuk-AW7-2013-Handouts-final.pdf\">Handouts</a> </td>";
        Document doc = Jsoup.parseBodyFragment(html);
        Element td = doc.body();
        assertEquals(1, td.getElementsByTag("a").size());
        HtmlParser htmlParser = this.htmlParser;
        Event event = new Event();
        htmlParser.processDescription(td,event);
        assertEquals("Adopt Before you Adapt. In Boston.", event.getDescription());
        assertEquals(event.getDocumentLink(),"adc2013/sberczuk-AW7-2013-Handouts-final.pdf");
        assertEquals(event.getDocumentLinkText(),"Handouts");
    }

    @Test
    void processEventRow(){
        String html="<tr>\n" +
                "<td>Agile Development Practices</td>\n" +
                "<td>November 2013</td>\n" +
                "<td>Adopt Before you Adapt. In Boston. <a href=\"adc2013/sberczuk-AW7-2013-Handouts-final.pdf\">Handouts</a> </td>\n" +
                "</tr>";

        Document doc = Jsoup.parse(wrapTestData(html));
        Element tr = doc.getElementsByTag("tr").first();
        Event event = htmlParser.processEventRow(tr);
        assertEquals(event.getDocumentLink(),"adc2013/sberczuk-AW7-2013-Handouts-final.pdf");
        assertEquals(event.getDocumentLinkText(),"Handouts");

    }
    private String wrapTestData(String data){
        return "<body><table>"+data+"</table></body>";
    }
}