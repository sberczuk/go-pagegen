package main

import (
	"encoding/json"
	"fmt"
	"html/template"
	"io/ioutil"
	"os"
)

type Event struct {
	Venue        string `json:"venue"`
	Date         string `json:"date"`
	Description  string `json:"description"`
	VenueLink    string `json:"venueLink"`
	DocumentLink string `json:"documentLink"`

	// JSON seems to want attribs to be upper case?
}

func main() {

	// OK! multiples in a template (each?)
	// Parse input file
	tmpl := template.Must(template.ParseFiles("html-template"))
	data, err := ioutil.ReadFile("./events.json")
	if err != nil {
		fmt.Println(err.Error())
		os.Exit(1)
	}
	var events []Event
	json.Unmarshal(data, &events)
	//fmt.Println(events) // works!

	tmpl.Execute(os.Stdout, events)

}
