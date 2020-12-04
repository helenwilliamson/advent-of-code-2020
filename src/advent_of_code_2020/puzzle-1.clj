(ns advent-of-code-2020.puzzle-1
  (:gen-class)
  (:require [clojure.string :as str]))

(defn two-sum-to
  [sum values]
  (loop [data values]
    (let [value (first data)
          others (rest data)
          match (filter #(= 2020 (+ value %)) others)]
      (if (empty? match)
        (recur others)
        (* value (first match))))))

(defn read-input
  []
  (->> (slurp "resources/puzzle-1")
       (str/split-lines)
       (map #(Integer/parseInt %1))))

(defn two-sum-to-2020
  []
  (->> (read-input)
      (two-sum-to 2020)))
