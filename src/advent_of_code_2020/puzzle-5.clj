(ns advent-of-code-2020.puzzle-5
  (:gen-class)
  (:require [clojure.string :as str]))

(defn read-input
  []
  (->> (slurp "resources/puzzle-5")
       (str/split-lines)))

(defn row-number
  [boarding-pass]
  (loop [row-desigation (take 7 boarding-pass)
         rows (range 0 128)]
    (let [row-decider (fn [letter] (case letter
                                   \F 0
                                   \B 1))
          current (first row-desigation)
          partitioned (split-at (/ (count rows) 2) rows)]
      (if (nil? current)
        (first rows)
        (recur (rest row-desigation) (partitioned (row-decider current)))))))

(defn column-number
  [boarding-pass]
  (loop [row-desigation (drop 7 boarding-pass)
         rows (range 0 8)]
    (let [row-decider (fn [letter] (case letter
                                   \L 0
                                   \R 1))
          current (first row-desigation)
          partitioned (split-at (/ (count rows) 2) rows)]
      (if (nil? current)
        (first rows)
        (recur (rest row-desigation) (partitioned (row-decider current)))))))

(defn version-1
  []
  (->> (read-input)
       (map #(+ (* (row-number %1) 8) (column-number %1)))
       (sort >)
       first))
