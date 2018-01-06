(ns redux-example.store
  (:require [reagent.core :as r]
            [com.ben-allred.collaj.core :as collaj]
            [com.ben-allred.collaj.enhancers :as cen]))

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
  (collaj/combine-reducers {:incremento inc'er
                            :decremento dec'er
                            :bicremento bic'er}))

(def store (collaj/create-custom-store r/atom reducer (cen/with-log-middleware (comp js/console.log pr-str))))
