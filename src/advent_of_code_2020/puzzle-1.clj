(ns advent-of-code-2020.puzzle-1
  (:gen-class)
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

(defn sum-to
  [sum number-of-values values]
  (->> (combo/combinations values number-of-values)
       (filter #(= 2020 (reduce + %)))
       (first)
       (reduce *)))

(defn read-input
  []
  (->> (slurp "resources/puzzle-1")
       (str/split-lines)
       (map #(Integer/parseInt %1))))

(defn sum-to-2020
  [number-of-values]
  (->> (read-input)
      (sum-to 2020 number-of-values)))
