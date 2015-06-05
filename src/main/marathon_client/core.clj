(ns marathon-client.core
  (:refer-clojure :exclude [get])
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [marathon-client.support.rest :as rest]))

(defn- read-routes []
  (-> "routes.edn" io/resource slurp edn/read-string))

(defn client
  [config]
  (-> config
      (assoc :routes (read-routes))
      (update-in [:options] merge {:as :json-string-keys
                                   :accept :json
                                   :content-type :json})))

(defn inspect-app [c id]
  (rest/get c :app {:app-id id}))

(defn create-app! [c spec]
  (rest/post c :apps {} spec))

(defn update-app! [c id spec]
  (rest/put c :app {:app-id id} spec))

(defn destroy-app! [c id]
  (rest/delete c :app {:app-id id}))
