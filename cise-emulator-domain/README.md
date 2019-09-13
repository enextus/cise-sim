# Technical debt
* The serviceId in the config might be null in this case should not override anything
* The sender must be present: if it's not there an exception must be thrown 
* The cise-signature must be updated with an api module and implementation module so to separate domain from libraries. 

# Nice to have 
* toggles in the config in order to select what to override
* toggles in the config in order to exclude the signing of the message
* modify the cise-models to use LocalDateTime instead of XMLGregorianCalendar