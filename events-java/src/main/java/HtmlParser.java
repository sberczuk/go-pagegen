import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HtmlParser {
    public static void main(String[] args) throws IOException {
        File input = new File("./events.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
        HtmlParser htmlParser = new HtmlParser();
        JsonArray jsonObject = htmlParser.processEvents(doc);
        final Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        final StringWriter stringWriter = new StringWriter();
        JsonWriter writer = writerFactory.createWriter(stringWriter);
        writer.write(jsonObject);
        writer.close();
        String jsonString = stringWriter.toString();
        System.out.println(jsonString);
        FileWriter fileWriter = new FileWriter("events.json");
        fileWriter.write(jsonString);
        fileWriter.close();

    }

    public JsonArray processEvents(Document doc) {
        Elements rows = doc.getElementsByTag("tr");
        List<Event> events = new ArrayList<>();
        for (Element row : rows) {
            if (row.children().size() == 3) {
                Event event = processEventRow(row);
                events.add(event);

            } else {
                System.out.println("NO");
            }
        }
        return buildJsonObject(events);
    }

    private JsonArray buildJsonObject(List<Event> events) {
        // sort events
       events.sort(new Comparator<Event>() {
           @Override
           public int compare(Event o1, Event o2) {
               return -o1.getParsedDate().compareTo(o2.getParsedDate());
           }
       });
       JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonArrayBuilder arrayBuilder = factory.createArrayBuilder();
        //JsonArray value = arrayBuilder
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        for (Event event : events) {
            arrayBuilder.add(factory.createObjectBuilder()
                    .add("date", event.getDate())
                    .add("parsedDate", simpleDateFormat.format(event.getParsedDate()))
                    .add("description", event.getDescription())
                    .add("title", event.getDescription())
                    .add("venue", event.getVenue())
                    .add("venueLink", event.getVenueLink())
                    .add("documentLink", event.getDocumentLink()));
        }

        return arrayBuilder.build();
    }

    Event processEventRow(Element row) {
        Event event = new Event();
        Element venueTD = row.child(0);

        processVenueElement(venueTD, event);

        Element dateTD = row.child(1);

        processDateRow(dateTD, event);
        Element descriptionTD = row.child(2);
        processDescription(descriptionTD, event);
        return event;
    }

    void processDescription(Element descriptionTD, Event event) {
        event.setDescription(descriptionTD.ownText());
        Elements anchors = descriptionTD.getElementsByTag("a");
        if (anchors.size() > 0) {
            event.setDocumentLink(anchors.attr("href"));
            event.setDocumentLinkText(anchors.text());
        }

    }

    private void processDateRow(Element dateTD, Event event) {
        String dateText = dateTD.ownText();
        event.setDate(dateText);
        DateFormat df = new SimpleDateFormat("MMMMM yyyy");
        try {
            Date parse = df.parse(dateText);
            event.setParsedDate(parse);
        } catch (ParseException e) {
            event.setParsedDate(new Date());
            e.printStackTrace();
        }

    }

    private void processVenueElement(Element venueTD, Event event) {
        String venueName = venueTD.ownText();
        Elements anchors = venueTD.getElementsByTag("a");
        if (anchors.size() > 0) {
            event.setVenueLink(anchors.attr("href"));
            venueName = anchors.text();
        }
        event.setVenue(venueName);

    }
}
