(ns reloadable.web-server
  (:require [com.stuartsierra.component :as component]
            [org.httpkit.server :refer [run-server]]
            [reloadable.handler :refer [app]]))

(defrecord WebServer [port server channels]
  component/Lifecycle
  (start [component]
    (let [server (run-server (app (:key-chan channels)) {:port port})]
      (assoc component :server server)))
  (stop [component]
    (when server
      (server)
      component)))

(defn new-web-server
  [port]
  (map->WebServer {:port port}))
