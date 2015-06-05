(defproject marathon-client "0.1.0"
  :description "A Marathon client written in Clojure."
  :url "https://github.com/wkf/marathon-client-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :test-paths ["src/test"]
  :source-paths ["src/main"]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-http "1.1.2"]
                 [bidi "1.18.9" :exclusions [org.clojure/clojure]]
                 [camel-snake-kebab "0.3.1" :exclusions [org.clojure/clojure]]]
  :profiles {:dev {:source-paths ["src/dev"]
                   :dependencies [[org.clojure/data.json "0.2.6"]
                                  [clj-http-fake "1.0.1"]]}})
