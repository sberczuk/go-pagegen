package main


// 1. Parse the XML file and make a JSON file
// 2. use the JSON To make a template
import (

  "encoding/json"
  "encoding/xml"
  "fmt"
  "os"
  "html/template"
  "io/ioutil"
  "log"
  //"os"
)


// type XmlBook struct {
//   XMLName xml.Name xml:"books" json "-"
//   BookList []Book `xml:"book" json:"book"`
//
//   // JSON seems to want attribs to be upper case?
// }
type Books struct{
  Book []Book `xml:"book"`
}

type Book struct {
  Title string `xml:"title" json:title`
  Isbn string `xml:"isbn"`
  Authors string `xml:"authors"`
  ImageURL string `xml:imageURL`
  Comments string `xml:comments`
  // JSON seems to want attribs to be upper case?
}


func main() {
  var m Book;
  // load from xml
  data2, err := ioutil.ReadFile("books.xml")
  if err != nil {
    log.Fatal(err)
  }
  //fmt.Printf("File contents: %s", data2)


  var data Books
  xml.Unmarshal([]byte(data2), &data)
  jsonData, _ := json.Marshal(data)
  fmt.Printf("Raw JSON %s\n",string(jsonData))
  err2 := json.Unmarshal(jsonData, &m)
  if err != nil {
    log.Fatal(err2)
  }
  fmt.Println (err)
  fmt.Printf("json: %s\n", m)
  tmpl := template.Must(template.ParseFiles("html-template"))
  tmpl.Execute(os.Stdout, m)


}
