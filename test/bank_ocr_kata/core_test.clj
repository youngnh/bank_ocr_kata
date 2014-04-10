(ns bank-ocr-kata.core-test
  (:require [clojure.test :refer :all]
            [bank-ocr-kata.core :refer :all]))

(deftest test-write-entry
  (testing "00"
    (is (= (list " -  - "
                 "| || |"
                 "|_||_|"
                 "      ")
           (write-entry (repeat 2 0)))))

  (testing "000000000"
    (is (= (list " -  -  -  -  -  -  -  -  - "
                 "| || || || || || || || || |"
                 "|_||_||_||_||_||_||_||_||_|"
                 "                           ")
           (write-entry (repeat 9 0))))))

(deftest test-read-entry
  (testing "000000000"
    (is (= (repeat 9 0) (read-entry (write-entry (repeat 9 0))))))

  (testing "111111111"
    (is (= (repeat 9 1) (read-entry (write-entry (repeat 9 1)))))))
