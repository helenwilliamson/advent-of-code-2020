(ns advent-of-code-2020.puzzle-8
  (:gen-class)
  (:require [clojure.string :as str]))

(defn read-input
  ([] (read-input "puzzle-8"))
  ([name]
   (->> (-> (slurp (str "resources/" name))
            (str/split-lines))
        (map-indexed #(merge
                       {:index %1}
                       (update (zipmap [:operation :value] (str/split %2 #" "))
                               :value
                               (fn [pos] (Integer/parseInt pos)))))
        (apply vector))))

(defn run
  [programme]
  (loop [seen-instructions #{}
         position 0
         accumulator 0]
    (let [{:keys [index operation value]} (programme position)]
      (if (contains? seen-instructions index)
        accumulator
        (let [{:keys [new-position :new-accumulator]}
              (case operation
                "nop" {:new-position (inc position) :new-accumulator accumulator}
                "acc" {:new-position (inc position) :new-accumulator (+ accumulator value)}
                "jmp" {:new-position (+ value position) :new-accumulator accumulator})]
          (recur (conj seen-instructions index) new-position new-accumulator))))))

(defn terminates?
  [programme]
  (loop [seen-instructions #{}
         position 0
         accumulator 0]
    (if (= position (count programme))
      accumulator
      (let [{:keys [index operation value]} (programme position)]
        (if (contains? seen-instructions index)
          nil
          (let [{:keys [new-position :new-accumulator]}
                (case operation
                  "nop" {:new-position (inc position) :new-accumulator accumulator}
                  "acc" {:new-position (inc position) :new-accumulator (+ accumulator value)}
                  "jmp" {:new-position (+ value position) :new-accumulator accumulator})]
            (recur (conj seen-instructions index) new-position new-accumulator)))))))

(defn all-programmes
  [programme]
  (map (fn [{:keys [index operation] :as instruction}]
         (let [new-operation (if (= operation "nop")
                               "jmp"
                               "nop")]
           (assoc programme index (assoc instruction :operation new-operation))))
       (filter #(not= "acc" (:operation %1)) programme)))

(defn version-1
  []
  (-> (read-input)
      run))

(defn version-2
  []
  (->> (read-input)
       all-programmes
       (some terminates?)))
