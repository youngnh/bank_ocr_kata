(ns bank-ocr-kata.core-test
  (:require [clojure.test :refer :all]
            [bank-ocr-kata.test-utils :refer :all]
            [bank-ocr-kata.core :refer :all]
            [bank-ocr-kata.digits :refer :all]))

(deftest test-write-entry
  (testing "00"
    (is (= (list " _  _ "
                 "| || |"
                 "|_||_|"
                 "      ")
           (write-entry (repeat 2 0)))))

  (testing "000000000"
    (is (= (list " _  _  _  _  _  _  _  _  _ "
                 "| || || || || || || || || |"
                 "|_||_||_||_||_||_||_||_||_|"
                 "                           ")
           (write-entry (repeat 9 0))))))

(deftest test-read-entry
  (testing "000000000"
    (is (= (repeat 9 0) (read-entry (write-entry (repeat 9 0))))))

  (testing "111111111"
    (is (= (repeat 9 1) (read-entry (write-entry (repeat 9 1))))))

  (testing "123456789"
    (is (= (range 1 (inc 9)) (read-entry (write-entry (range 1 (inc 9))))))))

(deftest test-read-file
  (testing "read a file with 10 entries"
    (is (= (read-clj "resources/read_file_10_entries.ans") (read-file "resources/read_file_10_entries.input")))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; User Story 2

(deftest test-validate-account-number
  (testing "valid account number"
    (is (valid-account-number? [3 4 5 8 8 2 8 6 5])))

  (testing "invalid account number"
    (is (not (valid-account-number? [4 4 5 8 8 2 8 6 5])))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; User Story 3

(deftest test-illegible-number
  (testing "illegible number"
    (let [illegible '(8 6 1 1 0 ? ? 3 6)]
      (is (= illegible (read-entry (write-entry illegible)))))))

(deftest test-findings-file
  (testing "one of each status"
    (write-findings (read-file "resources/findings_one_of_each.input")
                    "resources/findings_one_of_each.gen")
    (is (= (slurp "resources/findings_one_of_each.ans") (slurp "resources/findings_one_of_each.gen")))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; User Story 4

(deftest test-could-be
  (testing "0 could be 8"
    (is (contains? (could-be (segment 0)) (segment 8))))

  (testing "5 could be 6 or 9"
    (let [mightbe (could-be (segment 5))]
      (is (contains? mightbe (segment 6)))
      (is (contains? mightbe (segment 9)))))

  ;; _
  ;;|_|
  ;;| |
  (testing "A could be 8"
    (is (contains? (could-be 0x5F) (segment 8)))))

(deftest test-attempt-repair
  (testing "555555555 could be 555655555 or 559555555"
    (let [actual (attempt-repair (vec (repeat 9 5)))]
      (is (contains? actual [5 5 5 6 5 5 5 5 5]))
      (is (contains? actual [5 5 9 5 5 5 5 5 5]))))

  ;; _
  ;;|_|
  ;;  |
  (testing "55?555555 could be 559555555"
    (let [actual (attempt-repair [5 5 0x4F 5 5 5 5 5 5])]
      (contains? actual [5 5 9 5 5 5 5 5 5]))))

