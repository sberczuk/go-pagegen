package main

// 1. Parse the html file and make a JSON file
// 2. use the JSON To make a template
// see https://godoc.org/golang.org/x/net/html
import (
	"bytes"
	"encoding/xml"
	"fmt"
	"io"
	"log"
	"os"
	"strings"
	//"golang.org/x/net/html"
	//"os"
)

// new plan;
// get the entire content of the second TD
// if td=2 collect all content, (raw?)

type Event struct {
	venueName string
	// text or alt text
	eventDate        string
	eventLogo        string
	venueURL         string
	eventDescription string
	presentationURL  string

	// JSON seems to want attribs to be upper case?
}

func main() {
	// load bytes from xml file
	//bookXmlBytes, err := ioutil.ReadFile("events.html")
	f, err := os.Open("events.html")
	if err != nil {

	}

	//z := html.NewTokenizer(r)
	dec := xml.NewDecoder(f)
	if err != nil {
		log.Fatal(err)
	}
	// map event
	// TR = event
	// first TD = venue
	// next TD= description
	var currElement string
	var buffer bytes.Buffer

	var inRow bool
	var inData bool
	var tdCount = 0
	//var events []Event
	for {
		tok, err := dec.Token()

		if err == io.EOF {
			break
		} else if err != nil {
			fmt.Printf("xml %v\n", err)
			os.Exit(2)
		}
		//var currentEvent Event
		//var e Event
		var dataKey string
		//var dataValue string
		switch tok := tok.(type) {
		case xml.StartElement:
			currElement = tok.Name.Local
			if currElement == "tr" {

				fmt.Println("\nNew Event")
				// create a new event struct
				//currElement = Event()

				inRow = true
				tdCount = 0
			} else if currElement == "td" {

				tdCount++
				fmt.Println(tdCount)
				if tdCount == 1 {
					//fmt.Println("\nVENUE -----")
					// skip
				} else if tdCount == 2 {
					inData = true
					//fmt.Println("\nDETAILS -----")
				} else {

				}
			}

			if inData {
				for _, item := range tok.Attr {
					// parse href -> appropriate URL
					// descriptoin -> text
					// hand code dates
					//var dataKey string
					fmt.Println(buffer.WriteString(item.Value))
					//fmt.Println(buffer.String())
					switch item.Name.Local {
					case "href":
						if tdCount == 1 {
							dataKey = "venueURL"
						} else if tdCount == 2 {
							dataKey = "eventURL"
						}
					case "src":
						if tdCount == 1 {
							dataKey = "venueImage"
						} else if tdCount == 2 {
							dataKey = "eventImage"
						}
					case "alt":
						if tdCount == 1 {
							dataKey = "venueName"
						} else if tdCount == 2 {
							dataKey = "??"
						}

					case "title":
						if tdCount == 1 {
							dataKey = "venueName"
						} else if tdCount == 2 {
							dataKey = "eventTitle"
						}
					}
					//fmt.Println("*>" + dataKey + "=" + dataValue)
					//buffer.WriteString("*>" + dataKey + "=" + dataValue)

					//do something with i,v
				}
			}

		case xml.EndElement:

			//currElement = ""
			currElement = tok.Name.Local
			if currElement == "tr" {
				inRow = false
				// push event to list

			} else if currElement == "td" {

				if tdCount == 1 {
					fmt.Println("\n END VENUE -----")
				} else if tdCount == 2 {
					inData = false
					fmt.Println(dataKey)
					fmt.Printf("\n%s  %s\n", "%%%>eventDescription", buffer.String())
					buffer.Reset()
					fmt.Println("\nEND DETAILS -----")
				}
			}

			//println(tok.Name.Local)
		case xml.CharData:
			// strip newline etc
			if inRow {
				//fmt.Print("\n>>>" + string(tok))
			}
			if inData {
				if tdCount == 1 {
					buffer.WriteString(string(tok))
					//fmt.Printf("\n%s  %s\n", "*>eventVenue", string(tok))
				} else if tdCount == 2 {
					processedString := strings.Replace(string(tok), "\n", " ", -1)
					buffer.WriteString(processedString)
					//fmt.Printf("\n%s  %s\n", "++>eventDescription", buffer.String())
				} else {
					fmt.Printf("\n%s  %s\n", "??", string(tok))

				}

			}
		}
	}

}
