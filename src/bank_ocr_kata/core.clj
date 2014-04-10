(ns bank-ocr-kata.core
  (:use [bank-ocr-kata.digits]))

(defn read-digit
  [lines]
  "Returns a double of the digit read and the remaining parts of the strings"
  (let [digit (map #(apply str (take 3 %)) (butlast lines))
        rest (map #(.substring % 3) lines)
        match-digit (fn [n] (= digit (ocr-digits n)))]
    [(first (filter match-digit (range (inc 9))))
     rest]))

(defn read-entry
  "Read a 9-digit from 4 lines of a pipe & underscore representation"
  [lines]
  (drop 1 (take (inc 9) (map first (iterate (fn [[_ lines]] (read-digit lines)) [:ignored lines]))))) 

(defn read-file
  "Read a file with account entries in pipe & underscore form, and return list of account numbers"
  [filename]
  (throw (UnsupportedOperationException. "Not yet implemented")))
