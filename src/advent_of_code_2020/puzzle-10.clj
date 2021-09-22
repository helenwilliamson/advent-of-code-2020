(ns advent-of-code-2020.puzzle-10
  (:gen-class)
  (:require [clojure.string :as str]))

(defn read-input
  ([] (read-input "puzzle-10"))
  ([name] (->> (slurp (str "resources/" name))
               (str/split-lines)
               (map #(Integer/parseInt %1))
               (sort))))

(defn get-differences
  [data]
  (->> (conj data 0)
       (partition 2 1)
       (map #(Math/abs (reduce - %1)))))

(defn next-adapter
  [head tail]
  (map (fn [value] (drop-while #(< %1 value) tail))
         (filter #(<= (- %1 head) 3) tail)))

(defn combinations
  [data]
  (loop [total 0
         [[head & tail :as current] & remaining] [(conj data 0)]]
    (if (nil? current)
      total
      (let [next-values (next-adapter head tail)
            new-total (if (empty? next-values) (inc total) total)]
        (recur new-total (concat next-values remaining))))))

(defn version-1
  []
  (reduce * (-> (read-input)
                (get-differences)
                (conj 3)
                (frequencies)
                (select-keys [1 3])
                (vals))))
