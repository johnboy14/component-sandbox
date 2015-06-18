(ns reloadable.Key-Channels
  (:require [clojure.core.async :as async]
            [com.stuartsierra.component :as component]))

(defrecord Key-Channels [buffersize]
  component/Lifecycle
  (start [component]
    (println "Creating Key Channels")
    (assoc component :key-chan (async/chan buffersize)))
  (stop [component]
    (println "Destroying Key Channels")
    (assoc component :key-chan nil)))

(defn new-key-channels [buffersize]
  (map->Key-Channels {:buffersize buffersize}))