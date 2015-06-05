# marathon-client

[![Build Status](https://secure.travis-ci.org/wkf/marathon-client-clj.svg)](http://travis-ci.org/wkf/marathon-client-clj)

An incomplete [Marathon](https://github.com/mesosphere/marathon) remote API client written in Clojure.

## Installation

To install, add the following dependency to your project.clj file:

[![Clojars Project](http://clojars.org/marathon-client/latest-version.svg)](http://clojars.org/marathon-client)

## Usage

To get started, require the core namespace:

```clojure
(require '[marathon-client.core :as marathon])
```

Next, create a marathon client:

```clojure
(def c
  (marathon/client {:uri "http://localhost:4343"}))

```

Once you have a client, you pass it to any of the implemented public functions. So far, that includes:

```clojure

(create-app! c {:id "/test"
                :cmd "sleep 100"})

(inspect-app c "/test")

(update-app! c "/test" {:cmd "sleep 1000"})

(destroy-app! "/test")

```

## Tests
This project uses core.test, so to run tests:

`lein test`

## Future

This client is a work in progress; there are many endpoints left to implement. If you have a pressing need for a particular feature, create an issue.

## License

Copyright © 2015 [Will Farrell](http://willfarrell.is)

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
