package main

// 1. Parse the XML file and make a JSON file
// 2. use the JSON To make a template
import (
	"encoding/json"
	"encoding/xml"
	"fmt"
	"html/template"
	"io/ioutil"
	"log"
	"os"
	//"os"
)

// type XmlBook struct {
//   XMLName xml.Name xml:"books" json "-"
//   BookList []Book `xml:"book" json:"book"`
//
//   // JSON seems to want attribs to be upper case?
// }
type Books struct {
	Books []Book `xml:"book" json:book`
}

type Book struct {
	Title    string `xml:"title" json:title`
	Isbn     string `xml:"isbn"`
	Authors  string `xml:"authors"`
	ImageURL string `xml:imageURL`
	Comments string `xml:comments`
	// JSON seems to want attribs to be upper case?
	// how to map to lower case?
}

func main() {
	var bookRoot Book
	// load bytes from xml file
	bookXmlBytes, err := ioutil.ReadFile("books.xml")
	if err != nil {
		log.Fatal(err)
	}

	// parse the xml byte stream into an XML Structure
	var bookStruct Books
	xml.Unmarshal([]byte(bookXmlBytes), &bookStruct)

	// marshall the data structure to JSON
	jsonData, _ := json.Marshal(bookStruct)
	fmt.Printf("Raw JSON %s\n", string(jsonData))
	err2 := json.Unmarshal(jsonData, &bookRoot)
	ioutil.WriteFile("bookdata.json", jsonData, 0666)

	if err != nil {
		log.Fatal(err2)
	}
	fmt.Printf("error is %s\n", err)

	// parse json file
	bookJsonBytes, err := ioutil.ReadFile("bookdata.json")

	tmpl := template.Must(template.ParseFiles("html-template"))

	fmt.Println(bookStruct.Books[2].Title)

	fmt.Println("template:")

	tmpl.Execute(os.Stdout, bookStruct.Books)

}
