use admin;
db.createUser(
    {
        user: "admin",
        pwd: "admin",
        roles: [ "root" ]
    }
);

use tophistorydb;
db.createUser(
    {
        user: "tophistoryuser",
        pwd: "tophistorypassword",
        roles: [ { role: "readWrite", db: "tophistorydb" } ]
    }
);