This project is meant to `validate` the `platform-store-client` :

    - validates network configuration (discovery via consul) 
    - validates ssl configuration (user certificates)
    - validates user credentials (jdbc url)
    - validates user privileges (DDL, CRUD)

It also acts as an example of how any `platform-store` consumer node should integrate the client
