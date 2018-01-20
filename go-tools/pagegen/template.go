package main

import (
	"encoding/json"
	"flag"
	"fmt"
	"html/template"
	"io/ioutil"
	"os"
)

type Event struct {
	Venue        string `json:"venue"`
	Date         string `json:"date"`
	parsedDate   string `json:"parsedDate"`
	Title        string `json:"title"`
	Description  string `json:"description"`
	VenueLink    string `json:"venueLink"`
	DocumentLink string `json:"documentLink"`

	// JSON seems to want attribs to be upper case?
}

var jsonFile = flag.String("data", "events.json", "JSON File to use as input.")
var templateFile = flag.String("template", "html-template", "HTML Template.")
var outFile = flag.String("out", "out.html", "Output File File.")

func main() {
	flag.Parse()

	// extension: Buid a template from parts, then parse the data
	tmpl := template.Must(template.ParseFiles(*templateFile))
	data, err := ioutil.ReadFile(*jsonFile)
	if err != nil {
		fmt.Println(err.Error())
		os.Exit(1)
	}
	var events []Event
	json.Unmarshal(data, &events)
	file, err := os.Create(*outFile)
	tmpl.Execute(file, events)

	fmt.Printf("Results saved to file %s\n", *outFile)

}
