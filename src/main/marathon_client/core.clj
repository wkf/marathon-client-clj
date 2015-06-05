(ns marathon-client.core
  (:refer-clojure :exclude [get])
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [marathon-client.support.rest :as rest]))

(def routes
  ["/"
   {"ping" :ping
    "logging" :logging
    "help" :help
    "metrics" :metrics
    "v2/" {"apps" {"" :apps
                   ["/" :app-id] {"" :app
                                  "/restart" :restart-app
                                  "/tasks" {"" :tasks
                                            ["/" :task-id] :task}
                                  "/versions" {"" :versions
                                               ["/" :version] :version}}}
           "groups" {"" :groups
                     ["/" :group-id] :group}
           "tasks" {"" :tasks
                    "/delete" :delete-tasks}
           "deployments" {"" :deployments
                          ["/" :deployment-id] :deployment}
           "events" :events
           "eventSubscriptions" :event-subscriptions
           "queue" :queue
           "info" :info
           "leader" :leader}}])

(defn client
  [config]
  (-> config
      (assoc :routes routes)
      (update-in [:options] merge {:as :json-string-keys
                                   :accept :json
                                   :content-type :json})))

(defn inspect-app
  ([c id] (inspect-app c id {}))
  ([c id options] (rest/get c :app {:app-id id} options)))

(defn create-app!
  ([c spec] (create-app! c spec {}))
  ([c spec options] (rest/post c :apps {} spec options)))

(defn update-app!
  ([c id spec] (update-app! c id spec {}))
  ([c id spec options] (rest/put c :app {:app-id id} spec options)))

(defn destroy-app!
  ([c id] (destroy-app! c id {}))
  ([c id options] (rest/delete c :app {:app-id id} options)))
