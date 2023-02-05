create table if not exists person
(
    id
    integer
    not
    null
    primary
    key,
    person_name
    varchar,
    age
    integer,
    license
    boolean,
    constraint
    person_car
    foreign
    key
(
    car_id
) references car
(
    id
));
create table if not exists car
(
    id
    integer
    not
    null
    primary
    key,
    brand
    varchar,
    model
    varchar,
    price
    integer
);

