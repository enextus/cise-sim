# Technical debt
* The cise-signature must be updated with an api module and implementation module so to separate domain from libraries. 

# Nice to have 
* toggles in the config in order to select what to override 
    -> is it really needed? Is sufficient to put an empty property in the config to avoid the override. Maybe could be better to pass the toggle from the ui as a runtime decision. 
* toggles in the config in order to exclude the signing of the message
* modify the cise-models to use LocalDateTime instead of XMLGregorianCalendar