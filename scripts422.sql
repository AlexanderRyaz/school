create table  if not exists person (id integer not null primary key,
person_name varchar, age integer, license boolean);
create table  if not exists car (id integer not null primary key,
brand varchar, model varchar, price integer);
create table if not exists person_car (id integer not null primary key,
person_id integer, car_id integer, constraint person_car_person foreign key (person_id) references person(id),
constraint person_car_car foreign key (car_id) references car(id));
