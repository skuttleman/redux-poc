(ns redux-example.nested-store
    (:require [com.ben-allred.collaj.core :as collaj]
              [com.ben-allred.collaj.enhancers :as collaj.en]
              [com.ben-allred.collaj.reducers :as collaj.red]
              [reagent.core :as r]))

(defn pertinent-user-reducer
    ([] nil)
    ([state [type {:keys [user incident]}]]
     (case type
         :incidents/request nil
         :incidents/receive (:pertinent-user incident)
         :incidents/update-pertinent-user user
         state)))

(defn tasks-reducer
    ([] [])
    ([state [type {:keys [tasks]}]]
     (case type
         :incidents/request []
         :incidents/receive-tasks tasks
         state)))

(defn forms-reducer
    ([] [])
    ([state [type {:keys [forms]}]]
     (case type
         :incidents/request []
         :incidents/receive-forms forms
         state)))

(defn incident-reducer
    ([] {})
    ([state [type {:keys [incident]} :as action]]
     (case type
         :incidents/request {}
         :incidents/receive incident
         state)))

(def incident-reducer' (collaj.red/assoc incident-reducer
                           :pertinent-user pertinent-user-reducer
                           :tasks tasks-reducer
                           :forms forms-reducer))

(defn incidents-reducer
    ([] {})
    ([state [type {:keys [incident] :as id} :as action]]
     (cond-> state
         (re-matches #":incidents/.*" (str type)) (update (:id incident) incident-reducer' action))))

(def reducer (collaj.red/combine {:incidents incidents-reducer}))

(def nested-store (collaj/create-custom-store r/atom reducer (collaj.en/with-log-middleware (comp js/console.log pr-str))))
