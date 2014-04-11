(ns bank-ocr-kata.digits)

(def ocr-digits
  [
   ;; ocr-0
   [" _ "
    "| |"
    "|_|"]

   ;; ocr-1
   ["   "
    "  |"
    "  |"]

   ;; ocr-2
   [" _ "
    " _|"
    "|_ "]

   ;; ocr-3
   [" _ "
    " _|"
    " _|"]

   ;; ocr-4
   ["   "
    "|_|"
    "  |"]

   ;; ocr-5
   [" _ "
    "|_ "
    " _|"]

   ;; ocr-6
   [" _ "
    "|_ "
    "|_|"]

   ;; ocr-7
   [" _ "
    "  |"
    "  |"]

   ;; ocr-8
   [" _ "
    "|_|"
    "|_|"]

   ;; ocr-9
   [" _ "
    "|_|"
    " _|"]

   ])

(defmacro def-digits [digits-vec]
  `(do ~@(map (fn [n]
                `(def ~(symbol (str "ocr-" n)) (~digits-vec ~n)))
              (range (inc 9)))))

(def-digits ocr-digits)

;; Positions of a digit:
;; 012
;; 345
;; 678

;; pos  8 7 6  5 4 3 1
;; 0    1 1 1  1 0 1 1  0x7B
;; 1    1 0 0  1 0 0 0  0x48
;; 2    0 1 1  1 1 0 1  0x3D
;; 3    1 1 0  1 1 0 1  0x6D
;; 4    1 0 0  1 1 1 0  0x4E
;; 5    1 1 0  0 1 1 1  0x67
;; 6    1 1 1  0 1 1 1  0x77
;; 7    1 0 0  1 0 0 1  0x49
;; 8    1 1 1  1 1 1 1  0x7F
;; 9    1 1 0  1 1 1 1  0x6F

(def segment-digits {0 0x7B
                     1 0x48
                     2 0x3D
                     3 0x6D
                     4 0x4E
                     5 0x67
                     6 0x77
                     7 0x49
                     8 0x7F
                     9 0x6F})

(def digits-segment (apply hash-map (mapcat reverse segment-digits)))

(defn segment
  "Returns the binary representation of a numeric digit. A 7 bit number representing what segments are 'lit up'"
  [n]
  (get segment-digits n))

(defn could-be
  "Returns all of the digits (in segment-form) that the given one
   could have been if a single pipe or underscore had been missed by
   the scanner.

   digit should be a segment representation of a digit"
  [seg]
  (let [valid? (set (vals segment-digits))]
    (->> (map #(bit-or seg (bit-shift-left 1 %)) (range 7))
         (filter valid?)
         (reduce conj #{seg}))))
