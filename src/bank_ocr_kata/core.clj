(ns bank-ocr-kata.core
  (:use [bank-ocr-kata.digits]))

(defn write-entry
  "Write a seq of 9 digits to 4 lines of pipe & underscore string representation"
  [digits]
  (let [last-line (apply str (repeat (* 3 (count digits)) " "))]
    (concat (apply map str (map ocr-digits digits))
            (list last-line))))

(defn read-entry
  "Read a 9-digit from 4 lines of a pipe & underscore representation"
  [lines]
  (let [digit (map #(apply str (take 3 %)) (butlast lines))
        match-digit (fn [n] (= digit (ocr-digits n)))]
    (repeat 9 (first (filter match-digit (range 9))))))
