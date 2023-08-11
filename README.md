# Java Challenge

Design, document and partial implement an API that would service a front-end application which enrolls new clients.

## Description

Suppose there is a frontend application whose purpose is to enroll new clients into the core banking system. The Frontdesk Employee uses the application to input clients data (identity document information) then submit a full Client check. Based on the checking response, the Frontdesk Employee will either generate the enrolment document or a denial document specifying the reason the enrolment was denied. In both cases the generated document will have to be signed by both Client and Frontdesk Employee and submitted back to system.

## Your tasks

1. Design (create endpoint contracts) the API that will support the above operations (full client check, generating enrolment/denial documents, etc), create the needed documentation for the API
2. Implement the operation that performs the Client check.

The Client check operation should check the document id validity (whether it's expired or not), query an external system for checking client's reputation (the client reputation is defined by a number: 0 to 20 means 'Candidate with no risk', 21 to 99 means 'Candidate with medium risk, but enrollment still possible', anything above 99 means 'Risky candidate, enrollment not acceptable') and querying another external system to check whether the client already exists.

**On the real project we use the stack describe bellow, feel free to select any of them for the showcase project if you want and have the knowledge. It is ok for you to submit code that uses the alternatives to them (eg: use maven instead of gradle):**

- java 11
- gradle
- springboot 2.4.x
- junit5
- mybatis
- liquibase
- mapstruct
- OpenAPI
- lombok
- Github Enterprise
- docker & dockercompose

Please remember that it is more important that you submit code as if you intend to ship it to production, rather than completing the whole implementation. The details matter. Tests are expected, as is well written, simple idiomatic code and any additional information that you feel is important for me as a reviewer. Feel free to share your solution in GitHub.
