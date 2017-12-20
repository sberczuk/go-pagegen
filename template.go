package main

import (

  "os"
  "html/template"

)

func main() {

  // OK! multiples in a template (each?)
  // Parse input file
  tmpl := template.Must(template.ParseFiles("dat"))

  data := []   struct {
    Title string
    Item string
    } {

      {
        Title: "My page",
        Item:"My photos",
      },
      {
        Title: "My book",
        Item:"My books ",
      },
    }


    tmpl.Execute(os.Stdout, data)

  }
