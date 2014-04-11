(ns bank-ocr-kata.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:use [bank-ocr-kata.digits]))

(defn illegible
  "Turns text into a number representing the illegible OCR digit"
  [digit]
  (let [pos-order (apply str (reverse (apply str (.charAt (first digit) 1) (drop 1 digit))))]
    (-> pos-order
        (.replaceAll "_" "1")
        (.replaceAll "\\|" "1")
        (.replaceAll " " "0")
        (Integer/parseInt 2))))

(defn read-digit
  [lines]
  "Returns a double of the digit read and the remaining parts of the strings"
  (let [digit (map #(.substring % 0 3) (butlast lines))
        rest (map #(.substring % 3) lines)
        match-digit (fn [n] (= digit (ocr-digits n)))
        matched (first (filter match-digit (range (inc 9))))]
    [(or matched (illegible digit))
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
  (and (every? #(and (>= % 0) (<= % 9)) account-number)
   (let [sum (apply + (map * (reverse account-number) (range 1 (inc (count account-number)))))]
     (zero? (mod sum 11)))))

(defn attempt-repair
  "Given an invalid or illegible account #, try to find a valid
   account given how the scanner might make an error"
  [account-number]
  (->>
   (mapcat (fn [n]
             (let [digit (get account-number n)
                   mightbe (disj (could-be (segment digit)) (segment digit))]
               (map #(assoc account-number n (digits-segment %)) mightbe)))
           (range (count account-number)))
   (filter valid-account-number?)
   (set)))

(defn write-findings
  "Produce a file with the account numbers read, noting any errors or illegibilities"
  [account-numbers filename]
  (with-open [out (io/writer filename)]
    (binding [*out* out]
      (doseq [account-number account-numbers]
        (let [format-amb (fn [mightbe-set]
                           (apply str (interpose ", " (map #(str "'" (apply str %) "'") (sort mightbe-set)))))
              [account-number' status] (cond
                                        (some #(> % 9) account-number) (let [might-be (attempt-repair (vec account-number))]
                                                                         (cond
                                                                          (empty? might-be) [(map #(if (> % 9) '? %) account-number) "ILL"]
                                                                          (= 1 (count might-be)) [(first might-be) ""]
                                                                          :else [(map #(if (> % 9) '? %) account-number) (str "AMB [" (format-amb might-be) "]")]))

                                        (not (valid-account-number? account-number)) (let [might-be (attempt-repair (vec account-number))]
                                                                                       (cond
                                                                                        (empty? might-be) [account-number "ERR"]
                                                                                        (= 1 (count might-be)) [(first might-be) ""]
                                                                                        :else [account-number (str "AMB [" (format-amb might-be) "]")]))
                                        :else [account-number ""])]
          (println (apply str account-number') status))))))


