(ns advent-of-code-2020.puzzle-7-1
  (:gen-class)
  (:require [clojure.string :as str]))

(def rule-matcher #"[a-zA-Z ]+ bags?")
(def splitter #" contain ")

(defn read-input
  []
  (->> (slurp "resources/puzzle-7")
       (str/split-lines)
       (map #(str/split %1 splitter))
       (map #(list (-> (first %1)
                       (str/replace #" bags?" ""))
                   (->> (second %1)
                        (re-seq rule-matcher)
                        (map str/trim)
                        (map (fn [bag] (str/replace bag #" bags?" ""))))))))

(def goal "shiny gold")

(defn matching-rules
  [rules colour]
  (filter #(some (fn [bag] (str/includes? bag colour))
                 (second %1))
          rules))

(defn contains-shiny-gold
  [data]
  (loop [wip (matching-rules data goal)
         matched-rules #{}]
    (if (empty? wip)
      matched-rules
      (let [[bag] (first wip)
            rules-for-current (matching-rules data bag)]
        (recur (concat (rest wip) rules-for-current) (conj matched-rules bag))))))

(defn version-1
  []
  (-> (read-input)
      (contains-shiny-gold)
      count))
