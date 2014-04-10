(ns bank-ocr-kata.digits)

(def ocr-digits
  [
   ;; ocr-0
   [" - "
    "| |"
    "|_|"]

   ;; ocr-1
   ["   "
    "  |"
    "  |"]

   ;; ocr-2
   [" - "
    " _|"
    "|_ "]

   ;; ocr-3
   [" - "
    " _|"
    " _|"]

   ;; ocr-4
   ["   "
    "|_|"
    "  |"]

   ;; ocr-5
   [" - "
    "|_ "
    " _|"]

   ;; ocr-6
   [" - "
    "|_ "
    "|_|"]

   ;; ocr-7
   [" - "
    "  |"
    "  |"]

   ;; ocr-8
   [" - "
    "|_|"
    "|_|"]

   ;; ocr-9
   [" - "
    "|_|"
    " _|"]

   ])

(defmacro def-digits [digits-vec]
  `(do ~@(map (fn [n]
                `(def ~(symbol (str "ocr-" n)) (~digits-vec ~n)))
              (range (inc 9)))))

(def-digits ocr-digits)
