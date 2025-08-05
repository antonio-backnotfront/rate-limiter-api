insert into role(id, name) values
                           (1, 'user');
insert into role(id, name) values
                           (2, 'admin');


insert into user(email, password, role_id) values
                                                # pass1
                                               ('anna@email.com','$2a$12$teClfutkJutplDbjISElFOqZos2y9y5qshSZAksLnfzSkyj2GpjFe', 1);
insert into user(email, password, role_id) values
                                                # pwe2
                                                ('john@email.com','$2a$12$avvPnAUIVjnQuwmGJM9T8ODFJVW75gbaT.iR162frl56P8AqikWM6', 2);
