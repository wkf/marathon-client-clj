(ns marathon-client.support.rest
  (:refer-clojure :exclude [get])
  (:require [clojure.walk :as walk]
            [bidi.bidi :as bidi]
            [clj-http.client :as http]
            [camel-snake-kebab.core :refer [->camelCaseString
                                            ->kebab-case-keyword]])
  (:import (java.net URI)))

(defn- transform-keys-1
  "Apply a function f to every key in a map."
  [f x]
  (if (map? x) (into {} (map (fn [[k v]] [(f k) v]) x)) x))

(defn- skip
  "Don't just walk... skip!

   A prewalk that applies a function f, skipping any keys in the vector ks."
  [f ks form]
  (if-let [m (and (map? form) (not-empty (select-keys form ks)))]
    (f (merge
         (skip f ks (apply dissoc form (keys m))) m))
    (walk/walk (partial skip f ks) identity (f form))))

(defn- ->body [form]
  (skip (partial transform-keys-1 ->camelCaseString) [:env] form))

(defn- <-body [form]
  (skip (partial transform-keys-1 ->kebab-case-keyword) ["env"] form))

(defn- path-for
  [routes route params]
  (apply bidi/path-for routes route (flatten (seq params))))

(defn request
  [f c route params body options]
  (let [path (path-for (:routes c) route params)
        uri' (str (.resolve (URI. (:uri c)) (URI. path)))
        options' (merge
                   (:options c) options (when body
                                          {:form-params (->body body)}))]
    (->> (f uri' options') :body <-body)))

(defn get
  ([c route] (get c route {}))
  ([c route params] (get c route params {}))
  ([c route params options] (request http/get c route params nil options)))

(defn put
  ([c route] (put c route {}))
  ([c route params] (put c route params nil))
  ([c route params body] (put c route params body {}))
  ([c route params body options] (request http/put c route params body options)))

(defn post
  ([c route] (post c route {}))
  ([c route params] (post c route params nil))
  ([c route params body] (post c route params body {}))
  ([c route params body options] (request http/post c route params body options)))

(defn delete
  ([c route] (delete c route {}))
  ([c route params] (delete c route params {}))
  ([c route params options] (request http/delete c route params nil options)))
