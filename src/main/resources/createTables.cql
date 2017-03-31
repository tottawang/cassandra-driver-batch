CREATE TABLE if not exists products (
    itemid text,
    version int,
    productid uuid,
    scopes map<text, text>,
    PRIMARY KEY ((itemid, version), productid)
);

CREATE TABLE if not exists product_by_id (
    itemid text,
    version int,
    productid uuid,
    scopes map<text, text>,
    PRIMARY KEY ((productid, itemid), version)
);

