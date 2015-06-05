(ns marathon-client.core-test
  (:require [clj-http.core]
            [clj-http.fake :refer [with-fake-routes]]
            [clojure.data.json :as json]
            [clojure.test :refer :all]
            [marathon-client.core :refer :all]))

(deftest skip-env-map-when-converting-keys
  (testing "keys in env map in request are not converted to camel case strings"
    (with-fake-routes
      {"http://testhost/v2/apps" (fn [req]
                                   (let [env (-> req :body slurp json/read-str (get "env"))]
                                     {:status 200
                                      :body (json/write-str {"app" {"env" env}})}))}
      (is (-> (client {:uri "http://testhost"})
              (create-app! {:env {"TEST_VAR" "value"}})
              :app
              :env
              (= {"TEST_VAR" "value"}))))))
