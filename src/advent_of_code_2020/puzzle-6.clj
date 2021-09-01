(ns advent-of-code-2020.puzzle-6
  (:gen-class)
  (:require [clojure.string :as str]))

(defn read-input
  []
  (map str/split-lines (-> (slurp "resources/puzzle-6")
                           (str/split #"\n\n"))))

(defn unique-answers
  [group-answers]
  (into #{} (flatten (map #(into [] %1) group-answers))))

(defn version-1
  []
  (->> (read-input)
       (map unique-answers)
       (map count)
       (reduce +)))
