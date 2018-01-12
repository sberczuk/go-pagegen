import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.json.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HtmlParser {
    public static void main(String[] args) throws IOException {
        File input = new File("/Users/sberczuk/code/personal/go-pagegen/events/events.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
        HtmlParser htmlParser = new HtmlParser();
        JsonArray jsonObject = htmlParser.processEvents(doc);
        System.out.println(jsonObject);
    }

    public JsonArray processEvents(Document doc){
        Elements rows = doc.getElementsByTag("tr");
        List<Event> events = new ArrayList<>();
        for (Element row : rows){
             //System.out.println(row);
            if(row.children().size() ==3 ) {
                Event event = processEventRow(row);
                events.add(event);

            } else{
                System.out.println("NO");
            }
        }
       return  buildJsonObject(events);
    }

    private JsonArray buildJsonObject(List<Event> events) {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonArrayBuilder arrayBuilder = factory.createArrayBuilder();
        //JsonArray value = arrayBuilder
        for (Event event : events) {
            arrayBuilder.add(factory.createObjectBuilder().add("date", event.getDate())
                    .add("description", event.getDescription())
                    .add("venue", event.getVenue())
                    .add("venueLink", event.getVenueLink())
                    .add("documentLink",event.getDocumentLink()));
        }

   return arrayBuilder.build();
    }

    private  Event processEventRow(Element row) {
        Event event = new Event();
        Element venueTD = row.child(0);
        event.setVenue(venueTD.ownText());
        processVenueRow(venueTD);
        Element dateTD = row.child(1);
        event.setDate(dateTD.ownText());
        processDateRow(dateTD);
        Element descriptionTD = row.child(2);
        event.setDescription(descriptionTD.ownText());
        processDescription(descriptionTD);
        return event;
    }

    private void processDescription(Element descriptionTD) {
    }

    private void processDateRow(Element dateTD) {
        
    }

    private  void processVenueRow(Element venueTD) {

    }
}
