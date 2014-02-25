(ns clj-wordnet.test.client
  (:use [clojure.test]
        [clj-wordnet.core]))

(def wordnet (make-dictionary "./data/dict"))

(deftest fetch-with-noun
  (is (= "dog" (:lemma (first (wordnet "dog" :noun))))))

(deftest fetch-without-pos
  (is (= "dog" (:lemma (first (wordnet "dog "))))))

(deftest fetch-unknown-word
  (is (empty? (wordnet "fdssfsfs"))))

(deftest fetch-empty-word
  (is (empty? (wordnet ""))))

(deftest fetch-nil-word
  (is (empty? (wordnet nil))))

(deftest fetch-by-stemming
  (is (= "dog" (:lemma (first (wordnet "dogs")))))
  (is (= "buy" (:lemma (first (wordnet "bought"))))))

(deftest relational-synset-test
  (let [dog (first (wordnet "dog" :noun))]
    (is (= ["domesticated_animal" "domestic_animal" "canid" "canine"])
           (map :lemma (flatten (vals (related-synsets dog :hypernym)))))))
