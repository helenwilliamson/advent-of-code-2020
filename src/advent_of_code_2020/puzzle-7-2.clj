(ns advent-of-code-2020.puzzle-7-2
  (:gen-class)
  (:require [clojure.string :as str]))

(def rule-matcher #"\d?[a-zA-Z ]+ bags?")
(def splitter #" contain ")

(defn read-input
  []
  (->> (slurp "resources/puzzle-7")
       (str/split-lines)
       (map #(str/split %1 splitter))
       (map #(zipmap [:colour :contents]
                     (list (-> (first %1)
                               (str/replace #" bags?" ""))
                           (->> (second %1)
                                (re-seq rule-matcher)
                                (map str/trim)
                                (filter (fn [bag] (not= bag "no other bags")))
                                (map (fn [bag] (zipmap [:number :colour]
                                                       (-> bag
                                                           (str/replace #" bags?" "")
                                                           (str/split #" " 2)))))))))))

(def goal "shiny gold")

(defn rule-for-colour
  [rules colour]
  (first (filter #(= colour (:colour %1)) rules)))

(defn contents-of-colour
  ([data] (contents-of-colour data goal))
  ([data colour]
   (let [rule (rule-for-colour data colour)
         contents (:contents rule)]
     (if (empty? contents)
       0
       (reduce (fn [acc rule]
                 (let [number-in-rule (Integer/parseInt (:number rule))
                       child-rules (contents-of-colour data (:colour rule))]
                   (+ acc number-in-rule  (* number-in-rule child-rules))))
               0
               contents)
       ))))

(defn version-2
  []
  (-> (read-input)
      (contents-of-colour)))
