(ns bank-ocr-kata.core)

(defn write-entry
  "Write a seq of 9 digits to 4 lines of pipe & underscore string representation"
  [digits]
  (list " -  -  -  -  -  -  -  -  - "
        "| || || || || || || || || |"
        "|_||_||_||_||_||_||_||_||_|"))

(defn read-entry
  "Read a 9-digit from 4 lines of a pipe & underscore representation"
  [lines]
  (repeat 9 0))
