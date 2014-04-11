(ns bank-ocr-kata.core-test
  (:require [clojure.test :refer :all]
            [bank-ocr-kata.test-utils :refer :all]
            [bank-ocr-kata.core :refer :all]))

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
