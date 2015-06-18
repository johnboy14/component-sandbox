(ns reloadable.system
  (:require [com.stuartsierra.component :as component]
            [reloadable.Key-Channels :refer [new-key-channels]]
            [reloadable.key-maker :refer [new-key-maker]]
            [reloadable.web-server :refer [new-web-server]]))

(defn new-system []
  (component/system-map
    :key-channels (new-key-channels 100)
    :key-maker (component/using (new-key-maker) {:channels :key-channels})
    :server (component/using (new-web-server 3000) {:channels :key-channels})))
