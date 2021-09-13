(ns advent-of-code-2020.puzzle-9
  (:gen-class)
  (:require [clojure.string :as str]))

(defn read-input
  ([] (read-input "puzzle-9"))
  ([name]
   (->> (slurp (str "resources/" name))
                   (str/split-lines)
                   (map #(Integer/parseInt %1)))))

(defn matches-preamble?
  [preamble value]
  (some (fn [preamble-value]
          (let [required-value (- value preamble-value)]
            (some #(= %1 required-value) preamble))) preamble))

(defn find-first-invalid
  [input preamble-size]
  (let [preamble (apply vector (take preamble-size input))
        data (drop preamble-size input)]
    (loop [current-preamble preamble
           [value & remaining] data]
      (if (matches-preamble? current-preamble value)
        (recur (conj (->> (drop 1 current-preamble)
                          (apply vector))
                     value)
               remaining)
        value))))

(defn find-contiguous-range-for
  [data total]
  (loop [[head second & remaining] data]
    (let [{:keys [sum smallest biggest]}
          (reduce
           (fn [{:keys [sum smallest biggest]} value]
             (if (>= sum total)
               (reduced {:sum sum :smallest smallest :biggest biggest})
               {:sum (+ sum value)
                :smallest (if (< value smallest) value smallest)
                :biggest (if (> value biggest) value biggest)}))
           {:sum (+ head second)
            :smallest (if (< head second) head second)
            :biggest (if (> head second) head second)}
           remaining)]
      (if (= sum total)
        {:smallest smallest :biggest biggest}
        (recur (conj remaining second))))))

(defn version-1
  []
  (-> (read-input)
      (find-first-invalid 25)))

(defn version-2
  []
  (let [data (read-input)]
    (->> (find-first-invalid data 25)
         (find-contiguous-range-for data)
         vals
         (reduce +))))
