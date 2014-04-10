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
