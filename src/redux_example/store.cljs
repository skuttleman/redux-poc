(ns redux-example.store
    (:require [com.ben-allred.collaj.core :as collaj]
              [com.ben-allred.collaj.enhancers :as collaj.en]
              [com.ben-allred.collaj.reducers :as collaj.red]
              [reagent.core :as r]))

(defn inc'er
  ([] 0)
  ([state [type]]
   (case type
     :counter/increment (inc state)
     state)))

(defn dec'er
  ([] 0)
  ([state [type]]
   (case type
     :counter/decrement (dec state)
     state)))

(defn bic'er
  ([] 0)
  ([state [type]]
   (case type
     :counter/increment (inc state)
     :counter/decrement (dec state)
     state)))

(def reducer
  (collaj.red/combine {:incremento      inc'er
                            :decremento dec'er
                            :bicremento bic'er}))

(def store (collaj/create-custom-store r/atom reducer (collaj.en/with-log-middleware (comp js/console.log pr-str))))
