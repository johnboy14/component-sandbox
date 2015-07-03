(ns reloadable.handler
  (:require [clojure.core.async :as async]
            [compojure.route :as route]
            [compojure.core :refer :all]
            [ring.middleware.json :as middleware]))

(defn get-keys [channel]
  (async/<!! channel))

(defn app [channel]
  (->
    (routes
      (GET "/keys" []
           {:status  200
            :headers {"Content-Type" "text/plain"}
            :body    (get-keys channel)})
      (route/not-found "hello"))
    (middleware/wrap-json-body {:keywords? true})
    (middleware/wrap-json-response)))
