(ns bank-ocr-kata.core
  (:use [bank-ocr-kata.digits]))

(defn write-entry
  "Write a seq of 9 digits to 4 lines of pipe & underscore string representation"
  [digits]
  (let [last-line (apply str (repeat (* 3 (count digits)) " "))]
    (concat (apply map str (map ocr-digits digits))
            (list last-line))))

(defn print-entry
  "Print a seq of 9 digits to 4 lines of pipe & underscore string representation"
  [digits]
  (doseq [line (write-entry digits)]
    (println line)))

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
