package main

import (

  "encoding/json"
 "fmt"
 //"os"
)




type Attributes struct {
  Title string
  Isbn string
  Authors string
  ImageURL string
  Comments string
  // JSON seems to want attribs to be upper case?

}
type Book struct {
  Btype string
  Id string
  Attributes Attributes
}





                func main() {
                  var m Book;

                   books :=  []byte(
                     `{  "btype": "book",  "id": "0547391404",

            "attributes": {
                "title": "The Storytelling Animal",
                "isbn": "0547391404",
                "authors": "Jonathan Gottschall",
                "imageURL": "cover/0547391404.jpg",

                "comments": "Jonathan Gottshcall discusses why stories are so pervasive in our lives, in a well written, compelling book that explores the science, history, and future of stories and storytelling. Among other things, the book covers why children and adults create and consume fiction, the science of dreams, the role of stories in influencing (and defining) history, and what technology means for the future of stories. Not just full of interesting facts, many chapters start out in the manner of a compelling story, drawing you into learning about the science and history of story telling, proving the point stories are a great way to learn. Many of my favorite technical books interlace stories with technical details. If you are curious about how the mind works, and in the science and role of stories, or even just want some great examples of how to use story to make a point, read this book. (This review is based on a pre-publication review copy).\n    ",
                "seeAlso": [
                    "0201741571",
                    "020172183x",
                    "0932633021",
                    "0932633161"
                ]
              }
           }`)

                  err := json.Unmarshal(books, &m)
                 fmt.Println (err)
                 fmt.Println (m)
                 fmt.Println (m.Attributes.Title)



                }
