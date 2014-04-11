(ns bank-ocr-kata.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:use [bank-ocr-kata.digits]))

(defn read-digit
  [lines]
  "Returns a double of the digit read and the remaining parts of the strings"
  (let [digit (map #(apply str (take 3 %)) (butlast lines))
        rest (map #(.substring % 3) lines)
        match-digit (fn [n] (= digit (ocr-digits n)))
        matched (first (filter match-digit (range (inc 9))))]
    [(or matched '?)
     rest]))

(defn read-entry
  "Read a 9-digit from 4 lines of a pipe & underscore representation"
  [lines]
  (drop 1 (take (inc 9) (map first (iterate (fn [[_ lines]] (read-digit lines)) [:ignored lines]))))) 

(defn read-file
  "Read a file with account entries in pipe & underscore form, and return list of account numbers"
  [filename]
  (->> filename
       slurp
       str/split-lines
       (partition 4)
       (map read-entry)))

(defn valid-account-number?
  [account-number]
  (let [sum (apply + (map * (reverse account-number) (range 1 (inc (count account-number)))))]
    (zero? (mod sum 11))))

(defn write-findings
  "Produce a file with the account numbers read, noting any errors or illegibilities"
  [account-numbers filename]
  (with-open [out (io/writer filename)]
    (binding [*out* out]
      (doseq [account-number account-numbers]
        (let [illegible? (some #(= % '?) account-number)
              status (if illegible?
                       "ILL"
                       (if (not (valid-account-number? account-number))
                         "ERR"
                         ""))]
          (println (apply str account-number) status))))))
