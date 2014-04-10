(ns bank-ocr-kata.test-utils
  (:require [clojure.java.io :as io]
            [clojure.pprint :as pprint])
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

(defn rand-acct
  "Generated a random 9 digit account #"
  []
  (take 9 (repeatedly #(rand-int (inc 9)))))

(defn random-length-ocr-file
  "Generate an ocr file with n-entries in it. Account #s are randomly generated"
  [n-entries filename]
  (random-ocr-file (take n-entries (repeatedly rand-acct)) filename))

(defn random-ocr-file
  "Generate an ocr file with the given entries in it"
  [entries filename]
  (with-open [out (io/writer filename)]
    (binding [*out* out]
      (doseq [entry entries]
        (print-entry entry)))))


(defn read-clj
  "Reads a file as a serialized/readable Clojure datastructure"
  [filename]
  (with-open [in (io/reader filename)] (read in)))

(defn write-clj
  "(Pretty) Prints a Clojure datastructure to a file"
  [x filename]
  (with-open [out (io/writer filename)] (pprint/pprint x out)))


(defn ocr-input-ans-pair
  "Generate a .input + .ans files for use in tests"
  [n-entries fileprefix]
  (let [accts (take n-entries (repeatedly rand-acct))]
    (random-ocr-file accts (str fileprefix ".input"))
    (write-clj accts (str fileprefix ".ans"))))
