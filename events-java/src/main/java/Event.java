public class Event {

    public String getDocumentLinkText() {
        return documentLinkText;
    }

    public void setDocumentLinkText(String documentLinkText) {
        this.documentLinkText = documentLinkText;
    }

    //later refactor to have this in a class
    static class EventLink{
        public String href="";
        public String linkText="";
    }
    private String venue;
    private String venueLink="";
    private String date="";
    private String description="";
    private String documentLink="";
    private String documentLinkText="";

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getVenueLink() {
        return venueLink;
    }

    public void setVenueLink(String venueLink) {
        this.venueLink = venueLink;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocumentLink() {
        return documentLink;
    }

    public void setDocumentLink(String documentLink) {
        this.documentLink = documentLink;
    }
}
