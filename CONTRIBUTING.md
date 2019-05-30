
# CISE SIM

## Overview 

The CISE SIM is a software meant to read/consume message on the behalf of a node or adapter in the CISE infrastructure.
validating them into a CISE Data Model and sending back complaint synchronous acknowledgment.

The standard INPUT is a file in this version but could be changed to a GitHub or bitbucket repository

There will be two main Simulator modality supported:
* EGN
* SLA

## General architecture
The software is composed of 7 java modules: 

3 that are generic modules as a cise reference 
- cise-entity
- cise-sign
- cise-formal

3 that are specific modules as used only in the simulator
- cise-sim-app
- cise-sim-sender
- cise-sim-web

a specific cise-sim-react module that referred to javascript project is defined apart

 future improvement consists in putting a proxy that capturing and then replay a series of actions.